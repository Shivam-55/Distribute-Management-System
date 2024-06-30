package com.example.inventoryservice.repository;

import com.example.inventoryservice.entities.OrderHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository interface for managing OrderHistory entities.
 */
@Repository
public interface OrderHistoryRepo extends JpaRepository<OrderHistory,Long> {
}
