package com.company.dms.controller;

import org.apache.http.auth.InvalidCredentialsException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.company.dms.dto.UserDto;
import com.company.dms.otherservicecall.ProfileServiceCall;
import com.company.dms.response.MessageResponse;
import com.company.dms.security.JwtAuthRequest;
import com.company.dms.security.JwtUtils;
import com.company.dms.utils.EncodeText;
import com.company.dms.utils.logger.LoggableClass;
import com.company.dms.utils.logger.LoggableMethod;


/**
 * Controller class for handling authentication related endpoints.
 */
@LoggableClass
@RestController
@RequestMapping("/auth")
public class AuthController {
    private final ProfileServiceCall profileServiceCall;
    private final JwtUtils jwtUtils;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    @Autowired
    public AuthController(ProfileServiceCall profileServiceCall, BCryptPasswordEncoder bCryptPasswordEncoder,
                          JwtUtils jwtUtils){
        this.profileServiceCall = profileServiceCall;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.jwtUtils = jwtUtils;
    }
    /**
     * Endpoint for user login.
     *
     * @param request JwtAuthRequest containing email and password
     * @return ResponseEntity containing message response and HTTP status
     * @throws InvalidCredentialsException if email or password is invalid
     */
    @LoggableMethod
    @PostMapping("/login")
    public ResponseEntity<MessageResponse<String>> login(@RequestBody JwtAuthRequest request) throws InvalidCredentialsException {
        UserDto userDto = profileServiceCall.getUserWithMail(request.getEmail());
        if(userDto==null) throw new InvalidCredentialsException("Invalid email or password");
        passwordCheck(userDto.getPassword(), request.getPassword());
        String token = this.jwtUtils.generateToken(userDto.getEmail(),userDto.getUserId(),userDto.getRole().getName());
        MessageResponse<String> messageResponse = MessageResponse.<String>builder()
              .data(EncodeText.encrytString(token))
              .message("Token generated")
              .status(true)
              .build();
        return new ResponseEntity<>(messageResponse,HttpStatus.OK);
    }

    /**
     * Method to check if entered password matches the stored password.
     *
     * @param storedPassword Stored password
     * @param enteredPassword Entered password
     * @throws InvalidCredentialsException if passwords do not match
     */
    @LoggableMethod
    public void passwordCheck(String storedPassword, String enteredPassword) throws InvalidCredentialsException {
         if(!bCryptPasswordEncoder.matches(enteredPassword,storedPassword)){
             throw new InvalidCredentialsException("Invalid email or password");
         }
    }
}
