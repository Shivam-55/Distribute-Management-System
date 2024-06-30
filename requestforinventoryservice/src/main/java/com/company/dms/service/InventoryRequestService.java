package com.company.dms.service;

import java.util.List;

import com.company.dms.dto.inventorydto.InventoryRequestDto;
import com.company.dms.dto.inventorydto.InventoryRequestInputDto;
import com.company.dms.entity.InventoryRequest;


/**
 * Service interface for managing inventory requests.
 */
public interface InventoryRequestService {
    /**
     * Raises a request for inventory based on the provided input DTO and user ID.
     *
     * @param inventoryRequestInputDto The input DTO containing request details.
     * @param userId                   The ID of the user raising the request.
     * @return The DTO representing the raised inventory request.
     */
    InventoryRequestDto raiseRequestForInventory(InventoryRequestInputDto inventoryRequestInputDto, Long userId);

    /**
     * Retrieves a list of strings representing requests for the specified user.
     *
     * @param userId The ID of the user to retrieve requests for.
     * @return A list of strings representing the user's requests.
     */
    List<String> getYourRequest(Long userId);

    /**
     * Retrieves all requests made by the specified user.
     *
     * @param userId The ID of the user to retrieve requests for.
     * @return A list of InventoryRequest objects representing the user's requests.
     */
    List<InventoryRequest> getAllUserRequest(Long userId);

    /**
     * Cancels a request for inventory based on the provided input DTO and user ID.
     *
     * @param inventoryRequestInputDto The input DTO containing request details.
     * @param userId                   The ID of the user canceling the request.
     * @return The DTO representing the canceled inventory request.
     */
    InventoryRequestDto cancelYourRequest(InventoryRequestInputDto inventoryRequestInputDto, Long userId);

    /**
     * Updates a request for inventory based on the provided input DTO and user ID.
     *
     * @param inventoryRequestInputDto The input DTO containing request details.
     * @param userId                   The ID of the user updating the request.
     * @return The DTO representing the updated inventory request.
     */
    InventoryRequestDto updateYourRequest(InventoryRequestInputDto inventoryRequestInputDto, Long userId);

    /**
     * Updates the status of an inventory request.
     *
     * @param inventoryRequestDto The DTO representing the inventory request to update.
     * @return The DTO representing the updated inventory request.
     */
    InventoryRequestDto updateRequestStatus(InventoryRequestDto inventoryRequestDto);

    /**
     * Retrieves the pending request for the specified requester and inventory.
     *
     * @param requesterId The ID of the requester.
     * @param inventoryId The ID of the inventory.
     * @return The DTO representing the pending inventory request, if found.
     */
    InventoryRequestDto getYourPendingRequest(Long requesterId, Long inventoryId);
}