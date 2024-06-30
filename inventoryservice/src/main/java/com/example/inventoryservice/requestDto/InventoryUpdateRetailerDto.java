package com.example.inventoryservice.requestDto;

import lombok.*;

/**
 * Data transfer object (DTO) representing Inventory update retailer dto.
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@ToString
public class InventoryUpdateRetailerDto {
    private String name;
    private int quantity;
}
