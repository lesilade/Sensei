package com.sensei.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;
import java.util.Set;
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
import com.sensei.domain.CaochingRequest;
import com.sensei.domain.CaochingRequestMatches;
import com.sensei.domain.CoachingSession;
import com.sensei.domain.CoachingSessionStatus;
import com.sensei.domain.Coachingconnections;
import com.sensei.domain.ConnectionStatus;
import com.sensei.domain.RequestMatchStatus;
import com.sensei.domain.TimeOfDay;
import com.sensei.domain.TraineeAvailability;
import com.sensei.domain.User;
import com.sensei.repository.CaochingRequestMatchesRepository;
import com.sensei.repository.CaochingRequestRepository;
import com.sensei.repository.CoachingSessionRepository;
import com.sensei.repository.CoachingconnectionsRepository;
import com.sensei.repository.TraineeAvailabilityRepository;
import com.sensei.service.UserService;
import com.sensei.web.rest.util.CoachingRequestMatchUtil;
import com.sensei.web.rest.util.HeaderUtil;
import com.sensei.web.rest.util.ResponseUtil;
import com.sensei.web.rest.vm.CaochingRequestVM;
import com.sensei.web.rest.vm.CoachDashboardVM;
import com.sensei.web.rest.vm.CoachingRequestStatusVm;
import com.sensei.web.rest.vm.CoachingRequestVmResponse;
import com.sensei.web.rest.vm.CoachingSessionVmResponse;
import com.sensei.web.rest.vm.TraineeAvailabilityVM;;

/**
 * REST controller for managing CaochingRequest.
 */
@RestController
@RequestMapping("/api")
public class CaochingRequestResource {

    private final Logger log = LoggerFactory.getLogger(CaochingRequestResource.class);

    private static final String ENTITY_NAME = "caochingRequest";

    private final CaochingRequestRepository caochingRequestRepository;
    private final TraineeAvailabilityRepository traineeAvailabilityRepository;
    private final UserService userService;
   // private final CaochingRequestSender caochinRequestSender;
    private final CoachingRequestMatchUtil coachingRequestMatchUtil;
    private final CoachingSessionRepository coachingSessionRepository;
    private final CaochingRequestMatchesRepository caochingRequestMatchesRepository;
    
    private final CoachingconnectionsRepository coachingconnectionsRepository;
    
    public CaochingRequestResource(CaochingRequestRepository caochingRequestRepository,
    		TraineeAvailabilityRepository traineeAvailabilityRepository,
    		UserService userService,
    	   // CaochingRequestSender caochinRequestSender
    	    CoachingRequestMatchUtil coachingRequestMatchUtil,
    	    CoachingSessionRepository coachingSessionRepository,
    	    CaochingRequestMatchesRepository caochingRequestMatchesRepository,
    	    CoachingconnectionsRepository coachingconnectionsRepository
    		) 
    {
        this.caochingRequestRepository = caochingRequestRepository;
        this.traineeAvailabilityRepository = traineeAvailabilityRepository;
        this.userService = userService;
        this.coachingRequestMatchUtil = coachingRequestMatchUtil;
        this.coachingSessionRepository = coachingSessionRepository;
        this.caochingRequestMatchesRepository = caochingRequestMatchesRepository;
        this.coachingconnectionsRepository = coachingconnectionsRepository;
    }
    
    
    /**
     * POST  /caoching-requests : Create a new caochingRequest.
     *
     * @param caochingRequest the caochingRequest to create
     * @return the ResponseEntity with status 201 (Created) and with body the new caochingRequest, or with status 400 (Bad Request) if the caochingRequest has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/caoching-requests")
    @Timed
    public ResponseEntity<CoachingRequestVmResponse> createCaochingRequest(@Valid @RequestBody CaochingRequestVM caochingRequestVm) throws URISyntaxException {
        log.debug("REST request to save CaochingRequest : {}", caochingRequestVm);
         
        final User trainee = userService.getUserWithAuthorities();
        
        final CaochingRequest caochingRequest = new CaochingRequest();
        caochingRequest.setUser(trainee);
        
        final CaochingRequest cr = convertToCaochingRequest(caochingRequestVm, caochingRequest);
                
        final CaochingRequest savedCoachingRequest = caochingRequestRepository.save(cr);
        
        CoachingRequestVmResponse result = populateResponse(savedCoachingRequest);
       
        coachingRequestMatchUtil.processCoachingRequest(caochingRequest);
        
        
        return ResponseEntity.created(new URI("/api/caoching-requests/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }
    
    /**
     * POST  /caoching-requests : Create a new caochingRequest.
     *
     * @param caochingRequest the caochingRequest to create
     * @return the ResponseEntity with status 201 (Created) and with body the new caochingRequest, or with status 400 (Bad Request) if the caochingRequest has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/caoching-requests/availabities")
    @Timed
    public ResponseEntity<Set<TraineeAvailability>> addAvailability(@Valid @RequestBody List<TraineeAvailabilityVM> traineeAvailabilityVM) throws URISyntaxException {
        log.debug("REST request to save traineeAvailability : {}", traineeAvailabilityVM);
        
        //final User trainee = getUserTrainee(traineeAvailabilityVM.iterator().next());
        CaochingRequest caochingRequest = caochingRequestRepository.findOne(traineeAvailabilityVM.iterator().next().getCoachRequestId());
        Set<TraineeAvailability> response = saveAvailability(traineeAvailabilityVM, caochingRequest);
        
        
        return ResponseEntity.created(new URI("/api/caoching-requests/" + response.iterator().next().getId().longValue()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, response.iterator().next().getId().toString()))
            .body(response);
    }

	private User getUser(String username) 
	{
		User trainee = null;
        Optional<User> user = userService.getUserByEmail(username);
           
        if(user.isPresent())
        {
        	trainee = user.get();
        }
		return trainee;
	}
	
	private User getUserTrainee(TraineeAvailabilityVM traineeAvailabilityVM) 
	{
		User trainee = null;
        Optional<User> user = userService.getUserByEmail(traineeAvailabilityVM.getUsername());
           
        if(user.isPresent())
        {
        	trainee = user.get();
        }
		return trainee;
	}

    /**
     * PUT  /caoching-requests : Updates an existing caochingRequest.
     *
     * @param caochingRequest the caochingRequest to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated caochingRequest,
     * or with status 400 (Bad Request) if the caochingRequest is not valid,
     * or with status 500 (Internal Server Error) if the caochingRequest couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/caoching-requests/{id}")
    @Timed
    public ResponseEntity<CoachingRequestVmResponse> updateCaochingRequest(@Valid @RequestBody CaochingRequestVM caochingRequestVm, @PathVariable Long id) throws URISyntaxException {
        log.debug("REST request to update CaochingRequest : {}", caochingRequestVm);
        CaochingRequest caochingRequest = caochingRequestRepository.findOne(id);
        if (caochingRequest == null) {
            return createCaochingRequest(caochingRequestVm);
        }
        
        final User trainee = getUser(caochingRequestVm.getUsername());
       // caochingRequest.setId(id);
        caochingRequest.setUser(trainee);
        
        CaochingRequest coachingRequest = convertToCaochingRequest(caochingRequestVm, caochingRequest);
        CaochingRequest updatedCoachingRequest = caochingRequestRepository.save(coachingRequest);
        
        CoachingRequestVmResponse result = populateResponse(updatedCoachingRequest);
        
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * GET  /caoching-requests : get all the caochingRequests.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of caochingRequests in body
     */
    @GetMapping("/caoching-requests")
    @Timed
    public List<CaochingRequest> getAllCaochingRequests() {
        log.debug("REST request to get all CaochingRequests");
        return caochingRequestRepository.findAll();
    }

    /**
     * GET  /caoching-requests/:id : get the "id" caochingRequest.
     *
     * @param id the id of the caochingRequest to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the caochingRequest, or with status 404 (Not Found)
     */
    @GetMapping("/caoching-requests/{id}")
    @Timed
    public ResponseEntity<CaochingRequest> getCaochingRequest(@PathVariable Long id) {
        log.debug("REST request to get CaochingRequest : {}", id);
        CaochingRequest caochingRequest = caochingRequestRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(caochingRequest));
    }

    /**
     * DELETE  /caoching-requests/:id : delete the "id" caochingRequest.
     *
     * @param id the id of the caochingRequest to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/caoching-requests/{id}")
    @Timed
    public ResponseEntity<Void> deleteCaochingRequest(@PathVariable Long id) {
        log.debug("REST request to delete CaochingRequest : {}", id);
       
        traineeAvailabilityRepository.removeBycoachingRequest(id);
        caochingRequestRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
    
    /**
     * POST  /caoching-requests : Create a new caochingRequest.
     *
     * @param caochingRequest the caochingRequest to create
     * @return the ResponseEntity with status 201 (Created) and with body the new caochingRequest, or with status 400 (Bad Request) if the caochingRequest has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/caoching-requests/coachResponse")
    @Timed
    public ResponseEntity<CoachingSessionVmResponse> processCoachResponse(@Valid @RequestBody CoachingRequestStatusVm coachingRequestStatusVm) throws URISyntaxException {
        log.debug("REST request to save CaochingRequest : {}", coachingRequestStatusVm);
        
        final CaochingRequest request = caochingRequestRepository.getOne(coachingRequestStatusVm.getCoachingRequestId());
        
        CoachingSession sessionResponse = null;
        
        final User coach = userService.getUserWithAuthorities();
        
        
        final List<CoachingSession> coachMaximumSession = coachingSessionRepository.findByUserAndStatus(coach, CoachingSessionStatus.INPROGRESS.getSatus());
        
        if(coachMaximumSession.size() == 3)
        {
        	return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "badRequest", "A Coach can only have 3 session at time")).body(null);
        }
        
        final User trainee = request.getUser();
        
        if(coachingRequestStatusVm.getIsAccepted())
        {
        	final List<CoachingSession> coachingRequestInSession = coachingSessionRepository.findByCaochingRequest(request);
        	
        	if(coachingRequestInSession.isEmpty())
        	{
        		//save a session for coach
            	CoachingSession coachSession = createCoachSession(request, coach);
            	sessionResponse = coachingSessionRepository.save(coachSession);
            	
            	CaochingRequestMatches match = caochingRequestMatchesRepository.findByUserAndCaochingRequest(coach,request);

            	match.setStatus(RequestMatchStatus.ACCEPT.getSatus());
            	caochingRequestMatchesRepository.save(match);

            	//save a session for training
                CoachingSession trainingSession = createTrainingSession(request, trainee);
                coachingSessionRepository.save(trainingSession);
                
                //create connection for both coach and training
                createCoachingConnections(coach, trainee);
                
                //update coaching request to current session/accepted
                
                request.setStatus(1);
                caochingRequestRepository.save(request);
                
                //update the remaining match to cantAccept status since one coach already accepted the request
                
        	}
        	else
        	{
        		processCantAccepRequest(request, coach);
        		
        		return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "badRequest", "A Coach already accepted the request")).body(null);
        	}

        }
        else
        {
        	processCantAccepRequest(request, coach);
    		
           coachingRequestMatchUtil.updateCoachingRequestMatch(request, coach);
           return ResponseEntity.ok().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "declineCoachingRequest", "Coach decline the request")).body(null);
        }
        
        CoachingSessionVmResponse response = populateResponse(sessionResponse);
        
        
        return ResponseEntity.created(new URI("/api/caoching-requests/" + response.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, response.getId().toString()))
            .body(response);
    }


	private void createCoachingConnections(final User coach, final User trainee) {
		
		Coachingconnections coachConnections = new Coachingconnections();
		coachConnections.setStatus(ConnectionStatus.ACCEPT.getSatus());
    	coachConnections.setUser(coach);
		coachConnections.setUsertwo(trainee);
		
		coachingconnectionsRepository.save(coachConnections);
		
		Coachingconnections traineeConnections = new Coachingconnections();
		traineeConnections.setStatus(ConnectionStatus.ACCEPT.getSatus());
		traineeConnections.setUser(trainee);
		traineeConnections.setUsertwo(coach);
		
		coachingconnectionsRepository.save(traineeConnections);
	}


	private void processCantAccepRequest(final CaochingRequest request, final User coach) {
		CaochingRequestMatches match = caochingRequestMatchesRepository.findByUserAndCaochingRequest(coach,request);
		
		if(match != null) 
		{
			match.setStatus(RequestMatchStatus.CANTACCEPT.getSatus());
			caochingRequestMatchesRepository.save(match);
		}

	}
    
    /**
     * GET  /caoching-request-matches/:id : get the "id" caochingRequestMatches.
     *
     * @param id the id of the caochingRequestMatches to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the caochingRequestMatches, or with status 404 (Not Found)
     */
    @GetMapping("caoching-requests/dashboard/pending")
    @Timed
    public ResponseEntity<List<CoachDashboardVM>> getUserPendingDashboard() {
        
        User user = userService.getUserWithAuthorities();
        
        if(user == null)
        {
        	return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "badRequest", "User does not exist")).body(null);
        }
        
        
        List<CaochingRequestMatches> caochingRequestMatches = caochingRequestMatchesRepository.findByUserAndStatus(user.getId(), RequestMatchStatus.PENDING.getSatus());
        
        List<CoachDashboardVM> response = populateDashboardData(caochingRequestMatches);
        
        
        System.out.println("getUserPendingDashboard response: ********" + response);
        
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(response));
    }
    
    /**
     * GET  /caoching-request-matches/:id : get the "id" caochingRequestMatches.
     *
     * @param id the id of the caochingRequestMatches to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the caochingRequestMatches, or with status 404 (Not Found)
     */
    @GetMapping("caoching-requests/dashboard/current")
    @Timed
    public ResponseEntity<List<CoachDashboardVM>> getUserCurrentDashboard() {
        log.debug("REST request to get Coach Dashboard");
        
        User user = userService.getUserWithAuthorities();
        
        if(user == null)
        {
        	return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "badRequest", "User does not exist")).body(null);
        }
        
        List<CoachingSession> caochingSession = coachingSessionRepository.findByUserAndStatusAndRole(user.getId(), CoachingSessionStatus.INPROGRESS.getSatus(), Constants.COACH);
        
        List<CoachDashboardVM> response = populateCurrentDashboardData(caochingSession);
        
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(response));
    }
    
    /**
     * GET  /caoching-requests/requestCoach : get the "id" caochingRequestMatches.
     *
     * @param id the id of the caochingRequestMatches to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the caochingRequestMatches, or with status 404 (Not Found)
     */
    @GetMapping("caoching-requests/request/pending")
    @Timed
    public ResponseEntity<List<CaochingRequest>> getUserPendingRequest() {
        log.debug("REST request to get CaochingRequest");
        
        User user = userService.getUserWithAuthorities();
        
        if(user == null)
        {
        	return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "badRequest", "User does not exist")).body(null);
        }
        
        final Integer pending = 0;
        List<CaochingRequest> response = caochingRequestRepository.findByUserAndStatus(user, pending);
        
//        final Integer pending = 0;
//        final Integer current = 1;
//        final Integer completed = 2;
//        List<CaochingRequest> response = caochingRequestRepository.findByUserWithCoachingStatus(user, pending, current);
        
        
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(response));
    }
    
    /**
     * GET  /caoching-requests/requestCoach : get the "id" caochingRequestMatches.
     *
     * @param id the id of the caochingRequestMatches to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the caochingRequestMatches, or with status 404 (Not Found)
     */
    @GetMapping("caoching-requests/request/current")
    @Timed
    public ResponseEntity<List<CoachingSession>> getUserUserCurrentRequestSession() {
        log.debug("REST request to get CaochingRequest");
        
        User user = userService.getUserWithAuthorities();
        
        if(user == null)
        {
        	return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "badRequest", "User does not exist")).body(null);
        }
        
        //final Integer pending = 0;
        //List<CaochingRequest> response = caochingRequestRepository.findByUserAndStatus(user, pending);
        
        List<CoachingSession> caochingSession = coachingSessionRepository.findByUserAndStatusAndRole(user.getId(), CoachingSessionStatus.INPROGRESS.getSatus(), Constants.TRAINEE);
        
//        final Integer pending = 0;
//        final Integer current = 1;
//        final Integer completed = 2;
//        List<CaochingRequest> response = caochingRequestRepository.findByUserWithCoachingStatus(user, pending, current);
        
        
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(caochingSession));
    }


	private List<CoachDashboardVM> populateDashboardData(final List<CaochingRequestMatches> caochingRequestMatches)
	{
		List<CoachDashboardVM> responses = caochingRequestMatches.stream()
				.map(response -> { 
					
					CoachDashboardVM dashboard = new CoachDashboardVM();
					
					dashboard.setId(response.getId());
					dashboard.setCaochingRequest(response.getCaochingRequest());
					dashboard.setStatus(RequestMatchStatus.getValue(response.getStatus()));
					dashboard.setUser(response.getUser());
					dashboard.setRequestName(response.getRequestName());
					
					return dashboard;
					})
				.collect(Collectors.toList());
				
		return responses;
	}
	
	private List<CoachDashboardVM> populateCurrentDashboardData(final List<CoachingSession> coachingSession)
	{
		List<CoachDashboardVM> responses = coachingSession.stream()
				.map(response -> { 
					
					CoachDashboardVM dashboard = new CoachDashboardVM();
					
					dashboard.setId(response.getId());
					dashboard.setCaochingRequest(response.getCaochingRequest());
					dashboard.setStatus(RequestMatchStatus.getValue(response.getStatus()));
					dashboard.setUser(response.getUser());
					dashboard.setRequestName(response.getTitle());
					
					return dashboard;
					})
				.collect(Collectors.toList());
				
		return responses;
	}


	private CoachingSessionVmResponse populateResponse(CoachingSession sessionResponse) {
	
		CoachingSessionVmResponse response = new CoachingSessionVmResponse();
		
		response.setId(sessionResponse.getId());
		response.setActive(sessionResponse.isActive());
		response.setCaochingRequest(sessionResponse.getCaochingRequest());
		response.setRole(sessionResponse.getRole());
		response.setStatus(CoachingSessionStatus.getValue(sessionResponse.getStatus()));
		response.setUser(sessionResponse.getUser());
		response.setTitle(sessionResponse.getTitle());
		
		
        DateTimeFormatter formatPostedDate = DateTimeFormatter.ofPattern("MM/dd/yy hh:mm");
  	  
		response.setStartDate(sessionResponse.getStartDate().format(formatPostedDate));
		response.setEndDate(sessionResponse.getEndDate().format(formatPostedDate));
		
		return response;
	}


	private CoachingSession createTrainingSession(final CaochingRequest request, final User trainee) {
		
		CoachingSession session = new CoachingSession();
		
		session.setStatus(CoachingSessionStatus.INPROGRESS.getSatus());
		session.setActive(true);
		session.setTitle(request.getTopic());
		session.setRole(Constants.TRAINEE);
		session.setUser(trainee);
		session.setCaochingRequest(request);
		
		LocalDateTime startDate = LocalDateTime.now();
		session.setStartDate(startDate);
		LocalDateTime endDate = startDate.plus(request.getDuration().intValue(), ChronoUnit.WEEKS);
		session.setEndDate(endDate);
		
		return session;
	}
	
	private CoachingSession createCoachSession(final CaochingRequest request, final User coach) {
		
		CoachingSession session = new CoachingSession();
		
		session.setStatus(CoachingSessionStatus.INPROGRESS.getSatus());
		session.setActive(true);
		session.setTitle(request.getTopic());
		session.setRole(Constants.COACH);
		session.setUser(coach);
		session.setCaochingRequest(request);
		
		LocalDateTime startDate = LocalDateTime.now();
		session.setStartDate(startDate);
		LocalDateTime endDate = startDate.plus(request.getDuration().intValue(), ChronoUnit.WEEKS);
		session.setEndDate(endDate);
		
		return session;
	}
    
	private CoachingRequestVmResponse populateResponse(CaochingRequest result) {
		
		  CoachingRequestVmResponse coachingRequestVmResponse = new CoachingRequestVmResponse();
		  
		  coachingRequestVmResponse.setId(result.getId());
		  coachingRequestVmResponse.setDescription(result.getDescription());
		  coachingRequestVmResponse.setDuration(result.getDuration());
		  coachingRequestVmResponse.setUser(result.getUser());
		  coachingRequestVmResponse.setIndustry(result.getIndustry());
		  coachingRequestVmResponse.setSubtopic(result.getSubtopic());
		  coachingRequestVmResponse.setTopic(result.getTopic());
		  //coachingRequestVmResponse.getAvailability.addAll(result.getTraineeAvailabilities());
		
		  for(TraineeAvailability availability: result.getTraineeAvailabilities()) 
		  {
			  TraineeAvailabilityVM tvm = new TraineeAvailabilityVM();
			  tvm.setDay(availability.getDay());
			  tvm.setTimeofday(TimeOfDay.getValue(availability.getTimeofday()));
			  tvm.setUsername(availability.getUser().getLogin());
			  coachingRequestVmResponse.getAvailability().add(tvm);
		  }
		
		return coachingRequestVmResponse;
	}

	private CaochingRequest convertToCaochingRequest(CaochingRequestVM caochingRequestVm, CaochingRequest caochingRequest) {
        
        caochingRequest.setCloseBy(caochingRequestVm.getCloseBy());
        caochingRequest.setTopic(caochingRequestVm.getTopic());
        caochingRequest.setDescription(caochingRequestVm.getDescription());
        caochingRequest.setIndustry(caochingRequestVm.getIndustry());
        caochingRequest.setDuration(caochingRequestVm.getDuration());
        caochingRequest.setInNetwork(caochingRequestVm.getInNetwork());
        caochingRequest.setSubtopic(caochingRequestVm.getSubtopic());
        
		Set<TraineeAvailability> response = saveAvailability(caochingRequestVm.getAvailability(), caochingRequest);
                
        caochingRequest.setTraineeAvailabilities(response);
      
		return caochingRequest;
	}


	private Set<TraineeAvailability> saveAvailability(List<TraineeAvailabilityVM> traineeAvailabilityVM,
			CaochingRequest caochingRequest) {
		  Set<TraineeAvailability> response = traineeAvailabilityVM.stream().map(
        		avalability -> {
        			
        			  TraineeAvailability ta = new TraineeAvailability();
        			  
        			  ta.setDay(avalability.getDay());
        			  ta.setUser(caochingRequest.getUser());
        			  ta.setCaochingRequest(caochingRequest); 
        			  
        			  if(avalability.getTimeofday() != null)
        			  {
            			  if(avalability.getTimeofday().equalsIgnoreCase("morning"))
            			  {
            				  ta.setTimeofday(TimeOfDay.MORNING.getTimeCode());
            			  }
            			  else if(avalability.getTimeofday().equalsIgnoreCase("afternoon"))
            			  {
            				  ta.setTimeofday(TimeOfDay.AFTERNOON.getTimeCode());
            			  }
            			  else if(avalability.getTimeofday().equalsIgnoreCase("evening"))
            			  {
            				  ta.setTimeofday(TimeOfDay.EVENING.getTimeCode());
            			  }
        			  }
        			          			  
        			  TraineeAvailability savedTa = traineeAvailabilityRepository.save(ta);
        			  
        			  log.debug("saved CaochingRequest : {}", savedTa);
        			  
        			  return savedTa;
        			}
        		
        		).collect(Collectors.toSet());
		return response;
	}

}

