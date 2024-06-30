package com.company.dms.dto.userdto;

import com.company.dms.customenum.IsApproved;
import com.company.dms.dto.RoleDto;
import com.company.dms.utils.ExcludeFromCodeCoverage;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.*;
/**
 * Data transfer object (DTO) representing a user.
 */
@ExcludeFromCodeCoverage
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserDto {
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

    @JsonIgnore
    public String getPassword() {
        return password;
    }

    @JsonProperty
    public void setPassword(String password) {
        this.password = password;
    }

}
