package com.sensei.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.sensei.domain.CoachSession;

import com.sensei.repository.CoachSessionRepository;
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
 * REST controller for managing CoachSession.
 */
@RestController
@RequestMapping("/api")
public class CoachSessionResource {

    private final Logger log = LoggerFactory.getLogger(CoachSessionResource.class);

    private static final String ENTITY_NAME = "coachSession";

    private final CoachSessionRepository coachSessionRepository;

    public CoachSessionResource(CoachSessionRepository coachSessionRepository) {
        this.coachSessionRepository = coachSessionRepository;
    }

    /**
     * POST  /coach-sessions : Create a new coachSession.
     *
     * @param coachSession the coachSession to create
     * @return the ResponseEntity with status 201 (Created) and with body the new coachSession, or with status 400 (Bad Request) if the coachSession has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/coach-sessions")
    @Timed
    public ResponseEntity<CoachSession> createCoachSession(@Valid @RequestBody CoachSession coachSession) throws URISyntaxException {
        log.debug("REST request to save CoachSession : {}", coachSession);
        if (coachSession.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new coachSession cannot already have an ID")).body(null);
        }
        CoachSession result = coachSessionRepository.save(coachSession);
        return ResponseEntity.created(new URI("/api/coach-sessions/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /coach-sessions : Updates an existing coachSession.
     *
     * @param coachSession the coachSession to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated coachSession,
     * or with status 400 (Bad Request) if the coachSession is not valid,
     * or with status 500 (Internal Server Error) if the coachSession couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/coach-sessions")
    @Timed
    public ResponseEntity<CoachSession> updateCoachSession(@Valid @RequestBody CoachSession coachSession) throws URISyntaxException {
        log.debug("REST request to update CoachSession : {}", coachSession);
        if (coachSession.getId() == null) {
            return createCoachSession(coachSession);
        }
        CoachSession result = coachSessionRepository.save(coachSession);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, coachSession.getId().toString()))
            .body(result);
    }

    /**
     * GET  /coach-sessions : get all the coachSessions.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of coachSessions in body
     */
    @GetMapping("/coach-sessions")
    @Timed
    public List<CoachSession> getAllCoachSessions() {
        log.debug("REST request to get all CoachSessions");
        return coachSessionRepository.findAll();
    }

    /**
     * GET  /coach-sessions/:id : get the "id" coachSession.
     *
     * @param id the id of the coachSession to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the coachSession, or with status 404 (Not Found)
     */
    @GetMapping("/coach-sessions/{id}")
    @Timed
    public ResponseEntity<CoachSession> getCoachSession(@PathVariable Long id) {
        log.debug("REST request to get CoachSession : {}", id);
        CoachSession coachSession = coachSessionRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(coachSession));
    }

    /**
     * DELETE  /coach-sessions/:id : delete the "id" coachSession.
     *
     * @param id the id of the coachSession to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/coach-sessions/{id}")
    @Timed
    public ResponseEntity<Void> deleteCoachSession(@PathVariable Long id) {
        log.debug("REST request to delete CoachSession : {}", id);
        coachSessionRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}