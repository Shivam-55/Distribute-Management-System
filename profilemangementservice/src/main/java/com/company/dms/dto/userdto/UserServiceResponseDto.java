package com.company.dms.dto.userdto;

import com.company.dms.customenum.IsApproved;
import com.company.dms.dto.RoleDto;
import com.company.dms.utils.ExcludeFromCodeCoverage;

import lombok.*;
/**
 * Data transfer object (DTO) representing the response from the user service.
 */
@ExcludeFromCodeCoverage
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserServiceResponseDto {
    private Long userId;
    private String username;
    private String password;
    private RoleDto role ;
    private Long mobile;
    private String email;
    private String area;
    private String address;
    private String pinCode;
    private IsApproved isApproved ;
    private boolean isActive;
}
