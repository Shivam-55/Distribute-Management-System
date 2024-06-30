package com.company.dms.dto.forgotpassworddto;

import com.company.dms.utils.ExcludeFromCodeCoverage;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.*;
/**
 * Data transfer object (DTO) representing the data required for the forgot password feature.
 */
@ExcludeFromCodeCoverage
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class ForgotPasswordDto {
    /**
     * The email address associated with the user account.
     */
    private String email;

    /**
     * The one-time password (OTP) generated for password reset.
     */
    private int otp;

    /**
     * Retrieves the one-time password (OTP).
     * @return The one-time password.
     */
    @JsonIgnore
    public int getOtp() {
        return otp;
    }
    /**
     * Sets the one-time password (OTP).
     * @param otp The one-time password to set.
     */
    @JsonProperty
    public void setOtp(int otp) {
        this.otp = otp;
    }
}
