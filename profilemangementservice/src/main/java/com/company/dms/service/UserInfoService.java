package com.company.dms.service;

import java.util.List;

import com.company.dms.customenum.IsApproved;
import com.company.dms.dto.TokenDto;
import com.company.dms.dto.userdto.*;
import com.company.dms.entity.Role;
import com.company.dms.entity.User;
/**
 * Interface for UserInfoService.
 */
public interface UserInfoService {
    /**
     * Retrieves user information by user ID.
     *
     * @param userId The ID of the user to retrieve information for.
     * @return UserDto object containing user information.
     */
    UserDto getUserInfo(Long userId);

    /**
     * Retrieves user information by email.
     *
     * @param mail The email of the user to retrieve information for.
     * @return UserServiceResponseDto object containing user information.
     */
    UserServiceResponseDto getUserInfoByMail(String mail);

    /**
     * Saves a new user with the provided email and password.
     *
     * @param mail     The email of the user to save.
     * @param password The password of the user to save.
     * @return UserDto object representing the saved user.
     */
    UserDto saveUser(String mail, String password);

    /**
     * Registers a new user with the provided user registration data.
     *
     * @param userDto The user registration data.
     * @return A string indicating the registration status.
     */
    String registration(UserRegistrationDto userDto);

    /**
     * Logs in a user with the provided user login data.
     *
     * @param userLoginDto The user login data.
     * @return Boolean indicating login success or failure.
     */
    Boolean login(UserLoginDto userLoginDto);

    /**
     * Approves a user with the provided user ID and user data.
     *
     * @param userId    The ID of the user to approve.
     * @param userDataDto The user data for approval.
     */
    void userApproval(Long userId, UserDataDto userDataDto);

    /**
     * Retrieves all user requests.
     *
     * @param tokenDto The token data for authentication.
     * @return List of UserDto objects representing user requests.
     */
    List<UserDto> allRequest(TokenDto tokenDto);

    /**
     * Removes a user with the provided token data and user data.
     *
     * @param tokenDto   The token data for authentication.
     * @param userDataDto The user data for removal.
     * @return UserDto object representing the removed user.
     */
    UserDto removeUser(TokenDto tokenDto, UserDataDto userDataDto);

    /**
     * Retrieves all active and approved users.
     *
     * @return List of UserDto objects representing active and approved users.
     */
    List<UserDto> getActiveApprovedUsers();

    /**
     * Finds users by role, active status, and approval status.
     *
     * @param role       The role of the users to find.
     * @param isActive   The active status of the users to find.
     * @param isApproved The approval status of the users to find.
     * @return List of User objects matching the criteria.
     */
    List<User> findUserByRoleAndIsActiveAndIsApproved(Role role, boolean isActive, IsApproved isApproved);

    void adminEntry();
}

