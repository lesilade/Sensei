package com.sensei.repository;

import com.sensei.domain.CaochingRequest;
import com.sensei.domain.CaochingRequestMatches;
import com.sensei.domain.CoachingSession;
import com.sensei.domain.User;

import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;
import java.util.List;

/**
 * Spring Data JPA repository for the CaochingRequestMatches entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CaochingRequestMatchesRepository extends JpaRepository<CaochingRequestMatches,Long> {

    @Query("select caoching_request_matches from CaochingRequestMatches caoching_request_matches where caoching_request_matches.user.email = ?#{principal.username}")
    List<CaochingRequestMatches> findByUserIsCurrentUser();
    
    CaochingRequestMatches findByUserAndCaochingRequest(User user, CaochingRequest request);
    
    List<CaochingRequestMatches> findByUser(User user);
    
    
    @Query(value = "select * from caoching_request_matches crm " + 
			  "where crm.user_id = ?1 " + 
			  "and crm.status = ?2 ", nativeQuery = true)
   List<CaochingRequestMatches> findByUserAndStatus(Long userId, Integer pendingStatus);
    
    
    /**
     * Get all user that have the coaching request having the coachingRequestMaximumNumber specified.
     * @param coachingRequestMaximumNumber The number of coaching request a coach can receive.
     * @return list of user with the coachingRequestMaximumNumber specified.
     */
	  @Query(value = "select crm.user_id from caoching_request_matches crm " + 
			  "group by crm.user_id" + 
			  "having count(crm.user_id) < ?1 ", nativeQuery = true)
    List<User> findUserWithMaximumCoachingRequest(Long coachingRequestMaximumNumber);
    
}
