package com.example.inventoryservice.repository;

import com.example.inventoryservice.entities.Inventory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Repository interface for managing Inventory entities.
 */
@Repository
public interface InventoryRepo extends JpaRepository<Inventory,Long> {

    /**
     * Finds an inventory item by its name.
     * @param name the name of the inventory item to find
     * @return an Optional containing the found inventory item, or empty if not found
     */
    Optional<Inventory> findByName(String name);
}
