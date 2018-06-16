package com.sensei.repository;

import com.sensei.domain.Like;
import com.sensei.domain.User;
import com.sensei.domain.UserConnections;

import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;
import java.util.List;

/**
 * Spring Data JPA repository for the Like entity.
 */
@SuppressWarnings("unused")
@Repository
public interface LikeRepository extends JpaRepository<Like,Long> {

    @Query("select jhi_like from Like jhi_like where jhi_like.user.login = ?#{principal.username}")
    List<Like> findByUserIsCurrentUser();
    
	 // @Query(value = "select * from user_like where user_id = ?1 and ", nativeQuery = true)
	 // Long findUserLike(Long userId);
	  
	  @Query(value = "select * from user_like ul " + 
				  "join items it on ul.items_id = it.id " + 
				  "where ul.user_id = ?1 " + 
				  "and it.item_id = ?2", nativeQuery = true)
	  List<Like> findUserLike(Long userId, Long itemId);
	  
	  @Query(value = "delete user_like ul " + 
			  "join items it on ul.items_id = it.id " + 
			  "where ul.user_id = ?1 " + 
			  "and it.item_id = ?2", nativeQuery = true)
    void deleteUserLike(Long userId, Long itemId);
    
}
