package com.example.inventoryservice.responseDto;

import lombok.*;


/**
 * Data transfer object (DTO) representing a role response.
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@ToString
public class RoleResponseDto {
    private String name ;
    private String description;
}
