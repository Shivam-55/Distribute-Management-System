package com.example.inventoryservice.requestDto;

import lombok.*;


/**
 * Data transfer object (DTO) representing a stock.
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@ToString
public class StockDto {
    private int quantity;
    private String inventoryName;
}
