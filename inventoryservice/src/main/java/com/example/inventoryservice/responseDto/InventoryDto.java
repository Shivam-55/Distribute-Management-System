package com.example.inventoryservice.responseDto;

import lombok.*;

/**
 * Data transfer object (DTO) representing an inventory.
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@ToString
public class InventoryDto {
    private Long inventoryId;
    private String name;
    private double price;
}
