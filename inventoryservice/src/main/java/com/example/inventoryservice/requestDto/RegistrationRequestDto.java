package com.example.inventoryservice.requestDto;

import com.example.inventoryservice.customenum.IsApproved;
import lombok.*;


/**
 * Data transfer object (DTO) representing a request for registration.
 */
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RegistrationRequestDto {
    private String username;
    private String password;
    private RoleRequestDto roleDto ;
    private Long mobile;
    private String email;
    private String area;
    private String address;
    private int pinCode;
    private IsApproved isApproved ;
    private boolean isActive;
}
