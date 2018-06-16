package com.sensei.repository;

import com.sensei.domain.Belt;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the Belt entity.
 */
@SuppressWarnings("unused")
@Repository
public interface BeltRepository extends JpaRepository<Belt,Long> {
    
}
