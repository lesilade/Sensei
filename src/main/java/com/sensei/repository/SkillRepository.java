package com.sensei.repository;

import com.sensei.domain.Skill;
import com.sensei.domain.User;

import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;
import java.util.List;

/**
 * Spring Data JPA repository for the Skill entity.
 */
@SuppressWarnings("unused")
@Repository
public interface SkillRepository extends JpaRepository<Skill,Long> {

    @Query("select skill from Skill skill where skill.user.email = ?#{principal.username}")
    List<Skill> findByUserIsCurrentUser();
    
    List<Skill> findByUser(User user);
    
}
