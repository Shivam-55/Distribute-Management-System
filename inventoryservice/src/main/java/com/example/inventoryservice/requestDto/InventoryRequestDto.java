package com.example.inventoryservice.requestDto;

import lombok.*;


/**
 * Data transfer object (DTO) representing a request for inventory.
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@ToString
public class InventoryRequestDto {
    private String name;
    private int quantity;
    private double price;
}
