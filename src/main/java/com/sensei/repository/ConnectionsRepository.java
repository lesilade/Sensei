package com.sensei.repository;

import com.sensei.domain.Connections;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;
import java.util.List;

/**
 * Spring Data JPA repository for the Connections entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ConnectionsRepository extends JpaRepository<Connections,Long> {

    @Query("select connections from Connections connections where connections.user.login = ?#{principal.username}")
    List<Connections> findByUserIsCurrentUser();
    
}
