package com.example.inventoryservice.responseDto;

import lombok.*;

/**
 * Data transfer object (DTO) representing an inventory response.
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@ToString
public class InventoryResponseDto {
    private String name;
    private double price;
    private int quantity ;
}
