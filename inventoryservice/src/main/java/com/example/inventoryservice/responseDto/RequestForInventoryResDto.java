package com.example.inventoryservice.responseDto;

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
public class RequestForInventoryResDto {
    private Long requesterUserId;
    private int quantity;
    private Long inventoryId;
    private String name;
    private Long approverUserId;
}
