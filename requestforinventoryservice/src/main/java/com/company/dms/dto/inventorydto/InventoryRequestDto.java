package com.company.dms.dto.inventorydto;

import lombok.*;
/**
 * Represents inventory request dto
 */
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Builder
public class InventoryRequestDto {
    private Long inventoryId;
    private String name;
    private int quantity;
    private Long requesterUserId;
    private Long approverUserId;
}
