package com.company.dms.dto.userdto;

import com.company.dms.utils.ExcludeFromCodeCoverage;

import lombok.*;
/**
 * Data transfer object (DTO) representing user data.
 */
@ExcludeFromCodeCoverage
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserDataDto {
    private Long userId;
    private String username;
    private String email;
    private String role;
    private String area;
    private String pinCode;
}
