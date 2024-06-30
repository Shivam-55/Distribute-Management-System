package com.example.inventoryservice.responseDto;

import com.example.inventoryservice.requestDto.RoleRequestDto;
import com.example.inventoryservice.customenum.IsApproved;
import lombok.*;


/**
 * Data transfer object (DTO) representing a registration.
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@ToString
public class RegistrationResponseDto {
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

