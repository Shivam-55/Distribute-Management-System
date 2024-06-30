package com.example.inventoryservice.requestDto;

import com.example.inventoryservice.customenum.IsApproved;
import lombok.*;


/**
 * Data transfer object (DTO) representing a user.
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@ToString
public class UserDto {
    private Long userId;
    private String username;
    private Long mobile;
    private String email;
    private String area;
    private String address;
    private String pinCode;
    private IsApproved isApproved ;
    private boolean isActive;
}

