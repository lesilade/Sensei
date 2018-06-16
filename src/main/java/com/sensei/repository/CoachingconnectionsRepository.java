package com.sensei.repository;

import com.sensei.domain.Coachingconnections;
import com.sensei.domain.User;

import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;
import java.util.List;

/**
 * Spring Data JPA repository for the Coachingconnections entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CoachingconnectionsRepository extends JpaRepository<Coachingconnections,Long> {

    List<Coachingconnections> findByUser(User user);
}
