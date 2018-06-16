package com.sensei.repository;

import com.sensei.domain.CoachSession;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;
import java.util.List;

/**
 * Spring Data JPA repository for the CoachSession entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CoachSessionRepository extends JpaRepository<CoachSession,Long> {

    @Query("select coach_session from CoachSession coach_session where coach_session.user.login = ?#{principal.username}")
    List<CoachSession> findByUserIsCurrentUser();
    
}
