package com.sensei.repository;

import com.sensei.domain.Affiliation;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;
import java.util.List;

/**
 * Spring Data JPA repository for the Affiliation entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AffiliationRepository extends JpaRepository<Affiliation,Long> {

    @Query("select affiliation from Affiliation affiliation where affiliation.user.email = ?#{principal.username}")
    List<Affiliation> findByUserIsCurrentUser();
    
}
