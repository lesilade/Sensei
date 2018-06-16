package com.sensei.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.sensei.domain.Connections;

import com.sensei.repository.ConnectionsRepository;
import com.sensei.web.rest.util.HeaderUtil;
import com.sensei.web.rest.util.ResponseUtil;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid; 
import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing Connections.
 */
@RestController
@RequestMapping("/api")
public class ConnectionsResource {

    private final Logger log = LoggerFactory.getLogger(ConnectionsResource.class);

    private static final String ENTITY_NAME = "connections";

    private final ConnectionsRepository connectionsRepository;

    public ConnectionsResource(ConnectionsRepository connectionsRepository) {
        this.connectionsRepository = connectionsRepository;
    }

    /**
     * POST  /connections : Create a new connections.
     *
     * @param connections the connections to create
     * @return the ResponseEntity with status 201 (Created) and with body the new connections, or with status 400 (Bad Request) if the connections has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/connections")
    @Timed
    public ResponseEntity<Connections> createConnections(@Valid @RequestBody Connections connections) throws URISyntaxException {
        log.debug("REST request to save Connections : {}", connections);
        if (connections.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new connections cannot already have an ID")).body(null);
        }
        Connections result = connectionsRepository.save(connections);
        return ResponseEntity.created(new URI("/api/connections/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /connections : Updates an existing connections.
     *
     * @param connections the connections to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated connections,
     * or with status 400 (Bad Request) if the connections is not valid,
     * or with status 500 (Internal Server Error) if the connections couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/connections")
    @Timed
    public ResponseEntity<Connections> updateConnections(@Valid @RequestBody Connections connections) throws URISyntaxException {
        log.debug("REST request to update Connections : {}", connections);
        if (connections.getId() == null) {
            return createConnections(connections);
        }
        Connections result = connectionsRepository.save(connections);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, connections.getId().toString()))
            .body(result);
    }

    /**
     * GET  /connections : get all the connections.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of connections in body
     */
    @GetMapping("/connections")
    @Timed
    public List<Connections> getAllConnections() {
        log.debug("REST request to get all Connections");
        return connectionsRepository.findAll();
    }

    /**
     * GET  /connections/:id : get the "id" connections.
     *
     * @param id the id of the connections to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the connections, or with status 404 (Not Found)
     */
    @GetMapping("/connections/{id}")
    @Timed
    public ResponseEntity<Connections> getConnections(@PathVariable Long id) {
        log.debug("REST request to get Connections : {}", id);
        Connections connections = connectionsRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(connections));
        
    }

    /**
     * DELETE  /connections/:id : delete the "id" connections.
     *
     * @param id the id of the connections to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/connections/{id}")
    @Timed
    public ResponseEntity<Void> deleteConnections(@PathVariable Long id) {
        log.debug("REST request to delete Connections : {}", id);
        connectionsRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}