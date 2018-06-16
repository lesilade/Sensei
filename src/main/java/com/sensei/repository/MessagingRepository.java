package com.sensei.repository;

import com.sensei.domain.Messaging;

import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;
import java.util.List;

/**
 * Spring Data JPA repository for the Messaging entity.
 */
@SuppressWarnings("unused")
@Repository
public interface MessagingRepository extends JpaRepository<Messaging,Long> {

    @Query("select messaging from Messaging messaging where messaging.user.login = ?#{principal.username}")
    List<Messaging> findByUserIsCurrentUser();
    
}
