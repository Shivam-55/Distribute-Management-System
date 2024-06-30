package com.company.dms.controller;

import static org.mockito.Mockito.when;

import com.company.dms.controller.UserInfo;
import com.company.dms.dto.RoleDto;
import com.company.dms.dto.userdto.UserDataDto;
import com.company.dms.dto.userdto.UserDto;
import com.company.dms.dto.userdto.UserLoginDto;
import com.company.dms.dto.userdto.UserRegistrationDto;
import com.company.dms.dto.userdto.UserServiceResponseDto;
import com.company.dms.service.UserInfoService;
import com.company.dms.serviceImp.RoleInfoServiceImp;
import com.company.dms.validation.CachedBodyHttpServletRequest;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.servlet.http.HttpServletRequest;

import java.util.ArrayList;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.aot.DisabledInAotMode;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.ContentResultMatchers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

@ContextConfiguration(classes = {UserInfo.class})
@ExtendWith(SpringExtension.class)
@DisabledInAotMode
class UserInfoDiffblueTest {
    @MockBean
    private RoleInfoServiceImp roleInfoServiceImp;

    @Autowired
    private UserInfo userInfo;

    @MockBean
    private UserInfoService userInfoService;

    /**
     * Method under test: {@link UserInfo#getUserInfo(Long)}
     */
    @Test
    void testGetUserInfo() throws Exception {
        // Arrange
        UserDto.UserDtoBuilder pinCodeResult = UserDto.builder()
                .area("Area")
                .email("jane.doe@example.org")
                .mobile(1L)
                .password("iloveyou")
                .pinCode("Pin Code");
        RoleDto role = RoleDto.builder()
                .description("The characteristics of someone or something")
                .name("Bella")
                .roleId(1L)
                .build();
        UserDto buildResult = pinCodeResult.role(role).userId(1L).username("janedoe").build();
        when(userInfoService.getUserInfo(Mockito.<Long>any())).thenReturn(buildResult);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/user/info/{userId}", 1L);

        // Act and Assert
        MockMvcBuilders.standaloneSetup(userInfo)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content()
                        .string(
                                "{\"userId\":1,\"username\":\"janedoe\",\"role\":{\"roleId\":1,\"name\":\"Bella\",\"description\":\"The characteristics"
                                        + " of someone or something\"},\"mobile\":1,\"email\":\"jane.doe@example.org\",\"area\":\"Area\",\"address\":null,"
                                        + "\"pinCode\":\"Pin Code\",\"isApproved\":null,\"active\":false}"));
    }

    /**
     * Method under test: {@link UserInfo#allRequests(HttpServletRequest)}
     */
    @Test
    @Disabled("TODO: Complete this test")
    void testAllRequests() {
        // TODO: Diffblue Cover was only able to create a partial test for this method:
        //   Reason: No inputs found that don't throw a trivial exception.
        //   Diffblue Cover tried to run the arrange/act section, but the method under
        //   test threw
        //   jakarta.servlet.ServletException: Request processing failed: java.lang.NullPointerException: Cannot invoke "String.getBytes(java.nio.charset.Charset)" because "src" is null
        //       at jakarta.servlet.http.HttpServlet.service(HttpServlet.java:564)
        //       at jakarta.servlet.http.HttpServlet.service(HttpServlet.java:658)
        //   java.lang.NullPointerException: Cannot invoke "String.getBytes(java.nio.charset.Charset)" because "src" is null
        //       at java.base/java.util.Base64$Decoder.decode(Base64.java:589)
        //       at com.company.dms.utils.DecodeText.decrypt(DecodeText.java:34)
        //       at com.company.dms.utils.DecodeText.decryptText(DecodeText.java:48)
        //       at com.company.dms.controller.UserInfo.getTokenDtoFromHeader(UserInfo.java:99)
        //       at com.company.dms.controller.UserInfo.allRequests(UserInfo.java:54)
        //       at jakarta.servlet.http.HttpServlet.service(HttpServlet.java:564)
        //       at jakarta.servlet.http.HttpServlet.service(HttpServlet.java:658)
        //   See https://diff.blue/R013 to resolve this issue.

        // Arrange
//        UserInfo userInfo = new UserInfo();

        // Act
        userInfo.allRequests(new CachedBodyHttpServletRequest(new MockHttpServletRequest(), "https://example.org/example"));
    }

    /**
     * Method under test:
     * {@link UserInfo#userApprove(UserDataDto, HttpServletRequest)}
     */
    @Test
    @Disabled("TODO: Complete this test")
    void testUserApprove() {
        // TODO: Diffblue Cover was only able to create a partial test for this method:
        //   Reason: No inputs found that don't throw a trivial exception.
        //   Diffblue Cover tried to run the arrange/act section, but the method under
        //   test threw
        //   jakarta.servlet.ServletException: Request processing failed: java.lang.NullPointerException: Cannot invoke "String.getBytes(java.nio.charset.Charset)" because "src" is null
        //       at jakarta.servlet.http.HttpServlet.service(HttpServlet.java:590)
        //       at jakarta.servlet.http.HttpServlet.service(HttpServlet.java:658)
        //   java.lang.NullPointerException: Cannot invoke "String.getBytes(java.nio.charset.Charset)" because "src" is null
        //       at java.base/java.util.Base64$Decoder.decode(Base64.java:589)
        //       at com.company.dms.utils.DecodeText.decrypt(DecodeText.java:34)
        //       at com.company.dms.utils.DecodeText.decryptText(DecodeText.java:48)
        //       at com.company.dms.controller.UserInfo.userApprove(UserInfo.java:68)
        //       at jakarta.servlet.http.HttpServlet.service(HttpServlet.java:590)
        //       at jakarta.servlet.http.HttpServlet.service(HttpServlet.java:658)
        //   See https://diff.blue/R013 to resolve this issue.

        // Arrange
//        UserInfo userInfo = new UserInfo();
        UserDataDto userDataDto = new UserDataDto();

        // Act
        userInfo.userApprove(userDataDto,
                new CachedBodyHttpServletRequest(new MockHttpServletRequest(), "https://example.org/example"));
    }

    /**
     * Method under test:
     * {@link UserInfo#removeUser(UserDataDto, HttpServletRequest)}
     */
    @Test
    @Disabled("TODO: Complete this test")
    void testRemoveUser() {
        // TODO: Diffblue Cover was only able to create a partial test for this method:
        //   Reason: No inputs found that don't throw a trivial exception.
        //   Diffblue Cover tried to run the arrange/act section, but the method under
        //   test threw
        //   jakarta.servlet.ServletException: Request processing failed: java.lang.NullPointerException: Cannot invoke "String.getBytes(java.nio.charset.Charset)" because "src" is null
        //       at jakarta.servlet.http.HttpServlet.service(HttpServlet.java:590)
        //       at jakarta.servlet.http.HttpServlet.service(HttpServlet.java:658)
        //   java.lang.NullPointerException: Cannot invoke "String.getBytes(java.nio.charset.Charset)" because "src" is null
        //       at java.base/java.util.Base64$Decoder.decode(Base64.java:589)
        //       at com.company.dms.utils.DecodeText.decrypt(DecodeText.java:34)
        //       at com.company.dms.utils.DecodeText.decryptText(DecodeText.java:48)
        //       at com.company.dms.controller.UserInfo.getTokenDtoFromHeader(UserInfo.java:99)
        //       at com.company.dms.controller.UserInfo.removeUser(UserInfo.java:77)
        //       at jakarta.servlet.http.HttpServlet.service(HttpServlet.java:590)
        //       at jakarta.servlet.http.HttpServlet.service(HttpServlet.java:658)
        //   See https://diff.blue/R013 to resolve this issue.

        // Arrange
//        UserInfo userInfo = new UserInfo();
        UserDataDto userDataDto = new UserDataDto();

        // Act
        userInfo.removeUser(userDataDto,
                new CachedBodyHttpServletRequest(new MockHttpServletRequest(), "https://example.org/example"));
    }

    /**
     * Method under test: {@link UserInfo#getAllActiveUsers()}
     */
    @Test
    void testGetAllActiveUsers() throws Exception {
        // Arrange
        when(userInfoService.getActiveApprovedUsers()).thenReturn(new ArrayList<>());
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/user/active");

        // Act and Assert
        MockMvcBuilders.standaloneSetup(userInfo)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content()
                        .string("{\"message\":\"All requests fetched Successfully\",\"status\":true,\"data\":[]}"));
    }

    /**
     * Method under test: {@link UserInfo#getUserInfo(String)}
     */
    @Test
    void testGetUserInfo2() throws Exception {
        // Arrange
        UserServiceResponseDto.UserServiceResponseDtoBuilder pinCodeResult = UserServiceResponseDto.builder()
                .area("Area")
                .email("jane.doe@example.org")
                .mobile(1L)
                .password("iloveyou")
                .pinCode("Pin Code");
        RoleDto role = RoleDto.builder()
                .description("The characteristics of someone or something")
                .name("Bella")
                .roleId(1L)
                .build();
        UserServiceResponseDto buildResult = pinCodeResult.role(role).userId(1L).username("janedoe").build();
        when(userInfoService.getUserInfoByMail(Mockito.<String>any())).thenReturn(buildResult);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/user/info/mail/{email}",
                "jane.doe@example.org");

        // Act and Assert
        MockMvcBuilders.standaloneSetup(userInfo)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content()
                        .string(
                                "{\"userId\":1,\"username\":\"janedoe\",\"password\":\"iloveyou\",\"role\":{\"roleId\":1,\"name\":\"Bella\",\"description\":\"The"
                                        + " characteristics of someone or something\"},\"mobile\":1,\"email\":\"jane.doe@example.org\",\"area\":\"Area\","
                                        + "\"address\":null,\"pinCode\":\"Pin Code\",\"isApproved\":null,\"active\":false}"));
    }

    /**
     * Method under test: {@link UserInfo#userLogin(UserLoginDto)}
     */
    @Test
    void testUserLogin() throws Exception {
        // Arrange
        when(userInfoService.login(Mockito.<UserLoginDto>any())).thenReturn(true);

        UserLoginDto userLoginDto = new UserLoginDto();
        userLoginDto.setEmail("jane.doe@example.org");
        userLoginDto.setPassword("iloveyou");
        String content = (new ObjectMapper()).writeValueAsString(userLoginDto);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post("/user/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(content);

        // Act and Assert
        ResultActions resultActions = MockMvcBuilders.standaloneSetup(userInfo)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"));
        ContentResultMatchers contentResult = MockMvcResultMatchers.content();
        resultActions.andExpect(contentResult.string(Boolean.TRUE.toString()));
    }

    /**
     * Method under test: {@link UserInfo#userRegistration(UserRegistrationDto)}
     */
    @Test
    void testUserRegistration() throws Exception {
        // Arrange
        when(userInfoService.registration(Mockito.<UserRegistrationDto>any())).thenReturn("Registration");

        UserRegistrationDto userRegistrationDto = new UserRegistrationDto();
        userRegistrationDto.setArea("Area");
        userRegistrationDto.setEmail("jane.doe@example.org");
        userRegistrationDto.setMobile(1L);
        userRegistrationDto.setPassword("iloveyou");
        userRegistrationDto.setPinCode(1L);
        userRegistrationDto.setRole("Role");
        userRegistrationDto.setUsername("janedoe");
        String content = (new ObjectMapper()).writeValueAsString(userRegistrationDto);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post("/user/registration")
                .contentType(MediaType.APPLICATION_JSON)
                .content(content);

        // Act and Assert
        MockMvcBuilders.standaloneSetup(userInfo)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("text/plain;charset=ISO-8859-1"))
                .andExpect(MockMvcResultMatchers.content().string("Successfully registered !!!!!Registration"));
    }
}
