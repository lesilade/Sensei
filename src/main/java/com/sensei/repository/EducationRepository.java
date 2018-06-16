package com.sensei.repository;

import com.sensei.domain.Education;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;
import java.util.List;

/**
 * Spring Data JPA repository for the Education entity.
 */
@SuppressWarnings("unused")
@Repository
public interface EducationRepository extends JpaRepository<Education,Long> {

    @Query("select education from Education education where education.user.email = ?#{principal.username}")
    List<Education> findByUserIsCurrentUser();
    
}
