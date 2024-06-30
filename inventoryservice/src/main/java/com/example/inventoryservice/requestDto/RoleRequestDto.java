package com.example.inventoryservice.requestDto;

import lombok.*;

/**
 * Data transfer object (DTO) representing a role.
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@ToString
public class RoleRequestDto {
    private Long roleId;
    private String name ;
    private String description;
}
