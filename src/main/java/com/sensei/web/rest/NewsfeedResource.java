package com.sensei.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.sensei.config.Constants;
import com.sensei.domain.Comment;
import com.sensei.domain.Like;
import com.sensei.domain.Newsfeed;
import com.sensei.domain.User;
import com.sensei.repository.CommentRepository;
import com.sensei.repository.ItemsRepository;
import com.sensei.repository.NewsfeedRepository;
import com.sensei.repository.UserRepository;
import com.sensei.service.UserService;
import com.sensei.web.rest.util.HeaderUtil;
import com.sensei.web.rest.util.ResponseUtil;
import com.sensei.web.rest.vm.LikeVm;
import com.sensei.web.rest.vm.NewsFeedVm;
import com.sensei.web.rest.vm.NewsFeedVmRequest;
import com.sensei.web.rest.vm.NewsFeedVmResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;
import com.sensei.web.rest.vm.CommentVm;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * REST controller for managing Newsfeed.
 */
@RestController
@RequestMapping("/api")
public class NewsfeedResource {

    private final Logger log = LoggerFactory.getLogger(NewsfeedResource.class);

    private static final String ENTITY_NAME = "newsfeed";

    private final NewsfeedRepository newsfeedRepository;
    private final UserRepository userRepository;
    private final CommentRepository commentRepository;
    private final ItemsRepository itemsRepository;

    public NewsfeedResource(NewsfeedRepository newsfeedRepository, UserRepository userRepository,
    		CommentRepository commentRepository, ItemsRepository itemsRepository) {
        this.newsfeedRepository = newsfeedRepository;
        this.userRepository = userRepository;
        this.commentRepository = commentRepository;
        this.itemsRepository = itemsRepository;
        
    }

    /**
     * POST  /newsfeeds : Create a new newsfeed.
     *
     * @param newsfeed the newsfeed to create
     * @return the ResponseEntity with status 201 (Created) and with body the new newsfeed, or with status 400 (Bad Request) if the newsfeed has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
/*    @PostMapping("/newsfeeds")
    @Timed
    public ResponseEntity<Newsfeed> createNewsfeed(@Valid @RequestBody Newsfeed newsfeed) throws URISyntaxException {
        log.debug("REST request to save Newsfeed : {}", newsfeed);
        if (newsfeed.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new newsfeed cannot already have an ID")).body(null);
        }
        Newsfeed result = newsfeedRepository.save(newsfeed);
        return ResponseEntity.created(new URI("/api/newsfeeds/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }*/
    
    
    @PostMapping("/newsfeeds")
    @Timed
    public ResponseEntity<NewsFeedVmResponse> createNewsfeed(@Valid @RequestBody NewsFeedVmRequest request) throws URISyntaxException {
        log.debug("REST request to save Newsfeed : {}", request);
        
/*     if (newsfeed.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new newsfeed cannot already have an ID")).body(null);
        }*/
        
        Newsfeed feed = new Newsfeed();
        feed.setComments(Collections.emptySet());
        feed.setContent(request.getContent());
        feed.setDatePosted(LocalDateTime.now());
        feed.setImageUrl(request.getImageUrl());
        feed.setTitle(request.getTitle());
        
        
        Optional<User> user = userRepository.findOneByLogin(request.getLogin());
        
        if(!user.isPresent())
        {
        	 return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "User with username : " + request.getLogin()+ " does not exist ")).body(null);
        }
        
        
        feed.setUser(user.get());
        
        NewsFeedVmResponse newsFeedVmResponse = saveNewsFeed(feed);
        		
        return ResponseEntity.created(new URI("/api/newsfeeds/" + newsFeedVmResponse.getResponse().getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, newsFeedVmResponse.getResponse().getId().toString()))
            .body(newsFeedVmResponse);
    } 


    /**
     * GET  /newsfeeds : get all the newsfeeds.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of newsfeeds in body
     */
    @GetMapping("/newsfeeds")
    @Timed
    public List<NewsFeedVmResponse> getAllNewsfeeds() {
        log.debug("REST request to get all Newsfeeds");
        
       List<Newsfeed> results = newsfeedRepository.findAll();
       
       
       List<NewsFeedVmResponse> responseList = results.stream().map( newsFeed -> 
       {
    	   NewsFeedVmResponse newsFeedVmResponse = new NewsFeedVmResponse();
    	   
	       NewsFeedVm response =  new NewsFeedVm();
	       List<CommentVm> commentList = getCommentVm(newsFeed);
	       
	       response.setComment(commentList);
	       response.setContent(newsFeed.getContent());
	       
	       DateTimeFormatter formatPostedDate = DateTimeFormatter.ofPattern("MM/dd/yy hh:mm");
	       response.setDate(newsFeed.getDatePosted().format(formatPostedDate));
	       response.setUser(newsFeed.getUser()); 
	      
	       response.setImageUrl(newsFeed.getImageUrl());
	       response.setId(newsFeed.getId());
	      
	       
	       LikeVm likes = new LikeVm();
	       Long count = itemsRepository.findItemCount(newsFeed.getId(), Constants.NEWSFEED);
	       likes.setCount(count);
	       response.setLikes(likes);
	       
	       newsFeedVmResponse.addResponse(response);
	       return newsFeedVmResponse;
       }
       
       ).collect(Collectors.toList());
       
       return responseList;
        
    }


    /**
     * GET  /newsfeeds/:id : get the "id" newsfeed.
     *
     * @param id the id of the newsfeed to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the newsfeed, or with status 404 (Not Found)
     */
    @GetMapping("/newsfeeds/{id}")
    @Timed
    public ResponseEntity<Newsfeed> getNewsfeed(@PathVariable Long id) {
        log.debug("REST request to get Newsfeed : {}", id);
        Newsfeed newsfeed = newsfeedRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(newsfeed));
    }

    /**
     * DELETE  /newsfeeds/:id : delete the "id" newsfeed.
     *
     * @param id the id of the newsfeed to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/newsfeeds/{id}")
    @Timed
    public ResponseEntity<Void> deleteNewsfeed(@PathVariable Long id) {
        log.debug("REST request to delete Newsfeed : {}", id);
        newsfeedRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
    
    
	private NewsFeedVmResponse saveNewsFeed(Newsfeed feed) {
	   Newsfeed result = newsfeedRepository.save(feed);
        
       NewsFeedVmResponse newsFeedVmResponse = new NewsFeedVmResponse();
        
       NewsFeedVm response =  new NewsFeedVm();
       
       response.setComment(Collections.emptyList());
       response.setContent(result.getContent());
       
       DateTimeFormatter formatPostedDate = DateTimeFormatter.ofPattern("MM/dd/yy hh:mm");
       response.setDate(result.getDatePosted().format(formatPostedDate));
       
       response.setUser(result.getUser());
      
       response.setImageUrl(result.getImageUrl());
       response.setId(result.getId());
       
       LikeVm likes = new LikeVm();
       likes.setCount(0L);
       response.setLikes(likes);
       
       newsFeedVmResponse.addResponse(response);
        		
	  return newsFeedVmResponse;
	}
	
	private List<CommentVm> getCommentVm(Newsfeed newsFeed) {
		   List<Comment> comments = commentRepository.findByNewsfeed(newsFeed);
		   
	       List<CommentVm> commentList = new ArrayList<CommentVm>(comments.size());
	       
	      for(Comment comment: comments) 
	      {
	    	  CommentVm commentVm = new CommentVm();
	    	  commentVm.setContent(comment.getContent());
	    	  
	          DateTimeFormatter formatPostedDate = DateTimeFormatter.ofPattern("MM/dd/yy hh:mm");
	    	  commentVm.setDatePosted(comment.getDatePosted().format(formatPostedDate));
	    	  commentVm.setId(comment.getId());
	    	  commentList.add(commentVm);
	      }
		
	      return commentList;
	}
}
