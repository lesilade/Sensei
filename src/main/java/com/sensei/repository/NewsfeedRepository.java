package com.sensei.repository;

import com.sensei.domain.Newsfeed;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;
import java.util.List;

/**
 * Spring Data JPA repository for the Newsfeed entity.
 */
@SuppressWarnings("unused")
@Repository
public interface NewsfeedRepository extends JpaRepository<Newsfeed,Long> {

    @Query("select newsfeed from Newsfeed newsfeed where newsfeed.user.login = ?#{principal.username}")
    List<Newsfeed> findByUserIsCurrentUser();
    
}
