package com.sensei.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.temporal.ChronoField;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
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
import com.sensei.domain.Affiliation;
import com.sensei.domain.Authority;
import com.sensei.domain.Education;
import com.sensei.domain.Experience;
import com.sensei.domain.Skill;
import com.sensei.domain.User;
import com.sensei.domain.UserProfile;
import com.sensei.repository.AffiliationRepository;
import com.sensei.repository.EducationRepository;
import com.sensei.repository.ExperienceRepository;
import com.sensei.repository.SkillRepository;
import com.sensei.repository.UserRepository;
import com.sensei.security.AuthoritiesConstants;
import com.sensei.service.MailService;
import com.sensei.service.UserService;
import com.sensei.service.dto.SkillDTO;
import com.sensei.service.dto.UserDTO;
import com.sensei.web.rest.util.HeaderUtil;
import com.sensei.web.rest.util.PaginationUtil;
import com.sensei.web.rest.util.ResponseUtil;
import com.sensei.web.rest.vm.AffiliationVM;
import com.sensei.web.rest.vm.EducationVM;
import com.sensei.web.rest.vm.ExperienceVM;
import com.sensei.web.rest.vm.ManagedUserVM;

import io.swagger.annotations.ApiParam;

/**
 * REST controller for managing users.
 * <p>
 * This class accesses the User entity, and needs to fetch its collection of authorities.
 * <p>
 * For a normal use-case, it would be better to have an eager relationship between User and Authority,
 * and send everything to the client side: there would be no View Model and DTO, a lot less code, and an outer-join
 * which would be good for performance.
 * <p>
 * We use a View Model and a DTO for 3 reasons:
 * <ul>
 * <li>We want to keep a lazy association between the user and the authorities, because people will
 * quite often do relationships with the user, and we don't want them to get the authorities all
 * the time for nothing (for performance reasons). This is the #1 goal: we should not impact our users'
 * application because of this use-case.</li>
 * <li> Not having an outer join causes n+1 requests to the database. This is not a real issue as
 * we have by default a second-level cache. This means on the first HTTP call we do the n+1 requests,
 * but then all authorities come from the cache, so in fact it's much better than doing an outer join
 * (which will get lots of data from the database, for each HTTP call).</li>
 * <li> As this manages users, for security reasons, we'd rather have a DTO layer.</li>
 * </ul>
 * <p>
 * Another option would be to have a specific JPA entity graph to handle this case.
 */
@RestController
@RequestMapping("/api")
public class UserResource {

    private final Logger log = LoggerFactory.getLogger(UserResource.class);

    private static final String ENTITY_NAME = "userManagement";

    private final UserRepository userRepository;

    private final MailService mailService;

    private final UserService userService;
    
    private final SkillRepository skillRepository;
    
    private final EducationRepository educationRepository;
    
    private final AffiliationRepository affiliationRepository;
    
    private final ExperienceRepository experienceRepository;
    
    public UserResource(UserRepository userRepository, MailService mailService,
            UserService userService, SkillRepository skillRepository,
            EducationRepository educationRepository, AffiliationRepository affiliationRepository,
            ExperienceRepository experienceRepository) {

        this.userRepository = userRepository;
        this.mailService = mailService;
        this.userService = userService;
        this.skillRepository = skillRepository;
        this.educationRepository = educationRepository;
        this.affiliationRepository = affiliationRepository;
        this.experienceRepository = experienceRepository;
    }
    /**
     * POST  /users  : Creates a new user.
     * <p>
     * Creates a new user if the login and email are not already used, and sends an
     * mail with an activation link.
     * The user needs to be activated on creation.
     *
     * @param managedUserVM the user to create
     * @return the ResponseEntity with status 201 (Created) and with body the new user, or with status 400 (Bad Request) if the login or email is already in use
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/users")
    @Timed
    @Secured(AuthoritiesConstants.ADMIN)
    public ResponseEntity createUser(@Valid @RequestBody ManagedUserVM managedUserVM) throws URISyntaxException {
        log.debug("REST request to save User : {}", managedUserVM);

        if (managedUserVM.getId() != null) {
            return ResponseEntity.badRequest()
                .headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new user cannot already have an ID"))
                .body(null);
        // Lowercase the user login before comparing with database
        } else if (userRepository.findOneByLogin(managedUserVM.getLogin().toLowerCase()).isPresent()) {
            return ResponseEntity.badRequest()
                .headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "userexists", "Login already in use"))
                .body(null);
        } else if (userRepository.findOneByEmail(managedUserVM.getEmail()).isPresent()) {
            return ResponseEntity.badRequest()
                .headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "emailexists", "Email already in use"))
                .body(null);
        } else {
            User newUser = userService.createUser(managedUserVM);
            mailService.sendCreationEmail(newUser);
            return ResponseEntity.created(new URI("/api/users/" + newUser.getLogin()))
                .headers(HeaderUtil.createAlert( "A user is created with identifier " + newUser.getLogin(), newUser.getLogin()))
                .body(newUser);
        }
    }

    /**
     * PUT  /users : Updates an existing User.
     *
     * @param managedUserVM the user to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated user,
     * or with status 400 (Bad Request) if the login or email is already in use,
     * or with status 500 (Internal Server Error) if the user couldn't be updated
     */
    @PutMapping("/users")
    @Timed
    @Secured(AuthoritiesConstants.ADMIN)
    public ResponseEntity<UserDTO> updateUser(@Valid @RequestBody ManagedUserVM managedUserVM) {
        log.debug("REST request to update User : {}", managedUserVM);
        Optional<User> existingUser = userRepository.findOneByEmail(managedUserVM.getEmail());
        if (existingUser.isPresent() && (!existingUser.get().getId().equals(managedUserVM.getId()))) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "emailexists", "Email already in use")).body(null);
        }
        existingUser = userRepository.findOneByLogin(managedUserVM.getLogin().toLowerCase());
        if (existingUser.isPresent() && (!existingUser.get().getId().equals(managedUserVM.getId()))) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "userexists", "Login already in use")).body(null);
        }
        Optional<UserDTO> updatedUser = userService.updateUser(managedUserVM);

        return ResponseUtil.wrapOrNotFound(updatedUser,
            HeaderUtil.createAlert("A user is updated with identifier " + managedUserVM.getLogin(), managedUserVM.getLogin()));
    }

    /**
     * GET  /users : get all users.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and with body all users
     */
    @GetMapping("/users")
    @Timed
    public ResponseEntity<List<UserDTO>> getAllUsers(@ApiParam Pageable pageable) {
        final Page<UserDTO> page = userService.getAllManagedUsers(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/users");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * @return a string list of the all of the roles
     */
    @GetMapping("/users/authorities")
    @Timed
    @Secured(AuthoritiesConstants.ADMIN)
    public List<String> getAuthorities() {
        return userService.getAuthorities();
    }
    
    
    /**
     * GET  /users/:login : get the "login" user.
     *
     * @param login the login of the user to find
     * @return the ResponseEntity with status 200 (OK) and with body the "login" user, or with status 404 (Not Found)
     */
    @GetMapping("/users/{login:" + Constants.LOGIN_REGEX + "}")
    @Timed
    public ResponseEntity<UserDTO> getUser(@PathVariable String login) {
        log.debug("REST request to get User : {}", login);
        return ResponseUtil.wrapOrNotFound(
            userService.getUserWithAuthoritiesByLogin(login)
                .map(UserDTO::new));
    }

    /**
     * DELETE /users/:login : delete the "login" User.
     *
     * @param login the login of the user to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/users/{login:" + Constants.LOGIN_REGEX + "}")
    @Timed
    @Secured(AuthoritiesConstants.ADMIN)
    public ResponseEntity<Void> deleteUser(@PathVariable String login) {
        log.debug("REST request to delete User: {}", login);
        userService.deleteUser(login);
        return ResponseEntity.ok().headers(HeaderUtil.createAlert( "A user is deleted with identifier " + login, login)).build();
    }
    
    @GetMapping("/users/profile")
    @Timed
    public ResponseEntity<UserProfile> getUserProfile() {
        
    	log.debug("REST request to update User : {}");
    	
    	UserProfile userProfile = new UserProfile();
    	List<Skill> skills = skillRepository.findByUserIsCurrentUser();
    	List<Experience> experiences = experienceRepository.findByUserIsCurrentUser();
    	List<Education>  educations = educationRepository.findByUserIsCurrentUser();
    	List<Affiliation> affiliations = affiliationRepository.findByUserIsCurrentUser();

    	userProfile.setUser(userService.getUserWithAuthorities());
    	userProfile.setUserSkills(skills);
    	userProfile.setUserExperience(experiences);
    	userProfile.setUserEducation(educations);
    	userProfile.setUserAffiliation(affiliations);
    	
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(userProfile));
    }
    
    /**
     * POST  /skills : Create a new skill.
     *
     * @param experienceVM the experienceVM to create
     * @return the ResponseEntity with status 201 (Created) and with body the new skill, or with status 400 (Bad Request) if the skill has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("users/experience")
    @Timed
    public ResponseEntity<Experience> createExperience(@Valid @RequestBody ExperienceVM experienceVM) throws URISyntaxException {
    	 log.debug("REST request to save Experience : {}", experienceVM);
    	 
    	 User user = userService.getUserWithAuthorities();
    	 
    	 
         if (user == null) {
        	 return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "User with username " + experienceVM.getUsername() +" does not exist")).body(null);
         }
         
         final Experience experience = convertToExperience(experienceVM);
         experience.setUser(user);
         
         Experience result = experienceRepository.save(experience);
         return ResponseEntity.created(new URI("/api/experiences/" + result.getId()))
             .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
             .body(result);

    }
    
    /**
     * POST  /skills : Create a new skill.
     *
     * @param educationVM the educationVM to create
     * @return the ResponseEntity with status 201 (Created) and with body the new skill, or with status 400 (Bad Request) if the skill has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("users/education")
    @Timed
    public ResponseEntity<Education> createEducation(@Valid @RequestBody EducationVM educationVM) throws URISyntaxException {
    	 log.debug("REST request to save Experience : {}", educationVM);
    	 
    	 User user = userService.getUserWithAuthorities();
    	 
    	 
         if (user == null) {
        	 return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "User with username " + educationVM.getUsername() +" does not exist")).body(null);
         }
         
         final Education education = convertToEducation(educationVM);
         education.setUser(user);
         
         Education result = educationRepository.save(education);
         return ResponseEntity.created(new URI("/api/experiences/" + result.getId()))
             .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
             .body(result);

    }
    
    /**
     * POST  /skills : Create a new skill.
     *
     * @param affiliationVM the affiliationVM to create
     * @return the ResponseEntity with status 201 (Created) and with body the new skill, or with status 400 (Bad Request) if the skill has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("users/affiliation")
    @Timed
    public ResponseEntity<Affiliation> createAffiliation(@Valid @RequestBody AffiliationVM affiliationVM) throws URISyntaxException {
    	 log.debug("REST request to save Experience : {}", affiliationVM);
    	 
    	 Optional<User> user = userService.getUserByEmail(affiliationVM.getUsername());
    	 
    	 
         if (!user.isPresent()) {
        	 return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "User with username " + affiliationVM.getUsername() +" does not exist")).body(null);
         }
         
         Affiliation affiliation = convertToAffiliationVM(affiliationVM);
         
         affiliation.setUser(user.get());
         
         Affiliation result = affiliationRepository.save(affiliation);
         return ResponseEntity.created(new URI("/api/experiences/" + result.getId()))
             .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
             .body(result);

    }
    
    private Affiliation convertToAffiliationVM(AffiliationVM affiliationVM) {
		
    	Affiliation affiliation = new Affiliation();
    	
    	affiliation.setEnddate(convertDate(affiliationVM.getEnddate()));
    	affiliation.setFocusarea(affiliationVM.getFocusarea());
    	affiliation.setOrganization(affiliationVM.getOrganization());
    	affiliation.setStartdate(convertDate(affiliationVM.getStartdate()));
    	affiliation.setIscurrent(affiliationVM.getIscurrent());
    	affiliation.setRole(affiliationVM.getRole());
    	
		return affiliation;
	}
    
    private Education convertToEducation(EducationVM educationVM) {
		
    	Education education = new Education();
    	education.setDegree(educationVM.getDegree());
    	education.setEnddate(convertDate(educationVM.getEnddate()));
    	education.setIscurrent(educationVM.getIscurrent());
    	education.setMajorareaofstudy(educationVM.getMajorareaofstudy());
    	education.setMinorareaofstudy(educationVM.getMinorareaofstudy());
    	education.setSchool(educationVM.getSchool());
    	education.setStartdate(convertDate(educationVM.getStartdate()));

		return education;
	}
    
	private Experience convertToExperience(ExperienceVM experienceVM) {
		
    	Experience experience = new Experience();
    	
    	experience.setCity(experienceVM.getCity());
    	experience.setContent(experienceVM.getContent());
    	experience.setIscurrent(experienceVM.getIscurrent());
    	experience.setStartdate(convertDate(experienceVM.getStartdate()));
    	experience.setState(experienceVM.getState());
    	experience.setTitle(experienceVM.getTitle());
    	experience.setOrganization(experienceVM.getOrganization());

    	final boolean isNotCurrentExperience = !experience.isIscurrent();
        if(isNotCurrentExperience)
        {
           experience.setEnddate(convertDate(experienceVM.getEnddate()));
        }
  	  	return experience;
	}
	
    private LocalDateTime convertDate(final String date)
    {
    	    String newDate = date.concat(" 00:00");
    	    
    	    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
    	    LocalDateTime ldt = LocalDateTime.parse(newDate, formatter);
    	    return ldt;
    }

	/**
     * POST  /skills : Create a new skill.
     *
     * @param skill the skill to create
     * @return the ResponseEntity with status 201 (Created) and with body the new skill, or with status 400 (Bad Request) if the skill has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("users/skills")
    @Timed
    public ResponseEntity<Skill> createSkill(@Valid @RequestBody SkillDTO skill) throws URISyntaxException {
        log.debug("REST request to save Skill : {}", skill);
        
        Optional<User> user = userService.getUserByEmail(skill.getUsername());
        
        if(user.isPresent())
        {
        	Skill newSkill = skill.convert(skill, user.get());
        	Skill result = skillRepository.save(newSkill);
        	
        	final Set<Authority> roles = user.get().getAuthorities();
        	boolean isCoach = false;
        	
        	for(Authority role: roles)
        	{
        		if(role.getName().equals("ROLE_COACH"))
        		{
        			isCoach = true;
        		}
        	}
        	
        	if(!isCoach)
        	{
        		Authority authority = new Authority();
        		authority.setName("ROLE_COACH");
        		roles.add(authority);
        		userService.updateUser(roles);
        	}
        	
            return ResponseEntity.created(new URI("/api/skills/" + result.getId()))
                    .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
                    .body(result);
        }
        else
        {
        	return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "User with username " + skill.getUsername() +" does not exist")).body(null);
        }

    }
    
    private UserDTO populateUserDTO(Optional<User> user) 
    {
		// TODO Auto-generated method stub
		return null;
	}
	/**
     * DELETE  users/skills/:id : delete the "id" skill.
     *
     * @param id the id of the skill to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("users/skills/{id}")
    @Timed
    public ResponseEntity<Void> deleteSkill(@PathVariable Long id) {
        log.debug("REST request to delete Skill : {}", id);
        skillRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
