package com.company.dms.dto.inventorydto;

import lombok.*;

/**
 * Represents inventory dto
 */
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Builder
public class InventoryDto {
    private Long inventoryId;
    private String name;
    private double price;
}

