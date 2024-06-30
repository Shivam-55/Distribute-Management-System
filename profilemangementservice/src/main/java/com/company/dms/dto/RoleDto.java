package com.company.dms.dto;
import com.company.dms.utils.ExcludeFromCodeCoverage;

import lombok.*;
@ExcludeFromCodeCoverage
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RoleDto {
    private Long roleId;
    private String name ;
    private String description;
}
