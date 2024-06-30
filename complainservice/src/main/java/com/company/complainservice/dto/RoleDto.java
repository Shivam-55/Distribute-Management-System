package com.company.complainservice.dto;

import lombok.*;

/**
 * Data transfer object (DTO) for role information.
 */
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Builder
public class RoleDto {
    private Long roleId;
    private String name ;
    private String description;
}
