package com.sensei.repository;

import com.sensei.domain.Experience;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;
import java.util.List;

/**
 * Spring Data JPA repository for the Experience entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ExperienceRepository extends JpaRepository<Experience,Long> {

    @Query("select experience from Experience experience where experience.user.email = ?#{principal.username}")
    List<Experience> findByUserIsCurrentUser();
    
}
