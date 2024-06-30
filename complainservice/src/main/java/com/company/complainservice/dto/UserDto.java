package com.company.complainservice.dto;

import com.company.complainservice.customeneum.IsApproved;

import lombok.*;


/**
 * Data transfer object (DTO) for user information.
 */
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Builder
public class UserDto {
    private Long userId;
    private String username;
    private IsApproved isApproved ;
    private boolean isActive;
}
