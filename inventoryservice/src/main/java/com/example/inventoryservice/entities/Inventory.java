package com.example.inventoryservice.entities;

import jakarta.persistence.*;
import lombok.*;

/**
 * Entity class representing an inventory item.
 */
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@ToString
public class Inventory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "inventory_id")
    private Long inventoryId;
    private String name;
    private double price;
}
