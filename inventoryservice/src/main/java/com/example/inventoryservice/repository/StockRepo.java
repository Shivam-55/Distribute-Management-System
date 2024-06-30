package com.example.inventoryservice.repository;

import com.example.inventoryservice.entities.Inventory;
import com.example.inventoryservice.entities.Stock;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Repository interface for managing Stock entities.
 */
@Repository
public interface StockRepo extends JpaRepository<Stock,Long> {

    /**
     * Finds a stock item by user ID and inventory.
     * @param userId the ID of the user
     * @param inventory the inventory item
     * @return an Optional containing the found stock item, or empty if not found
     */
    Optional<Stock> findByUserIdAndInventory(Long userId, Inventory inventory);

    /**
     * Deletes all stock items associated with a specific inventory item.
     * @param inventory the inventory item
     * @return the list of deleted stock items
     */
    @Transactional
    List<Stock> deleteAllByInventory(Inventory inventory);

    /**
     * Finds all stock items associated with a specific user.
     * @param userId the ID of the user
     * @return an Optional containing the list of found stock items, or empty if not found
     */
    Optional<List<Stock>> findByUserId(Long userId);
}
