package com.example.inventoryservice.service;

import com.example.inventoryservice.entities.Inventory;
import com.example.inventoryservice.entities.OrderHistory;
import com.example.inventoryservice.entities.Stock;
import com.example.inventoryservice.requestDto.InventoryUpdateRetailerDto;
import com.example.inventoryservice.requestDto.RequestForInventoryDto;
import com.example.inventoryservice.requestDto.StockDto;

import java.util.List;
import java.util.Optional;


/**
 * Service interface for managing available stock.
 */
public interface StockAvailableService {

    /**
     * Saves a stock item.
     * @param stock the stock item to save
     */
    void saveStock(Stock stock);

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
    List<Stock> deleteByInventory(Inventory inventory);

    /**
     * Adds quantity to the available stock.
     * @param inventory the inventory item
     * @param quantity the quantity to add
     * @return the updated total quantity of the inventory item
     */
    int addToStockAvailable(Inventory inventory, int quantity);

    /**
     * Views all available stock items for a specific user.
     * @param userId the ID of the user
     * @return the list of available stock items for the user
     */
    List<Stock> viewAllStockAvailable(Long userId);

    /**
     * Views the stock item for a specific inventory item.
     * @param inventoryName the name of the inventory item
     * @param userId the ID of the user
     * @return the DTO representation of the stock item
     */
    StockDto viewXStock(String inventoryName, Long userId);

    /**
     * Approves a request for inventory and updates stock accordingly.
     * @param approverUserId the ID of the user approving the request
     * @param requestForInventoryDto the request for inventory DTO
     * @return the order history entry for the approved request
     */
    OrderHistory approveRequest(Long approverUserId, RequestForInventoryDto requestForInventoryDto);

    /**
     * Updates stock for a retailer.
     * @param userId the ID of the retailer
     * @param inventoryUpdateRetailerDto the DTO containing update information
     * @return the updated stock DTO for the retailer
     */
    StockDto stockUpdateForRetailer(Long userId, InventoryUpdateRetailerDto inventoryUpdateRetailerDto);
}
