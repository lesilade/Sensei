package com.sensei.repository;

import com.sensei.domain.CaochingRequest;
import com.sensei.domain.User;

import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;
import java.util.List;

/**
 * Spring Data JPA repository for the CaochingRequest entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CaochingRequestRepository extends JpaRepository<CaochingRequest,Long> {

    @Query("select caoching_request from CaochingRequest caoching_request where caoching_request.user.login = ?#{principal}")
    List<CaochingRequest> findByUserIsCurrentUser();
    List<CaochingRequest> findByUserAndStatus(User user, Integer status);
    
//    
//    @Query(value = "select * from caoching_request cr " + 
//			  "where cr.user_id = ?1 " + 
//			  "and cr.status = ?2 " + 
//			  "or cr.status = ?3 ", nativeQuery = true)
//  List<CaochingRequest> findByUserWithCoachingStatus(Long userId, Integer pendingStatus, Integer currenrtStatus);
    
}
