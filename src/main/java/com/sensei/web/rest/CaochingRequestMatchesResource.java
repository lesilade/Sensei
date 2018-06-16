package com.sensei.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.sensei.domain.CaochingRequestMatches;
import com.sensei.domain.User;
import com.sensei.repository.CaochingRequestMatchesRepository;
import com.sensei.service.UserService;
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
 * REST controller for managing CaochingRequestMatches.
 */
@RestController
@RequestMapping("/api")
public class CaochingRequestMatchesResource {

    private final Logger log = LoggerFactory.getLogger(CaochingRequestMatchesResource.class);

    private static final String ENTITY_NAME = "caochingRequestMatches";

    private final CaochingRequestMatchesRepository caochingRequestMatchesRepository;
    private final UserService userService;

    public CaochingRequestMatchesResource(
    		CaochingRequestMatchesRepository caochingRequestMatchesRepository,
    		UserService userService) {
        this.caochingRequestMatchesRepository = caochingRequestMatchesRepository;
        this.userService = userService;
    }
    
    
    /**
     * GET  /caoching-request-matches/:id : get the "id" caochingRequestMatches.
     *
     * @param id the id of the caochingRequestMatches to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the caochingRequestMatches, or with status 404 (Not Found)
     */
    @GetMapping("users/dashboard")
    @Timed
    public ResponseEntity<List<CaochingRequestMatches>> getUserCaochingRequestMatches() {
        log.debug("REST request to get CaochingRequestMatches");
        
        User user = userService.getUserWithAuthorities();
        
        if(user == null)
        {
        	return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "badRequest", "User does not exist")).body(null);
        }
        
        List<CaochingRequestMatches> caochingRequestMatches = caochingRequestMatchesRepository.findByUser(user);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(caochingRequestMatches));
    }

    /**
     * POST  /caoching-request-matches : Create a new caochingRequestMatches.
     *
     * @param caochingRequestMatches the caochingRequestMatches to create
     * @return the ResponseEntity with status 201 (Created) and with body the new caochingRequestMatches, or with status 400 (Bad Request) if the caochingRequestMatches has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/caoching-request-matches")
    @Timed
    public ResponseEntity<CaochingRequestMatches> createCaochingRequestMatches(@Valid @RequestBody CaochingRequestMatches caochingRequestMatches) throws URISyntaxException {
        log.debug("REST request to save CaochingRequestMatches : {}", caochingRequestMatches);
        if (caochingRequestMatches.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new caochingRequestMatches cannot already have an ID")).body(null);
        }
        CaochingRequestMatches result = caochingRequestMatchesRepository.save(caochingRequestMatches);
        return ResponseEntity.created(new URI("/api/caoching-request-matches/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /caoching-request-matches : Updates an existing caochingRequestMatches.
     *
     * @param caochingRequestMatches the caochingRequestMatches to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated caochingRequestMatches,
     * or with status 400 (Bad Request) if the caochingRequestMatches is not valid,
     * or with status 500 (Internal Server Error) if the caochingRequestMatches couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/caoching-request-matches")
    @Timed
    public ResponseEntity<CaochingRequestMatches> updateCaochingRequestMatches(@Valid @RequestBody CaochingRequestMatches caochingRequestMatches) throws URISyntaxException {
        log.debug("REST request to update CaochingRequestMatches : {}", caochingRequestMatches);
        if (caochingRequestMatches.getId() == null) {
            return createCaochingRequestMatches(caochingRequestMatches);
        }
        CaochingRequestMatches result = caochingRequestMatchesRepository.save(caochingRequestMatches);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, caochingRequestMatches.getId().toString()))
            .body(result);
    }

    /**
     * GET  /caoching-request-matches : get all the caochingRequestMatches.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of caochingRequestMatches in body
     */
    @GetMapping("/caoching-request-matches")
    @Timed
    public List<CaochingRequestMatches> getAllCaochingRequestMatches() {
        log.debug("REST request to get all CaochingRequestMatches");
        return caochingRequestMatchesRepository.findAll();
    }

    /**
     * GET  /caoching-request-matches/:id : get the "id" caochingRequestMatches.
     *
     * @param id the id of the caochingRequestMatches to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the caochingRequestMatches, or with status 404 (Not Found)
     */
    @GetMapping("/caoching-request-matches/{id}")
    @Timed
    public ResponseEntity<CaochingRequestMatches> getCaochingRequestMatches(@PathVariable Long id) {
        log.debug("REST request to get CaochingRequestMatches : {}", id);
        CaochingRequestMatches caochingRequestMatches = caochingRequestMatchesRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(caochingRequestMatches));
    }
    
    /**
     * DELETE  /caoching-request-matches/:id : delete the "id" caochingRequestMatches.
     *
     * @param id the id of the caochingRequestMatches to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/caoching-request-matches/{id}")
    @Timed
    public ResponseEntity<Void> deleteCaochingRequestMatches(@PathVariable Long id) {
        log.debug("REST request to delete CaochingRequestMatches : {}", id);
        caochingRequestMatchesRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
