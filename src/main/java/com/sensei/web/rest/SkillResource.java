package com.sensei.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.sensei.domain.Skill;
import com.sensei.domain.User;
import com.sensei.repository.SkillRepository;
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
 * REST controller for managing Skill.
 */
@RestController
@RequestMapping("/api")
public class SkillResource {

    private final Logger log = LoggerFactory.getLogger(SkillResource.class);

    private static final String ENTITY_NAME = "skill";

    private final SkillRepository skillRepository;
    private final UserService userService;

    public SkillResource(SkillRepository skillRepository, UserService userService) {
        this.skillRepository = skillRepository;
        this.userService = userService;
    }

    /**
     * POST  /skills : Create a new skill.
     *
     * @param skill the skill to create
     * @return the ResponseEntity with status 201 (Created) and with body the new skill, or with status 400 (Bad Request) if the skill has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/skills")
    @Timed
    public ResponseEntity<Skill> createSkill(@Valid @RequestBody Skill skill) throws URISyntaxException {
        log.debug("REST request to save Skill : {}", skill);
        if (skill.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new skill cannot already have an ID")).body(null);
        }
        Skill result = skillRepository.save(skill);
        return ResponseEntity.created(new URI("/api/skills/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /skills : Updates an existing skill.
     *
     * @param skill the skill to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated skill,
     * or with status 400 (Bad Request) if the skill is not valid,
     * or with status 500 (Internal Server Error) if the skill couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/skills/{id}")
    @Timed
    public ResponseEntity<Skill> updateSkill(@Valid @RequestBody Skill skill, @PathVariable Long id) throws URISyntaxException {
        log.debug("REST request to update Skill : {}, with ID {}", skill, id);
        Skill retreivedSkill = skillRepository.findOne(id);
        if (retreivedSkill == null) {
        	
        	log.debug("Skill does not Exist and will add the skill Skill : {}", skill);
            return createSkill(skill);
        }
        
        skill.setId(id);
        Skill result = skillRepository.save(skill);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, skill.getId().toString()))
            .body(result);
    }

    /**
     * GET  /skills : get all the skills.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of skills in body
     */
    @GetMapping("/skills")
    @Timed
    public List<Skill> getAllSkills() {
        log.debug("REST request to get all Skills");
        
        final User user = userService.getUserWithAuthorities();
        return skillRepository.findByUser(user);
    }

    /**
     * GET  /skills/:id : get the "id" skill.
     *
     * @param id the id of the skill to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the skill, or with status 404 (Not Found)
     */
    @GetMapping("/skills/{id}")
    @Timed
    public ResponseEntity<Skill> getSkill(@PathVariable Long id) {
        log.debug("REST request to get Skill : {}", id);
        Skill skill = skillRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(skill));
    }

    /**
     * DELETE  /skills/:id : delete the "id" skill.
     *
     * @param id the id of the skill to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/skills/{id}")
    @Timed
    public ResponseEntity<Void> deleteSkill(@PathVariable Long id) {
        log.debug("REST request to delete Skill : {}", id);
        skillRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
