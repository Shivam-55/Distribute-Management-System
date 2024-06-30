package com.company.dms.dto.forgotpassworddto;

import com.company.dms.utils.ExcludeFromCodeCoverage;

import lombok.*;
/**
 * Data transfer object (DTO) representing the data required for setting a new password.
 */
@ExcludeFromCodeCoverage
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class NewPasswordDto {
    private String email;
    private String password;
}
