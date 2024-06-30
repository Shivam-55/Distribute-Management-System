package com.company.dms.dto.forgotpassworddto;

import com.company.dms.utils.ExcludeFromCodeCoverage;

import lombok.*;
/**
 * Data transfer object (DTO) representing the email address and OTP (one-time password) used for authentication.
 */
@ExcludeFromCodeCoverage
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class OtpDto {
    private String email;
    private int otp;
}
