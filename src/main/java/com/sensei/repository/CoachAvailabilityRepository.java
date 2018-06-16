package com.sensei.repository;

import com.sensei.domain.CoachAvailability;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;
import java.util.List;

/**
 * Spring Data JPA repository for the CoachAvailability entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CoachAvailabilityRepository extends JpaRepository<CoachAvailability,Long> {

    @Query("select coach_availability from CoachAvailability coach_availability where coach_availability.user.login = ?#{principal.username}")
    List<CoachAvailability> findByUserIsCurrentUser();
    
}
