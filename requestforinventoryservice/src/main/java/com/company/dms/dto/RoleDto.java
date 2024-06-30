package com.company.dms.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
/**
 * Represents role dto
 */
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Builder
public class RoleDto {
    @JsonProperty("roleId")
    private Long roleId;
    private String name ;
    private String description;
}

