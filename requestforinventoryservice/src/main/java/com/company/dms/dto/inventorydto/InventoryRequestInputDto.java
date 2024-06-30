package com.company.dms.dto.inventorydto;

import lombok.*;
/**
 * Represents inventory input dto
 */
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Builder
public class InventoryRequestInputDto {
    private String name;
    private int quantity;
}
