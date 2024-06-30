package com.example.inventoryservice.serviceImp;

import com.example.inventoryservice.entities.OrderHistory;
import com.example.inventoryservice.repository.OrderHistoryRepo;
import com.example.inventoryservice.service.OrderHistoryService;
import com.example.inventoryservice.utility.logger.LoggableClass;
import com.example.inventoryservice.utility.logger.LoggableMethod;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Implementation class for the OrderHistoryService interface.
 */
@LoggableClass
@Service
public class OrderHistoryServiceImp implements OrderHistoryService {
    private final OrderHistoryRepo orderHistoryRepo;
    @Autowired
    public OrderHistoryServiceImp(OrderHistoryRepo orderHistoryRepo){
        this.orderHistoryRepo = orderHistoryRepo;
    }
    /**
     * Saves order history.
     *
     * @param orderHistory The order history to be saved.
     * @return OrderHistory object representing the saved order history.
     */
    @LoggableMethod
    @Override
    public OrderHistory saveOrderHistory(OrderHistory orderHistory) {
        return orderHistoryRepo.save(orderHistory);
    }
}
