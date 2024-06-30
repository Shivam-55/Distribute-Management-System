package com.example.inventoryservice.service;

import com.example.inventoryservice.entities.OrderHistory;

/**
 * Service interface for managing order history.
 */
public interface OrderHistoryService {

    /**
     * Saves an order history entry.
     * @param orderHistory the order history entry to save
     * @return the saved order history entry
     */
    OrderHistory saveOrderHistory(OrderHistory orderHistory);
}
