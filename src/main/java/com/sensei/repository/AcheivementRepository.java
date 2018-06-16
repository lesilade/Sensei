package com.sensei.repository;

import com.sensei.domain.Acheivement;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;
import java.util.List;

/**
 * Spring Data JPA repository for the Acheivement entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AcheivementRepository extends JpaRepository<Acheivement,Long> {

    @Query("select acheivement from Acheivement acheivement where acheivement.user.login = ?#{principal.username}")
    List<Acheivement> findByUserIsCurrentUser();
    
}
