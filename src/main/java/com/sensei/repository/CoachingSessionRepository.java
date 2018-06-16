package com.sensei.repository;

import com.sensei.domain.CaochingRequest;
import com.sensei.domain.CoachingSession;
import com.sensei.domain.User;

import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;
import java.util.List;

/**
 * Spring Data JPA repository for the CoachingSession entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CoachingSessionRepository extends JpaRepository<CoachingSession,Long> {

    @Query("select coaching_session from CoachingSession coaching_session where coaching_session.user.login = ?#{principal.username}")
    List<CoachingSession> findByUserIsCurrentUser();
    
    @Query(value = "select * from coaching_session cs " + 
			  "where cs.user_id = ?1 " + 
			  "and cs.status = ?2 " + 
			  "and cs.caoching_request_id  = ?3 ", nativeQuery = true)
    List<CoachingSession> findByRoleAndCaochingRequestAndStatus(Long userId, String role, Long requestId);
    
    //List<CoachingSession> findByUserAndStatusAndRole(User user, Integer status, String role);
    
    List<CoachingSession> findByUser(User user);
    
    List<CoachingSession> findByCaochingRequest(CaochingRequest request);
    
  
  @Query(value = "select * from coaching_session cs " + 
			  "where cs.user_id = ?1 " + 
			  "and cs.status = ?2 " + 
			  "and cs.user_role  = ?3 ", nativeQuery = true)
 List<CoachingSession> findByUserAndStatusAndRole(Long userId, Integer pendingStatus, String role);
  
 List<CoachingSession> findByUserAndStatus(User user, Integer status);
     
}
