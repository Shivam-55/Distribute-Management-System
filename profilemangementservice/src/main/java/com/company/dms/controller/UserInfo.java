package com.company.dms.controller;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.company.dms.dto.RoleDto;
import com.company.dms.dto.TokenDto;
import com.company.dms.dto.userdto.*;
import com.company.dms.response.MessageResponse;
import com.company.dms.service.UserInfoService;
import com.company.dms.serviceImp.RoleInfoServiceImp;
import com.company.dms.utils.DecodeText;
import com.company.dms.utils.logger.LoggableClass;
import com.company.dms.utils.logger.LoggableMethod;
import com.company.dms.utils.logger.NotLoggableMethod;

import java.util.List;

/**
 * Controller class to handle user-related functionalities.
 */
@LoggableClass
@RestController
@RequestMapping("/user")
public class UserInfo {
    private static final String FINAL_MESSAGE = "All requests fetched Successfully" ;
    private final UserInfoService userService;
    private final RoleInfoServiceImp roleService;
    @Autowired
    public UserInfo(UserInfoService userService, RoleInfoServiceImp roleService){
        this.userService = userService;
        this.roleService = roleService;
    }
    /**
     * Retrieves user information based on the provided user ID.
     *
     * @param userId The ID of the user whose information needs to be retrieved.
     * @return ResponseEntity<UserDto> The response entity containing the user information.
     */
    @LoggableMethod
    @GetMapping("/info/{userId}")
    public ResponseEntity<UserDto> getUserInfo(@PathVariable Long userId) {
        UserDto userDto = userService.getUserInfo(userId);
        return new ResponseEntity<>(userDto, HttpStatus.OK);
    }

    /**
     * Retrieves user information based on the provided email.
     *
     * @param email The email of the user whose information needs to be retrieved.
     * @return ResponseEntity<UserServiceResponseDto> The response entity containing the user information.
     */
    @LoggableMethod
    @GetMapping("/info/mail/{email}")
    public ResponseEntity<UserServiceResponseDto> getUserInfo(@PathVariable String email) {
        UserServiceResponseDto userDto = userService.getUserInfoByMail(email);
        return new ResponseEntity<>(userDto, HttpStatus.OK);
    }

    /**
     * Registers a new user with the provided information.
     *
     * @param userDto The user registration information.
     * @return ResponseEntity<String> The response entity indicating the success of the registration.
     */
    @LoggableMethod
    @PostMapping("/registration")
    public ResponseEntity<String> userRegistration(@RequestBody UserRegistrationDto userDto) {
        return new ResponseEntity<>("Successfully registered !!!!!" + userService.registration(userDto), HttpStatus.OK);
    }

    /**
     * Logs in the user with the provided credentials.
     *
     * @param userLoginDto The user login credentials.
     * @return ResponseEntity<Boolean> The response entity indicating the success of the login operation.
     */
    @LoggableMethod
    @PostMapping("/login")
    public ResponseEntity<Boolean> userLogin(@RequestBody UserLoginDto userLoginDto) {
        return new ResponseEntity<>(userService.login(userLoginDto), HttpStatus.OK);
    }


    /**
     * Retrieves all user requests.
     *
     * @param httpServletRequest The HTTP servlet request.
     * @return ResponseEntity<MessageResponse<List<UserDto>>> The response entity containing the user requests.
     */
    @LoggableMethod
    @GetMapping("/request")
    public ResponseEntity<MessageResponse<List<UserDto>>> allRequests(HttpServletRequest httpServletRequest) {
        TokenDto tokenDto = getTokenDtoFromHeader(httpServletRequest);
        List<UserDto> userDtoList = userService.allRequest(tokenDto);
        MessageResponse<List<UserDto>> response = MessageResponse.<List<UserDto>>builder()
                .data(userDtoList)
                .message(FINAL_MESSAGE)
                .status(true)
                .build();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    //    dono ki testing distributor,retailer
    /**
     * Approves user requests.
     *
     * @param userDataDto       The user data DTO.
     * @param httpServletRequest The HTTP servlet request.
     * @return ResponseEntity<String> The response entity indicating the success of the approval.
     */
    @LoggableMethod
    @PostMapping("/requests/approve")
    public ResponseEntity<String> userApprove(@RequestBody UserDataDto userDataDto, HttpServletRequest httpServletRequest) {
        Long userId = Long.valueOf(DecodeText.decryptText(httpServletRequest.getHeader("userId")));
        userService.userApproval(userId,userDataDto);
        return new ResponseEntity<>("user Approved", HttpStatus.OK);
    }

    //    dono ki testing distributor,retailer

    /**
     * Removes a user.
     *
     * @param userDataDto       The user data.
     * @param httpServletRequest The HTTP servlet request.
     * @return ResponseEntity<MessageResponse<UserDto>> The response entity containing the removed user data.
     */
    @LoggableMethod
    @PostMapping("/remove")
    public ResponseEntity<MessageResponse<UserDto>> removeUser(@RequestBody UserDataDto userDataDto, HttpServletRequest httpServletRequest) {
        TokenDto tokenDto = getTokenDtoFromHeader(httpServletRequest);
        UserDto user = userService.removeUser(tokenDto, userDataDto);
        MessageResponse<UserDto> response = MessageResponse.<UserDto>builder()
                .data(user)
                .message(FINAL_MESSAGE)
                .status(true)
                .build();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    /**
     * Retrieves information about all active users.
     *
     * @return ResponseEntity<MessageResponse<List<UserDto>>> The response entity containing the information about all active users.
     */
    @LoggableMethod
    @GetMapping("/active")
    public ResponseEntity<MessageResponse<List<UserDto>>> getAllActiveUsers() {
        List<UserDto> userDtoList = userService.getActiveApprovedUsers();
        MessageResponse<List<UserDto>> response = MessageResponse.<List<UserDto>>builder()
                .data(userDtoList)
                .message(FINAL_MESSAGE)
                .status(true)
                .build();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }


    /**
     * Retrieves the token DTO from the HTTP servlet request header.
     *
     * @param httpServletRequest The HTTP servlet request.
     * @return TokenDto The token DTO retrieved from the header.
     */
    @NotLoggableMethod
    private TokenDto getTokenDtoFromHeader(HttpServletRequest httpServletRequest){
        Long userId = Long.valueOf(DecodeText.decryptText(httpServletRequest.getHeader("userId")));
        String role = DecodeText.decryptText(httpServletRequest.getHeader("userRole"));
        TokenDto tokenDto = new TokenDto();
        tokenDto.setUserId(userId);
        RoleDto roleDto = roleService.roleToDto(roleService.findByRoleName(role));
        tokenDto.setRole(roleDto);
        return tokenDto;
    }

}
