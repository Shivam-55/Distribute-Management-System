package com.company.dms.dto.userdto;

import com.company.dms.utils.ExcludeFromCodeCoverage;

import lombok.*;
/**
 * Data transfer object (DTO) representing user registration information.
 */
@ExcludeFromCodeCoverage
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserRegistrationDto {
    private String username;
    private String password;
    private String role ;
    private Long mobile;
    private String email;
    private String area;
    private Long pinCode;
}
