package com.sensei.repository;

import com.sensei.domain.CaochingRequest;
import com.sensei.domain.TraineeAvailability;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.data.jpa.repository.*;
import java.util.List;

/**
 * Spring Data JPA repository for the TraineeAvailability entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TraineeAvailabilityRepository extends JpaRepository<TraineeAvailability,Long> {

    @Query("select trainee_availability from TraineeAvailability trainee_availability where trainee_availability.user.login = ?#{principal.username}")
    List<TraineeAvailability> findByUserIsCurrentUser();
    
    @Transactional
    @Modifying
    @Query(value = "DELETE FROM trainee_availability WHERE caoching_request_id = ?1", nativeQuery = true)
    void removeBycoachingRequest(Long id);
    
}
