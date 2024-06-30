package com.example.inventoryservice.service;

import com.example.inventoryservice.entities.Inventory;
import com.example.inventoryservice.entities.Stock;
import com.example.inventoryservice.requestDto.InventoryRequestDto;
import com.example.inventoryservice.requestDto.RequestForInventoryDto;
import com.example.inventoryservice.responseDto.InventoryDto;
import com.example.inventoryservice.responseDto.InventoryResponseDto;

import java.util.List;

/**
 * Interface for managing inventory operations.
 */
public interface InventoryService {

    /**
     * Adds inventory to the system.
     * @param inventory the inventory to add
     * @param role the role of the user adding the inventory
     * @return the response containing information about the added inventory
     */
    InventoryResponseDto addInventory(InventoryRequestDto inventory, String role);

    /**
     * Removes inventory from the system.
     * @param inventoryName the name of the inventory to remove
     * @param role the role of the user removing the inventory
     * @return the list of removed stock items
     */
    List<Stock> removeInventory(RequestForInventoryDto inventoryName, String role);

    /**
     * Finds inventory by name.
     * @param inventoryName the name of the inventory to find
     * @return the found inventory
     */
    Inventory findInventoryByName(String inventoryName);

    /**
     * Finds inventory DTO by name.
     * @param inventoryName the name of the inventory to find
     * @return the DTO representation of the found inventory
     */
    InventoryDto findInventoryDtoByName(String inventoryName);
}