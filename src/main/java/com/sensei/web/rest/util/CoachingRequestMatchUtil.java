package com.sensei.web.rest.util;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.sensei.config.Constants;
import com.sensei.domain.CaochingRequest;
import com.sensei.domain.CaochingRequestMatches;
import com.sensei.domain.CoachMatchResult;
import com.sensei.domain.RequestMatchStatus;
import com.sensei.domain.User;
import com.sensei.repository.CaochingRequestMatchesRepository;
import com.sensei.repository.UserRepository;
import com.sensei.service.MailService;


@Component
public class CoachingRequestMatchUtil 
{

	private final Logger log = LoggerFactory.getLogger(CoachingRequestMatchUtil.class);
	private final UserRepository userRepository;
	private final CaochingRequestMatchesRepository caochingRequestMatchesRepository;
	private final MailService mailService;
	
	
	
	public CoachingRequestMatchUtil (UserRepository userRepository, CaochingRequestMatchesRepository caochingRequestMatchesRepositor,
			MailService mailService)
	{
		this.userRepository = userRepository;
		this.caochingRequestMatchesRepository = caochingRequestMatchesRepositor;
		this.mailService = mailService;
	}
	

	public void processCoachingRequest(CaochingRequest caochingRequest)
	{
		
		log.debug("Processing the coaching request : {}", caochingRequest);
		
		//match the request with coach
		List<CoachMatchResult> matchs = matchRequestWithCaoch(caochingRequest);
		
		
		//filter user based on the number of coaching request
		//List<User> userWithMax = caochingRequestMatchesRepository.findUserWithMaximumCoachingRequest(3);
		
		//filter out userWithMax from matchs before save to database
		
		log.debug("found matching coach with size : {}", matchs.size());
		
		//save the match
		matchs.stream().forEach(
				match -> {
					 CaochingRequestMatches caochingRequestMatches = new CaochingRequestMatches();
					 caochingRequestMatches.setRequestName(caochingRequest.getTopic());
					 
					 final Optional<User> user = userRepository.findOneByEmail(match.getLogin());
					 
					
					 User caoch = user.get();
					 caochingRequestMatches.setUser(caoch);
					 caochingRequestMatches.setStatus(RequestMatchStatus.PENDING.getSatus());
					 caochingRequestMatches.setCaochingRequest(caochingRequest);
					 
					 CaochingRequestMatches result = caochingRequestMatchesRepository.save(caochingRequestMatches);
					 //send email notification
					 mailService.sendCoachRequestEmail(caoch);
					 log.debug(" Saved coaching request match : {}", result);
				 }
				);
	}
	
	
	public void updateCoachingRequestMatch(CaochingRequest caochingRequest, User coach)
	{
		//process to remove the coach that rejected the request
		//check if request not accepted ie status is 0
		//send to more people exlude the coach that decline the request
	}

	private List<CoachMatchResult> matchRequestWithCaoch(CaochingRequest caochingRequest) {
		
		List<String> coach = Arrays.asList(Constants.COACH);
	    List<CoachMatchResult> results = userRepository.findCoachBySkill(coach, caochingRequest.getTopic(), caochingRequest.getUser().getId());
	    
	    //check closeby if it set
/*	    if(caochingRequest.getCloseBy())
	    {
	    }*/
	    
	    
	    return results;
	}
}
