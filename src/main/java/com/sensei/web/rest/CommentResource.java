package com.sensei.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.sensei.domain.Comment;
import com.sensei.domain.Newsfeed;
import com.sensei.domain.User;
import com.sensei.repository.CommentRepository;
import com.sensei.repository.NewsfeedRepository;
import com.sensei.repository.UserRepository;
import com.sensei.service.UserService;
import com.sensei.web.rest.util.HeaderUtil;
import com.sensei.web.rest.util.ResponseUtil;
import com.sensei.web.rest.vm.CommentVmResponse;
import com.sensei.web.rest.vm.CommentVmResquest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing Comment.
 */
@RestController
@RequestMapping("/api")
public class CommentResource {

    private final Logger log = LoggerFactory.getLogger(CommentResource.class);

    private static final String ENTITY_NAME = "comment";

    private final CommentRepository commentRepository;
    private final NewsfeedRepository newsfeedRepository;
    private final UserService userService;

    public CommentResource(CommentRepository commentRepository, NewsfeedRepository newsfeedRepository, UserService userService) {
        this.commentRepository = commentRepository;
        this.newsfeedRepository = newsfeedRepository;
        this.userService = userService;
    }

    /**
     * POST  /comments : Create a new comment.
     *
     * @param comment the comment to create
     * @return the ResponseEntity with status 201 (Created) and with body the new comment, or with status 400 (Bad Request) if the comment has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("newsfeeds/comments")
    @Timed
    public ResponseEntity<CommentVmResponse> createComment(@Valid @RequestBody CommentVmResquest commentVmResquest) throws URISyntaxException {
        log.debug("REST request to save Comment : {}", commentVmResquest);
     
        /* if (comment.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new comment cannot already have an ID")).body(null);
        }*/
        
        Comment result = saveComment(commentVmResquest);
        
        CommentVmResponse commentVmResponse = getCommentResponse(result);
        
        return ResponseEntity.created(new URI("/api/comments/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(commentVmResponse);
    }


    /**
     * PUT  /comments : Updates an existing comment.
     *
     * @param comment the comment to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated comment,
     * or with status 400 (Bad Request) if the comment is not valid,
     * or with status 500 (Internal Server Error) if the comment couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/comments")
    @Timed
    public ResponseEntity<CommentVmResponse> updateComment(@Valid @RequestBody CommentVmResquest commentVmResquest, Long commentId) throws URISyntaxException {
        log.debug("REST request to update Comment : {}", commentVmResquest);
        
        Comment retrievedComment = commentRepository.findOne(commentId);
        
       if (retrievedComment == null) {
            return createComment(commentVmResquest);
        }
        
        Comment result = updateComment(commentVmResquest, retrievedComment);
        
        CommentVmResponse commentVmResponse = getCommentResponse(result);
        
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, retrievedComment.getId().toString()))
            .body(commentVmResponse);
    }


    /**
     * GET  /comments : get all the comments.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of comments in body
     */
    @GetMapping("/comments")
    @Timed
    public List<Comment> getAllComments() {
        log.debug("REST request to get all Comments");
        return commentRepository.findAll();
    }

    /**
     * GET  /comments/:id : get the "id" comment.
     *
     * @param id the id of the comment to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the comment, or with status 404 (Not Found)
     */
    @GetMapping("/comments/{id}")
    @Timed
    public ResponseEntity<Comment> getComment(@PathVariable Long id)
    {
        log.debug("REST request to get Comment : {}", id);
        Comment comment = commentRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(comment));
    }

    /**
     * DELETE  /comments/:id : delete the "id" comment.
     *
     * @param id the id of the comment to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/comments/{id}")
    @Timed
    public ResponseEntity<Void> deleteComment(@PathVariable Long id) 
    {
        log.debug("REST request to delete Comment : {}", id);
        commentRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
    
	private Comment saveComment(CommentVmResquest commentVmResquest) 
	{
		Newsfeed newsFeed = newsfeedRepository.findOne(commentVmResquest.getNewsfeedId());
        Comment comment = new Comment();
        comment.setNewsfeed(newsFeed);
        comment.setContent(commentVmResquest.getContent());
        comment.setDatePosted(LocalDateTime.now());
        comment.setUser(userService.getUserWithAuthorities());
        
        Comment result = commentRepository.save(comment);
		return result;
	}
	
	private Comment updateComment(CommentVmResquest commentVmResquest, Comment retrievedComment) {
		Newsfeed newsFeed = newsfeedRepository.findOne(commentVmResquest.getNewsfeedId());
        retrievedComment.setNewsfeed(newsFeed);
        retrievedComment.setContent(commentVmResquest.getContent());
        retrievedComment.setDatePosted(LocalDateTime.now());
        retrievedComment.setUser(userService.getUserWithAuthorities());
        
        Comment result = commentRepository.save(retrievedComment);
		return result;
	}
	
	private CommentVmResponse getCommentResponse(Comment result) {
		CommentVmResponse commentVmResponse = new CommentVmResponse();
        commentVmResponse.setContent(result.getContent());
        commentVmResponse.setNewsfeedId(result.getNewsfeed().getId());
        commentVmResponse.setId(result.getId());
        
        
        LocalDateTime postedDate = result.getDatePosted();
		DateTimeFormatter formatPostedDate = DateTimeFormatter.ofPattern("MM/dd/yy hh:mm");
		
		commentVmResponse.setDatePosted(postedDate.format(formatPostedDate));
		return commentVmResponse;
	}
}
