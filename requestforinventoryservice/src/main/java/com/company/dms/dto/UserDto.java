package com.company.dms.dto;

import com.company.dms.customenum.IsApproved;
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
public class UserDto {
    private Long userId ;
    private String username;
    private String password;
    @JsonProperty("role")
    private RoleDto role ;
    private Long mobile;
    private String email;
    private String area;
    private String address;
    private int pinCode;
    private IsApproved isApproved ;
    private boolean isActive;
}

