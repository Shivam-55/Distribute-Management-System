package com.example.inventoryservice.entities;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.util.Date;

/**
 * Entity class representing the stock of inventory items.
 */
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@ToString
public class Stock {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long stockId;
    @Column(name = "user_id")
    private Long userId;
    @ManyToOne(cascade = CascadeType.REMOVE)
    @JoinColumn(name = "inventory_id")
    private Inventory inventory;
    private int quantity;
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "create_date", nullable = false, updatable = false)
    @CreationTimestamp
    private Date createdAt;
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "update_date", nullable = false, updatable = false)
    @UpdateTimestamp
    private Date updatedAt;
}
