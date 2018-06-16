package com.sensei.repository;

import com.sensei.domain.Items;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the Items entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ItemsRepository extends JpaRepository<Items,Long> {
    
	  @Query(value = "SELECT COUNT(it.item_id) FROM items it WHERE it.item_id = ?1 and it.type = ?2", nativeQuery = true)
	  Long findItemCount(Long itemId, String type);
}
