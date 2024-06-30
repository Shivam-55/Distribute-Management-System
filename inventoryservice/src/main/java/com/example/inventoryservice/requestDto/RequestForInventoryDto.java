package com.example.inventoryservice.requestDto;

import jakarta.validation.constraints.NotEmpty;
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
public class RequestForInventoryDto {
    @NotEmpty(message = "Inventory name must not be empty or null")
    private String name;
    private int quantity;
    private Long requesterUserId;
}

