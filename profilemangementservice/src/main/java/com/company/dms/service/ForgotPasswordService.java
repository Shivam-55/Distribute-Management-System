package com.company.dms.service;

import com.company.dms.dto.forgotpassworddto.EmailDto;
import com.company.dms.dto.forgotpassworddto.ForgotPasswordDto;
import com.company.dms.dto.forgotpassworddto.NewPasswordDto;
import com.company.dms.dto.forgotpassworddto.OtpDto;
import com.company.dms.dto.userdto.UserDto;

/**
 * Interface for ForgotPasswordService.
 */
public interface ForgotPasswordService {
    /**
     * Method to handle forgot password functionality.
     *
     * @param emailDto EmailDto object containing the email information.
     * @return ForgotPasswordDto object containing the response information.
     */
    ForgotPasswordDto forgotPassword(EmailDto emailDto);

    /**
     * Method to verify OTP.
     *
     * @param otpDto OtpDto object containing the OTP information.
     */
    void verifyOtp(OtpDto otpDto);

    /**
     * Method to set a new password.
     *
     * @param newPasswordDto NewPasswordDto object containing the new password information.
     * @return UserDto object containing the user information after setting the new password.
     */
    UserDto setNewPassword(NewPasswordDto newPasswordDto);
}
