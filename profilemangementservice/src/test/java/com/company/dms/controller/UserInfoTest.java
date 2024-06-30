package com.company.dms.controller;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import com.company.dms.controller.UserInfo;
import com.company.dms.dto.RoleDto;
import com.company.dms.dto.TokenDto;
import com.company.dms.dto.userdto.*;
import com.company.dms.entity.Role;
import com.company.dms.exception.NoSuchRolePresentException;
import com.company.dms.service.UserInfoService;
import com.company.dms.serviceImp.RoleInfoServiceImp;

import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@ExtendWith(SpringExtension.class)
@WebMvcTest(UserInfo.class)
class UserInfoTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserInfoService mockUserService;
    @MockBean
    private RoleInfoServiceImp mockRoleService;

    @Test
    void testGetUserInfo1() throws Exception {
        // Setup
        when(mockUserService.getUserInfo(0L)).thenReturn(UserDto.builder().build());

        // Run the test
        final MockHttpServletResponse response = mockMvc.perform(get("/user/info/{userId}", 0)
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        // Verify the results
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.getContentAsString()).isEqualTo("expectedResponse");
    }

    @Test
    void testGetUserInfo2() throws Exception {
        // Setup
        when(mockUserService.getUserInfoByMail("email")).thenReturn(UserServiceResponseDto.builder().build());

        // Run the test
        final MockHttpServletResponse response = mockMvc.perform(get("/user/info/mail/{email}", "email")
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        // Verify the results
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.getContentAsString()).isEqualTo("expectedResponse");
    }

    @Test
    void testUserRegistration() throws Exception {
        // Setup
        when(mockUserService.registration(any(UserRegistrationDto.class))).thenReturn("result");

        // Run the test
        final MockHttpServletResponse response = mockMvc.perform(post("/user/registration")
                        .content("content").contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        // Verify the results
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.getContentAsString()).isEqualTo("expectedResponse");
    }

    @Test
    void testUserLogin() throws Exception {
        // Setup
        when(mockUserService.login(any(UserLoginDto.class))).thenReturn(false);

        // Run the test
        final MockHttpServletResponse response = mockMvc.perform(post("/user/login")
                        .content("content").contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        // Verify the results
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.getContentAsString()).isEqualTo("expectedResponse");
    }

    @Test
    void testAllRequests() throws Exception {
        // Setup
        when(mockRoleService.findByRoleName("roleName")).thenReturn(Role.builder().build());
        when(mockRoleService.roleToDto(any(Role.class))).thenReturn(RoleDto.builder().build());
        when(mockUserService.allRequest(any(TokenDto.class))).thenReturn(List.of(UserDto.builder().build()));

        // Run the test
        final MockHttpServletResponse response = mockMvc.perform(get("/user/request")
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        // Verify the results
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.getContentAsString()).isEqualTo("expectedResponse");
    }

    @Test
    void testAllRequests_RoleInfoServiceImpFindByRoleNameThrowsNoSuchRolePresentException() throws Exception {
        // Setup
        when(mockRoleService.findByRoleName("roleName")).thenThrow(NoSuchRolePresentException.class);

        // Run the test
        final MockHttpServletResponse response = mockMvc.perform(get("/user/request")
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        // Verify the results
        assertThat(response.getStatus()).isEqualTo(HttpStatus.INTERNAL_SERVER_ERROR.value());
        assertThat(response.getContentAsString()).isEqualTo("expectedResponse");
    }

    @Test
    void testAllRequests_UserInfoServiceReturnsNoItems() throws Exception {
        // Setup
        when(mockRoleService.findByRoleName("roleName")).thenReturn(Role.builder().build());
        when(mockRoleService.roleToDto(any(Role.class))).thenReturn(RoleDto.builder().build());
        when(mockUserService.allRequest(any(TokenDto.class))).thenReturn(Collections.emptyList());

        // Run the test
        final MockHttpServletResponse response = mockMvc.perform(get("/user/request")
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        // Verify the results
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.getContentAsString()).isEqualTo("[]");
    }

    @Test
    void testUserApprove() throws Exception {
        // Setup
        // Run the test
        final MockHttpServletResponse response = mockMvc.perform(post("/user/requests/approve")
                        .content("content").contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        // Verify the results
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.getContentAsString()).isEqualTo("expectedResponse");
        verify(mockUserService).userApproval(eq(0L), any(UserDataDto.class));
    }

    @Test
    void testRemoveUser() throws Exception {
        // Setup
        when(mockRoleService.findByRoleName("roleName")).thenReturn(Role.builder().build());
        when(mockRoleService.roleToDto(any(Role.class))).thenReturn(RoleDto.builder().build());
        when(mockUserService.removeUser(any(TokenDto.class), any(UserDataDto.class)))
                .thenReturn(UserDto.builder().build());

        // Run the test
        final MockHttpServletResponse response = mockMvc.perform(post("/user/remove")
                        .content("content").contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        // Verify the results
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.getContentAsString()).isEqualTo("expectedResponse");
    }

    @Test
    void testRemoveUser_RoleInfoServiceImpFindByRoleNameThrowsNoSuchRolePresentException() throws Exception {
        // Setup
        when(mockRoleService.findByRoleName("roleName")).thenThrow(NoSuchRolePresentException.class);

        // Run the test
        final MockHttpServletResponse response = mockMvc.perform(post("/user/remove")
                        .content("content").contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        // Verify the results
        assertThat(response.getStatus()).isEqualTo(HttpStatus.INTERNAL_SERVER_ERROR.value());
        assertThat(response.getContentAsString()).isEqualTo("expectedResponse");
    }

    @Test
    void testGetAllActiveUsers() throws Exception {
        // Setup
        when(mockUserService.getActiveApprovedUsers()).thenReturn(List.of(UserDto.builder().build()));

        // Run the test
        final MockHttpServletResponse response = mockMvc.perform(get("/user/active")
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        // Verify the results
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.getContentAsString()).isEqualTo("expectedResponse");
    }

    @Test
    void testGetAllActiveUsers_UserInfoServiceReturnsNoItems() throws Exception {
        // Setup
        when(mockUserService.getActiveApprovedUsers()).thenReturn(Collections.emptyList());

        // Run the test
        final MockHttpServletResponse response = mockMvc.perform(get("/user/active")
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        // Verify the results
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.getContentAsString()).isEqualTo("[]");
    }
}
