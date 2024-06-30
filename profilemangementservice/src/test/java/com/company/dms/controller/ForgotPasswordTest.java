package com.company.dms.controller;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

import com.company.dms.controller.ForgotPassword;
import com.company.dms.dto.RoleDto;
import com.company.dms.dto.forgotpassworddto.EmailDto;
import com.company.dms.dto.forgotpassworddto.ForgotPasswordDto;
import com.company.dms.dto.forgotpassworddto.NewPasswordDto;
import com.company.dms.dto.forgotpassworddto.OtpDto;
import com.company.dms.dto.userdto.UserDto;
import com.company.dms.service.ForgotPasswordService;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.aot.DisabledInAotMode;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

@ContextConfiguration(classes = {ForgotPassword.class})
@ExtendWith(SpringExtension.class)
@DisabledInAotMode
class ForgotPasswordTest {
    @Autowired
    private ForgotPassword forgotPassword;

    @MockBean
    private ForgotPasswordService forgotPasswordService;

    /**
     * Method under test: {@link ForgotPassword#recoverPassword(EmailDto)}
     */
    @Test
    void testRecoverPassword() throws Exception {
        // Arrange
        when(forgotPasswordService.forgotPassword(Mockito.<EmailDto>any()))
                .thenReturn(new ForgotPasswordDto("jane.doe@example.org", 1));

        EmailDto emailDto = new EmailDto();
        emailDto.setEmail("jane.doe@example.org");
        String content = (new ObjectMapper()).writeValueAsString(emailDto);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post("/password/forgot")
                .contentType(MediaType.APPLICATION_JSON)
                .content(content);

        // Act and Assert
        MockMvcBuilders.standaloneSetup(forgotPassword)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content()
                        .string(
                                "{\"message\":\"otp for forgot password send successfully\",\"status\":true,\"data\":{\"email\":\"jane.doe@example"
                                        + ".org\"}}"));
    }

    /**
     * Method under test: {@link ForgotPassword#verifyOtp(OtpDto)}
     */
    @Test
    void testVerifyOtp() throws Exception {
        // Arrange
        doNothing().when(forgotPasswordService).verifyOtp(Mockito.<OtpDto>any());

        OtpDto otpDto = new OtpDto();
        otpDto.setEmail("jane.doe@example.org");
        otpDto.setOtp(1);
        String content = (new ObjectMapper()).writeValueAsString(otpDto);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post("/otp/verify")
                .contentType(MediaType.APPLICATION_JSON)
                .content(content);

        // Act and Assert
        MockMvcBuilders.standaloneSetup(forgotPassword)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content()
                        .string("{\"message\":null,\"status\":true,\"data\":\"otp verified successfully\"}"));
    }

    /**
     * Method under test: {@link ForgotPassword#resetPassword(NewPasswordDto)}
     */
    @Test
    void testResetPassword() throws Exception {
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
        when(forgotPasswordService.setNewPassword(Mockito.<NewPasswordDto>any())).thenReturn(buildResult);

        NewPasswordDto newPasswordDto = new NewPasswordDto();
        newPasswordDto.setEmail("jane.doe@example.org");
        newPasswordDto.setPassword("iloveyou");
        String content = (new ObjectMapper()).writeValueAsString(newPasswordDto);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post("/new/password")
                .contentType(MediaType.APPLICATION_JSON)
                .content(content);

        // Act and Assert
        MockMvcBuilders.standaloneSetup(forgotPassword)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content()
                        .string(
                                "{\"message\":\"password reset successfully\",\"status\":true,\"data\":\"janedoeyour password recovered"
                                        + " successfully\"}"));
    }
}
