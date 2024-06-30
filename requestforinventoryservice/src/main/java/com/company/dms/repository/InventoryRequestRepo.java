package com.company.dms.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.company.dms.customenum.Status;
import com.company.dms.entity.InventoryRequest;

import java.util.List;
import java.util.Optional;


/**
 * Repository interface for managing inventory requests in the database.
 */
@Repository
public interface InventoryRequestRepo extends JpaRepository<InventoryRequest, Long> {

    /**
     * Finds an inventory request by user ID, inventory ID, and status.
     *
     * @param userId      The ID of the user associated with the request.
     * @param inventoryId The ID of the inventory associated with the request.
     * @param status      The status of the request.
     * @return An Optional containing the matching InventoryRequest, if found.
     */
    Optional<InventoryRequest> findByUserIdAndInventoryIdAndStatus(Long userId, Long inventoryId, Status status);

    /**
     * Finds all inventory requests by user ID.
     *
     * @param userId The ID of the user associated with the requests.
     * @return An Optional containing a list of matching InventoryRequests, if any are found.
     */
    Optional<List<InventoryRequest>> findByUserId(Long userId);

    /**
     * Finds all inventory requests by user ID.
     *
     * @param userId The ID of the user associated with the requests.
     * @return An Optional containing a list of matching InventoryRequests, if any are found.
     */
    Optional<List<InventoryRequest>> findByUserId2(Long userId);

    /**
     * Finds an inventory request by user ID, inventory ID, status, and quantity.
     *
     * @param userId      The ID of the user associated with the request.
     * @param inventoryId The ID of the inventory associated with the request.
     * @param status      The status of the request.
     * @param quantity    The quantity of the inventory requested.
     * @return An Optional containing the matching InventoryRequest, if found.
     */
    Optional<InventoryRequest> findByUserIdAndInventoryIdAndStatusAndQuantity(Long userId, Long inventoryId, Status status, int quantity);
}