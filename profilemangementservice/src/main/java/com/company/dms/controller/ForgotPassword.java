package com.company.dms.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.company.dms.dto.forgotpassworddto.EmailDto;
import com.company.dms.dto.forgotpassworddto.ForgotPasswordDto;
import com.company.dms.dto.forgotpassworddto.NewPasswordDto;
import com.company.dms.dto.forgotpassworddto.OtpDto;
import com.company.dms.dto.userdto.UserDto;
import com.company.dms.response.MessageResponse;
import com.company.dms.service.ForgotPasswordService;
import com.company.dms.utils.logger.LoggableClass;
import com.company.dms.utils.logger.LoggableMethod;

/**
 * Controller class to handle forgot password functionalities.
 */
@LoggableClass
@RestController
public class ForgotPassword {
    private ForgotPasswordService forgotPasswordService;
    @Autowired
    public ForgotPassword(ForgotPasswordService forgotPasswordService) {
        this.forgotPasswordService = forgotPasswordService;
    }
    /**
     * Endpoint to initiate password recovery process.
     *
     * @param emailDto The email DTO containing the user's email address.
     * @return ResponseEntity<MessageResponse<ForgotPasswordDto>> The response entity containing the status, data, and message.
     */
    @LoggableMethod
    @PostMapping("/password/forgot")
    public ResponseEntity<MessageResponse<ForgotPasswordDto>> recoverPassword(@RequestBody EmailDto emailDto) {
        ForgotPasswordDto forgotPasswordDto = forgotPasswordService.forgotPassword(emailDto);
        MessageResponse<ForgotPasswordDto> response = MessageResponse.<ForgotPasswordDto>builder()
                .status(true)
                .data(forgotPasswordDto)
                .message("otp for forgot password send successfully")
                .build();
        return new ResponseEntity<>(response , HttpStatus.OK);
    }

    /**
     * Endpoint to verify the OTP received during the password recovery process.
     *
     * @param otpDto The OTP DTO containing the OTP code.
     * @return ResponseEntity<MessageResponse<String>> The response entity containing the status, data, and message.
     */
    @LoggableMethod
    @PostMapping("/otp/verify")
    public ResponseEntity<MessageResponse<String>> verifyOtp(@RequestBody OtpDto otpDto){
        forgotPasswordService.verifyOtp(otpDto);
        MessageResponse<String> response = MessageResponse.<String>builder()
                .data("otp verified successfully")
                .status(true)
                .build();
        return new ResponseEntity<>(response,HttpStatus.OK);
    }

    /**
     * Endpoint to set a new password after successful verification of OTP.
     *
     * @param newPasswordDto The DTO containing the new password.
     * @return ResponseEntity<MessageResponse<String>> The response entity containing the status, data, and message.
     */
    @LoggableMethod
    @PostMapping("/new/password")
    public ResponseEntity<MessageResponse<String>> resetPassword(@RequestBody NewPasswordDto newPasswordDto){
        UserDto userDto = forgotPasswordService.setNewPassword(newPasswordDto);
        MessageResponse<String> response = MessageResponse.<String>builder()
                .data(userDto.getUsername()+"your password recovered successfully")
                .message("password reset successfully")
                .status(true)
                .build();
        return new ResponseEntity<>(response,HttpStatus.OK);
    }

}
