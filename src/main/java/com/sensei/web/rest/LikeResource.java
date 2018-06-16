package com.sensei.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.sensei.config.Constants;
import com.sensei.domain.Items;
import com.sensei.domain.Like;
import com.sensei.domain.Newsfeed;
import com.sensei.domain.User;
import com.sensei.repository.ItemsRepository;
import com.sensei.repository.LikeRepository;
import com.sensei.repository.NewsfeedRepository;
import com.sensei.repository.UserRepository;
import com.sensei.web.rest.util.HeaderUtil;
import com.sensei.web.rest.util.ResponseUtil;
import com.sensei.web.rest.vm.LikeVmRequest;
import com.sensei.web.rest.vm.LikeVmResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

/**
 * REST controller for managing Like.
 */
@RestController
@RequestMapping("/api")
public class LikeResource {

    private final Logger log = LoggerFactory.getLogger(LikeResource.class);

    private static final String ENTITY_NAME = "like";

    private final LikeRepository likeRepository;
    private final NewsfeedRepository newsfeedRepository;
    private final UserRepository userRepository;
    private final ItemsRepository itemsRepository;
    public LikeResource(LikeRepository likeRepository, NewsfeedRepository newsfeedRepository, UserRepository userRepository, ItemsRepository itemsRepository) {
        this.likeRepository = likeRepository;
        this.newsfeedRepository = newsfeedRepository;
        this.userRepository = userRepository;
        this.itemsRepository= itemsRepository;
    }

    /**
     * POST  /likes : Create a new like.
     *
     * @param like the like to create
     * @return the ResponseEntity with status 201 (Created) and with body the new like, or with status 400 (Bad Request) if the like has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("newsfeeds/likes")
    @Timed
    public ResponseEntity<LikeVmResponse> createLike(@Valid @RequestBody LikeVmRequest likeVmRequest) throws URISyntaxException {
        log.debug("REST request to save Like : {}", likeVmRequest.getLogin());
        /*if (like.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new like cannot already have an ID")).body(null);
        }*/
        
        Optional<User> user = userRepository.findOneByLogin(likeVmRequest.getLogin());
        
        if(!user.isPresent())
        {
          return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "User with username : " + likeVmRequest.getLogin()+ " does not exist ")).body(null);
           
        }
       
        // check if user already like the post
        List<Like> userLikexist = likeRepository.findUserLike(user.get().getId(), likeVmRequest.getNewsfeedId());
        
        if(userLikexist.size() == 1 )
        {
        	//user cannot like post more than 1.
        	
          likeRepository.delete(userLikexist.get(0));
        	
          Newsfeed newsFeed = newsfeedRepository.findOne(likeVmRequest.getNewsfeedId());
          
          LikeVmResponse likeVmResponse = new LikeVmResponse();
          likeVmResponse.setNewsfeedId(newsFeed.getId());
          
          Long count = itemsRepository.findItemCount(newsFeed.getId(), Constants.NEWSFEED);
          likeVmResponse.setCount(count);
          
          return ResponseEntity.ok().headers(HeaderUtil.createAlert(ENTITY_NAME, "user already like newsfeed")).body(likeVmResponse);
           
        }
        
        Newsfeed newsFeed = newsfeedRepository.findOne(likeVmRequest.getNewsfeedId());
        
        Like like = new Like();
        
        Items item = new Items();
        item.setItemId(likeVmRequest.getNewsfeedId().intValue());
        item.setType(Constants.NEWSFEED);
        
        Items savedItem = itemsRepository.save(item);
        
        like.setItems(savedItem);
       
        like.setUser(user.get());
        Like result = likeRepository.save(like);
        
        LikeVmResponse likeVmResponse = new LikeVmResponse();
        likeVmResponse.setNewsfeedId(savedItem.getItemId().longValue());
        
        Long count = itemsRepository.findItemCount(newsFeed.getId(), Constants.NEWSFEED);
        likeVmResponse.setCount(count);
        //likeVmResponse.setCount(count);
        
        return ResponseEntity.created(new URI("/api/likes/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(likeVmResponse);
    }

    /**
     * PUT  /likes : Updates an existing like.
     *
     * @param like the like to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated like,
     * or with status 400 (Bad Request) if the like is not valid,
     * or with status 500 (Internal Server Error) if the like couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
/*    @PutMapping("/likes")
    @Timed
    public ResponseEntity<Like> updateLike(@RequestBody LikeVmRequest likeVmRequest) throws URISyntaxException {
        log.debug("REST request to update Like : {}", likeVmRequest);
        
        if (like.getId() == null) {
            return createLike(like);
        }
        Like result = likeRepository.save(like);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, like.getId().toString()))
            .body(result);
    }*/

    /**
     * GET  /likes : get all the likes.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of likes in body
     */
    @GetMapping("/likes")
    @Timed
    public List<Like> getAllLikes() {
        log.debug("REST request to get all Likes");
        return likeRepository.findAll();
    }

    /**
     * GET  /likes/:id : get the "id" like.
     *
     * @param id the id of the like to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the like, or with status 404 (Not Found)
     */
    @GetMapping("/likes/{id}")
    @Timed
    public ResponseEntity<Like> getLike(@PathVariable Long id) {
        log.debug("REST request to get Like : {}", id);
        Like like = likeRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(like));
    }

    /**
     * DELETE  /likes/:id : delete the "id" like.
     *
     * @param id the id of the like to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/likes/{id}")
    @Timed
    public ResponseEntity<Void> deleteLike(@PathVariable Long id) {
        log.debug("REST request to delete Like : {}", id);
        likeRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
