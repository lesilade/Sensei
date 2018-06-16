package com.sensei.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.sensei.domain.Items;
import com.sensei.domain.TraineeAvailability;
import com.sensei.domain.User;
import com.sensei.domain.UserConnections;


/**
 * Spring Data JPA repository for the UserConnections entity.
 */
@SuppressWarnings("unused")
@Repository
public interface UserConnectionsRepository extends JpaRepository<UserConnections,Long> {
	
	  @Query(value = "select * from userconnections uc " + 
	  				  "join items it on uc.items_id = it.id " + 
	  				  "where uc.user_id = ?1 " + 
	  				  "and it.item_id = ?2 and it.type = ?3", nativeQuery = true)
	  List<UserConnections> findUserConnections(Long userId, Long itemId, String itemType);
	  
	  @Query(value = "select * from userconnections uc " + 
				  		"join items it on uc.items_id = it.id " + 
				  		"where uc.user_id = ?1 " + 
				  		"and it.type = ?2 and uc.status = ?3", nativeQuery = true)
	  List<UserConnections> findUserConnectionsFollowing(Long userId, String itemType, int status);
	  
	  @Query(value = "select * from userconnections uc " + 
		  		"join items it on uc.items_id = it.id " + 
		  		"where it.item_id = ?1 " + 
		  		"and it.type = ?2 and uc.status = ?3", nativeQuery = true)
	  List<UserConnections> findUserConnectionsFollowers(Long connectionUserId, String itemType, int status);
	  
	  @Query(value = "select * from userconnections uc " + 
				  "join items it on uc.items_id = it.id " + 
				  "where uc.user_id = ?1 " + 
				  "and it.item_id = ?2 and it.type = ?3 and uc.status = ?4", nativeQuery = true)
	  List<UserConnections> findUserConnections(Long userId, Long itemId, String itemType, int status);
	
}
