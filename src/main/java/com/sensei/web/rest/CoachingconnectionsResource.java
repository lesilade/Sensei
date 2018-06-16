package com.sensei.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.sensei.domain.Coachingconnections;
import com.sensei.domain.ConnectionStatus;
import com.sensei.domain.User;
import com.sensei.repository.CoachingconnectionsRepository;
import com.sensei.repository.UserRepository;
import com.sensei.service.UserService;
import com.sensei.web.rest.util.HeaderUtil;
import com.sensei.web.rest.util.PaginationUtil;
import com.sensei.web.rest.util.ResponseUtil;
import com.sensei.web.rest.vm.ConnectionsVmRequest;
import com.sensei.web.rest.vm.ConnectionsVmResponse;

import io.swagger.annotations.ApiParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * REST controller for managing Coachingconnections.
 */
@RestController
@RequestMapping("/api")
public class CoachingconnectionsResource {

    private final Logger log = LoggerFactory.getLogger(CoachingconnectionsResource.class);

    private static final String ENTITY_NAME = "coachingconnections";

    private final CoachingconnectionsRepository coachingconnectionsRepository;
    private final UserService userService;
    
    private final UserRepository userRepository;

    public CoachingconnectionsResource(
    		CoachingconnectionsRepository coachingconnectionsRepository, 
    		UserRepository userRepository,
    		UserService userService) {
        this.coachingconnectionsRepository = coachingconnectionsRepository;
        this.userRepository = userRepository;
        this.userService = userService;
    }

    /**
     * POST  /coachingconnections : Create a new coachingconnections.
     *
     * @param coachingconnections the coachingconnections to create
     * @return the ResponseEntity with status 201 (Created) and with body the new coachingconnections, or with status 400 (Bad Request) if the coachingconnections has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
/*    @PostMapping("/coachingconnections")
    @Timed
    public ResponseEntity<ConnectionsVmResponse> createCoachingconnections(@Valid @RequestBody ConnectionsVmRequest request) throws URISyntaxException {
        log.debug("REST request to save Coachingconnections : {}", request);
        
        User user = userRepository.findOne(request.getCurrentUserId());
        User usertwo = userRepository.findOne(request.getUsertwoId());
        
        if(user == null)
        {
        	 return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "User with ID : " + request.getCurrentUserId() + " does not exist ")).body(null);
        }
        
        if(usertwo == null)
        {
        	 return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "User with ID : " + request.getUsertwoId() + " does not exist ")).body(null);
        }
        
        Coachingconnections coachingconnections = convertToConnections(request, user);
        
        
        Coachingconnections result = coachingconnectionsRepository.save(coachingconnections);
        
        ConnectionsVmResponse response = new ConnectionsVmResponse();
        response.setId(result.getId());
        response.setUsertwoId(result.getUsertwoid());
        response.setStatus(ConnectionStatus.getValue(result.getStatus()));
        response.setUser(result.getConnection());
        
        
        return ResponseEntity.created(new URI("/api/coachingconnections/" + response.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, response.getId().toString()))
            .body(response);
    }*/


    /**
     * PUT  /coachingconnections : Updates an existing coachingconnections.
     *
     * @param coachingconnections the coachingconnections to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated coachingconnections,
     * or with status 400 (Bad Request) if the coachingconnections is not valid,
     * or with status 500 (Internal Server Error) if the coachingconnections couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
/*    @PutMapping("/coachingconnections")
    @Timed
    public ResponseEntity<ConnectionsVmResponse> updateCoachingconnections(@Valid @RequestBody ConnectionsVmRequest request) throws URISyntaxException {
        log.debug("REST request to update Coachingconnections : {}", request);
               
        User user = userRepository.findOne(request.getCurrentUserId());
        
        if(user == null)
        {
        	 return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "User with ID : " + request.getCurrentUserId() + " does not exist ")).body(null);
        }
        
        Coachingconnections coachingconnections = convertToConnections(request, user);
        
        Coachingconnections result = coachingconnectionsRepository.save(coachingconnections);
        
        ConnectionsVmResponse response = new ConnectionsVmResponse();
        response.setId(result.getId());
        response.setUser(result.getConnection());
        response.setUsertwoId(result.getUsertwoid());
        
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, response.getId().toString()))
            .body(response);
    }*/

    /**
     * GET  /coachingconnections : get all the coachingconnections.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of coachingconnections in body
     */
    @GetMapping("/coachingconnections")
    @Timed
    public ResponseEntity<List<Coachingconnections>> getAllCoachingconnections(@ApiParam Pageable pageable) {
        log.debug("REST request to get a page of Coachingconnections");
        Page<Coachingconnections> page = coachingconnectionsRepository.findAll(pageable);
        
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/coachingconnections");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }
    
    /**
     * GET  /coachingconnections : get all the coachingconnections.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of coachingconnections in body
     */
    @GetMapping("/coaching/connections")
    @Timed
    public ResponseEntity<List<User>> getUserCoachingconnections() {
        log.debug("REST request to get a Coachingconnections");
        
        User userFound = userService.getUserWithAuthorities();
        
        if(userFound == null)
        {
        	return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "badRequest", "User does not exist")).body(null);
        }
        
        List<Coachingconnections> connections = coachingconnectionsRepository.findByUser(userFound);
        
        List<User> users = connections.stream().map( connection -> { return connection.getUsertwo();}).collect(Collectors.toList());
        
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(users));
    }

    /**
     * GET  /coachingconnections/:id : get the "id" coachingconnections.
     *
     * @param id the id of the coachingconnections to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the coachingconnections, or with status 404 (Not Found)
     */
    @GetMapping("/coachingconnections/{id}")
    @Timed
    public ResponseEntity<Coachingconnections> getCoachingconnections(@PathVariable Long id) {
        log.debug("REST request to get Coachingconnections : {}", id);
        Coachingconnections coachingconnections = coachingconnectionsRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(coachingconnections));
    }

    /**
     * DELETE  /coachingconnections/:id : delete the "id" coachingconnections.
     *
     * @param id the id of the coachingconnections to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/coachingconnections/{id}")
    @Timed
    public ResponseEntity<Void> deleteCoachingconnections(@PathVariable Long id) {
        log.debug("REST request to delete Coachingconnections : {}", id);
        coachingconnectionsRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
    
/*	private Coachingconnections convertToConnections(ConnectionsVmRequest request, User user) {
		Coachingconnections coachingconnections = new Coachingconnections();
        coachingconnections.setConnection(user);
        coachingconnections.setUsertwoid(request.getUsertwoId());
        coachingconnections.setStatus(ConnectionStatus.PENDING.getSatus());
		return coachingconnections;
	}*/
}