package com.sensei.repository;

import com.sensei.domain.CoachingPoint;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the CoachingPoint entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CoachingPointRepository extends JpaRepository<CoachingPoint,Long> {
    
}
