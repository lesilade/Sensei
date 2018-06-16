package com.sensei.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.codahale.metrics.annotation.Timed;
import com.sensei.config.Constants;
import com.sensei.domain.Connections;
import com.sensei.domain.Items;
import com.sensei.domain.User;
import com.sensei.domain.UserConnectionStatus;
import com.sensei.domain.UserConnections;
import com.sensei.repository.ConnectionsRepository;
import com.sensei.repository.ItemsRepository;
import com.sensei.repository.UserConnectionsRepository;
import com.sensei.repository.UserRepository;
import com.sensei.service.UserService;
import com.sensei.web.rest.util.HeaderUtil;
import com.sensei.web.rest.util.ResponseUtil;
import com.sensei.web.rest.vm.UserConnectionsVM;
import com.sensei.web.rest.vm.UserConnectionsVMResponse;

/**
 * REST controller for managing Connections.
 */
@RestController
@RequestMapping("/api/connections")
public class UserConnectionsResource {
	
	
    private final Logger log = LoggerFactory.getLogger(UserConnectionsResource.class);

    private static final String ENTITY_NAME = "connections";
    private final UserConnectionsRepository userConnectionsRepository;
    private final UserService userService;
    
    private final ItemsRepository itemsRepository;
    private final UserRepository userRepository;
    

    public UserConnectionsResource(
    		UserConnectionsRepository userConnectionsRepository,
    		UserService userService,
    		ItemsRepository itemsRepository,
    		UserRepository userRepository) 
    {
        this.userConnectionsRepository = userConnectionsRepository;
        this.userService = userService;
        this.itemsRepository = itemsRepository;
        this.userRepository = userRepository;
        
    }

    /**
     * POST  /connections : Create a new connections.
     *
     * @param connections the connections to create
     * @return the ResponseEntity with status 201 (Created) and with body the new connections, or with status 400 (Bad Request) if the connections has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/follow")
    @Timed
    public ResponseEntity<UserConnectionsVMResponse> createUserConnections(@Valid @RequestBody UserConnectionsVM userConnectionsVM) throws URISyntaxException {
        log.debug("REST request to save user connections : {}", userConnectionsVM);
        
   	
   	 Optional<User> connectToUser = userService.getUserByEmail(userConnectionsVM.getConnectionUsername());
     if (!connectToUser.isPresent()) {
    	 return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "User with username " + userConnectionsVM.getConnectionUsername() +" does not exist")).body(null);
     }
     
     
     User currentLoggedInUser = userService.getUserWithAuthorities(); 
     
     if(connectToUser.get().getId() == currentLoggedInUser.getId())
     {
    	 return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "User cannot connect to self")).body(null);
     }
     
     List<UserConnections> retrieveConnections = userConnectionsRepository.findUserConnections(currentLoggedInUser.getId(), connectToUser.get().getId(), Constants.FOLLOWING);
    
     UserConnections savedUserConnections = null;

     if(!retrieveConnections.isEmpty())
     {
    	 UserConnections retrievedConnection = retrieveConnections.get(0);
    	 
    	 if(UserConnectionStatus.UNFLLOW.getSatus() == retrievedConnection.getStatus().intValue())
    	 {
    		 retrievedConnection.setStatus(UserConnectionStatus.FOLLOW.getSatus());
    	 }
    	 else
    	 {
    		 retrievedConnection.setStatus(UserConnectionStatus.UNFLLOW.getSatus());
    	 }
    	 
    	 savedUserConnections = userConnectionsRepository.save(retrievedConnection);
     }
     else
     {
    	 Items savedItem = saveConnectionItem(connectToUser);
    	 
    	 UserConnections userConnections = new UserConnections();
         userConnections.setUser(currentLoggedInUser);
         userConnections.setItems(savedItem);
       	 userConnections.setStatus(UserConnectionStatus.FOLLOW.getSatus());
         
         savedUserConnections = userConnectionsRepository.save(userConnections);
     }
    
      UserConnectionsVMResponse result = populateResponse(savedUserConnections, connectToUser.get());
        
        
        return ResponseEntity.created(new URI("/api/connections/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }


	/**
     * GET  /connections : get all the connections.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of connections in body
     */
    @GetMapping("/following")
    @Timed
    public List<User> getUserConnectionsFollowing() {
        log.debug("REST request to get all Connections");
        
        User currentLoggedInUser = userService.getUserWithAuthorities(); 
        List<UserConnections> userConnections = userConnectionsRepository.findUserConnectionsFollowing(currentLoggedInUser.getId(), 
        		Constants.FOLLOWING, UserConnectionStatus.FOLLOW.getSatus());
        
        //get the ids for all the users following the current loggedIn user
        List<Long> userConnectionIds = userConnections.stream().map( v -> { return v.getItems().getItemId().longValue();}).collect(Collectors.toList());
        
        //retrieve all the users by ids
        List<User> users = userRepository.findByIdIn(userConnectionIds);
        
        return users;
    }
    
    /**
     * POST  /connections : Create a new connections.
     *
     * @param connections the connections to create
     * @return the ResponseEntity with status 201 (Created) and with body the new connections, or with status 400 (Bad Request) if the connections has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/following/unfollow")
    @Timed
    public ResponseEntity<User> unFollowUserConnectionsFollowers(@Valid @RequestBody UserConnectionsVM userConnectionsVM) throws URISyntaxException {
        log.debug("REST request to save user connections : {}", userConnectionsVM);
        
   	
	   	 Optional<User> connectToUser = userService.getUserByEmail(userConnectionsVM.getConnectionUsername());
	     if (!connectToUser.isPresent()) {
	    	 return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "User with username " + userConnectionsVM.getConnectionUsername() +" does not exist")).body(null);
	     }
	     
	     User currentLoggedInUser = userService.getUserWithAuthorities(); 
	     
	     List<UserConnections> retrieveConnections = userConnectionsRepository.findUserConnections(currentLoggedInUser.getId(), connectToUser.get().getId(),  
	    		 Constants.FOLLOWING, UserConnectionStatus.FOLLOW.getSatus()); 
	    
	     User user = null;
	     if(!retrieveConnections.isEmpty())
	     {
	    	 UserConnections retrievedConnection = retrieveConnections.get(0);
			 retrievedConnection.setStatus(UserConnectionStatus.UNFLLOW.getSatus());
			 UserConnections savedUserConnections = userConnectionsRepository.save(retrievedConnection);
			 
			 user = savedUserConnections.getUser();
	     }
	     
	     if(user != null)
	     {
	         return ResponseEntity.created(new URI("/api/connections/" + user.getId()))
	                 .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, user.getId().toString()))
	                 .body(user);
	     }
	     else
	     {
	    	 return ResponseEntity.badRequest()
	    			 .headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "User with email " + currentLoggedInUser.getEmail() +" is not a following user with email " + userConnectionsVM.getConnectionUsername()))
	    			 .body(user);
	     }
	     
        
    }
    
	/**
     * GET  /connections : get all the connections.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of connections in body
     */
    @GetMapping("/followers")
    @Timed
    public List<User> getUserConnectionsFollowers() {
        log.debug("REST request to get all Connections");
        
        User currentLoggedInUser = userService.getUserWithAuthorities(); 
        List<UserConnections> userConnections = userConnectionsRepository.findUserConnectionsFollowers(currentLoggedInUser.getId(), 
        		Constants.FOLLOWING, UserConnectionStatus.FOLLOW.getSatus());
        
        //get the ids for all the users following the current loggedIn user
        List<User> users = userConnections.stream().map( v -> { return v.getUser();}).collect(Collectors.toList());
        
        
        log.debug("User Connection followers {}", users.toString()); 
        return users;
    }
    
    
    /**
     * POST  /connections : Create a new connections.
     *
     * @param connections the connections to create
     * @return the ResponseEntity with status 201 (Created) and with body the new connections, or with status 400 (Bad Request) if the connections has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/followers/block")
    @Timed
    public ResponseEntity<User> blockUserConnectionsFollowers(@Valid @RequestBody UserConnectionsVM userConnectionsVM) throws URISyntaxException {
        log.debug("REST request to save user connections : {}", userConnectionsVM);
        
   	
	   	 Optional<User> connectToUser = userService.getUserByEmail(userConnectionsVM.getConnectionUsername());
	     if (!connectToUser.isPresent()) {
	    	 return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "User with username " + userConnectionsVM.getConnectionUsername() +" does not exist")).body(null);
	     }
	     
	     User currentLoggedInUser = userService.getUserWithAuthorities(); 
	     
	     List<UserConnections> retrieveConnections = userConnectionsRepository.findUserConnections(connectToUser.get().getId(), currentLoggedInUser.getId(), 
	    		 Constants.FOLLOWING, UserConnectionStatus.FOLLOW.getSatus()); 
	    
	     User user = null;
	     if(!retrieveConnections.isEmpty())
	     {
	    	 UserConnections retrievedConnection = retrieveConnections.get(0);
			 retrievedConnection.setStatus(UserConnectionStatus.UNFLLOW.getSatus());
			 UserConnections savedUserConnections = userConnectionsRepository.save(retrievedConnection);
			 
			 user = savedUserConnections.getUser();
	     }
	     
	     if(user != null)
	     {
	         return ResponseEntity.created(new URI("/api/connections/" + user.getId()))
	                 .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, user.getId().toString()))
	                 .body(user);
	     }
	     else
	     {
	    	 return ResponseEntity.badRequest()
	    			 .headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "User with email " + currentLoggedInUser.getEmail() +" is not a follower of user with email " + userConnectionsVM.getConnectionUsername()))
	    			 .body(user);
	     }
	     
        
    }
    
    
	private Items saveConnectionItem(Optional<User> connectToUser) {
		 
		 Items item = new Items();
		 
	     //current logged In user is following the userId that is populated as the ItemId
		 item.setItemId(connectToUser.get().getId().intValue());
		 item.setType(Constants.FOLLOWING);
		 
		 Items savedItem = itemsRepository.save(item);
		 return savedItem;
	}


   private UserConnectionsVMResponse populateResponse(UserConnections userConnections, User connectToUser) {
   	
   	UserConnectionsVMResponse response = new UserConnectionsVMResponse();
   	response.setId(userConnections.getId());
   	response.setConnectionType(UserConnectionStatus.getValue(userConnections.getStatus()));
   	response.setUser(userConnections.getUser());
   	response.setConnectionUser(connectToUser);
   	response.setStatus(UserConnectionStatus.getValue(userConnections.getStatus()));
   	
   	return response;
	}

}
