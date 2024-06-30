package com.company.dms.serviceImp;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.anyBoolean;
import static org.mockito.Mockito.anyInt;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.time.ZoneOffset;
import java.util.Date;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.aot.DisabledInAotMode;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.company.dms.dto.RoleDto;
import com.company.dms.dto.forgotpassworddto.EmailDto;
import com.company.dms.dto.forgotpassworddto.NewPasswordDto;
import com.company.dms.dto.forgotpassworddto.OtpDto;
import com.company.dms.dto.userdto.UserServiceResponseDto;
import com.company.dms.entity.ForgotPassword;
import com.company.dms.exception.InvalidStepsException;
import com.company.dms.exception.NotActiveAnyMoreException;
import com.company.dms.otherservicecall.NotificationService;
import com.company.dms.repository.ForgotPasswordRepo;
import com.company.dms.service.UserInfoService;
import com.company.dms.serviceImp.ForgotPasswordImp;

@ContextConfiguration(classes = {ForgotPasswordImp.class})
@ExtendWith(SpringExtension.class)
@DisabledInAotMode
class ForgotPasswordImpTest {
    @Autowired
    private ForgotPasswordImp forgotPasswordImp;

    @MockBean
    private ForgotPasswordRepo forgotPasswordRepo;

    @MockBean
    private ModelMapper modelMapper;

    @MockBean
    private NotificationService notificationService;

    @MockBean
    private UserInfoService userInfoService;

    /**
     * Method under test: {@link ForgotPasswordImp#forgotPassword(EmailDto)}
     */
    @Test
    void testForgotPassword() {
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

        // Act and Assert
        assertThrows(NotActiveAnyMoreException.class,
                () -> forgotPasswordImp.forgotPassword(new EmailDto("jane.doe@example.org")));
        verify(userInfoService).getUserInfoByMail(eq("jane.doe@example.org"));
    }

    /**
     * Method under test: {@link ForgotPasswordImp#verifyOtp(OtpDto)}
     */
    @Test
    void testVerifyOtp() {
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

        // Act and Assert
        assertThrows(NotActiveAnyMoreException.class,
                () -> forgotPasswordImp.verifyOtp(new OtpDto("jane.doe@example.org", 1)));
        verify(userInfoService).getUserInfoByMail(eq("jane.doe@example.org"));
    }

    /**
     * Method under test: {@link ForgotPasswordImp#setNewPassword(NewPasswordDto)}
     */
    @Test
    void testSetNewPassword() {
        // Arrange
        ForgotPassword forgotPassword = new ForgotPassword();
        forgotPassword.setDate(Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()));
        forgotPassword.setEmail("jane.doe@example.org");
        forgotPassword.setId(1L);
        forgotPassword.setOtp(1);
        forgotPassword.setVerified(true);
        Optional<ForgotPassword> ofResult = Optional.of(forgotPassword);
        when(forgotPasswordRepo.findByEmail(Mockito.<String>any())).thenReturn(ofResult);
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

        // Act and Assert
        assertThrows(NotActiveAnyMoreException.class,
                () -> forgotPasswordImp.setNewPassword(new NewPasswordDto("jane.doe@example.org", "iloveyou")));
        verify(forgotPasswordRepo).findByEmail(eq("jane.doe@example.org"));
        verify(userInfoService).getUserInfoByMail(eq("jane.doe@example.org"));
    }

    /**
     * Method under test: {@link ForgotPasswordImp#setNewPassword(NewPasswordDto)}
     */
    @Test
    void testSetNewPassword2() {
        // Arrange
        ForgotPassword forgotPassword = new ForgotPassword();
        forgotPassword.setDate(Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()));
        forgotPassword.setEmail("jane.doe@example.org");
        forgotPassword.setId(1L);
        forgotPassword.setOtp(1);
        forgotPassword.setVerified(true);
        Optional<ForgotPassword> ofResult = Optional.of(forgotPassword);
        when(forgotPasswordRepo.findByEmail(Mockito.<String>any())).thenReturn(ofResult);
        when(userInfoService.getUserInfoByMail(Mockito.<String>any())).thenThrow(new InvalidStepsException("foo"));

        // Act and Assert
        assertThrows(InvalidStepsException.class,
                () -> forgotPasswordImp.setNewPassword(new NewPasswordDto("jane.doe@example.org", "iloveyou")));
        verify(forgotPasswordRepo).findByEmail(eq("jane.doe@example.org"));
        verify(userInfoService).getUserInfoByMail(eq("jane.doe@example.org"));
    }

    /**
     * Method under test: {@link ForgotPasswordImp#setNewPassword(NewPasswordDto)}
     */
    @Test
    void testSetNewPassword3() {
        // Arrange
        ForgotPassword forgotPassword = mock(ForgotPassword.class);
        when(forgotPassword.isVerified()).thenReturn(false);
        doNothing().when(forgotPassword).setDate(Mockito.<Date>any());
        doNothing().when(forgotPassword).setEmail(Mockito.<String>any());
        doNothing().when(forgotPassword).setId(Mockito.<Long>any());
        doNothing().when(forgotPassword).setOtp(anyInt());
        doNothing().when(forgotPassword).setVerified(anyBoolean());
        forgotPassword.setDate(Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()));
        forgotPassword.setEmail("jane.doe@example.org");
        forgotPassword.setId(1L);
        forgotPassword.setOtp(1);
        forgotPassword.setVerified(true);
        Optional<ForgotPassword> ofResult = Optional.of(forgotPassword);
        when(forgotPasswordRepo.findByEmail(Mockito.<String>any())).thenReturn(ofResult);

        // Act and Assert
        assertThrows(InvalidStepsException.class,
                () -> forgotPasswordImp.setNewPassword(new NewPasswordDto("jane.doe@example.org", "iloveyou")));
        verify(forgotPassword).isVerified();
        verify(forgotPassword).setDate(isA(Date.class));
        verify(forgotPassword).setEmail(eq("jane.doe@example.org"));
        verify(forgotPassword).setId(isA(Long.class));
        verify(forgotPassword).setOtp(eq(1));
        verify(forgotPassword).setVerified(eq(true));
        verify(forgotPasswordRepo).findByEmail(eq("jane.doe@example.org"));
    }

    /**
     * Method under test: {@link ForgotPasswordImp#setNewPassword(NewPasswordDto)}
     */
    @Test
    void testSetNewPassword4() {
        // Arrange
        Optional<ForgotPassword> emptyResult = Optional.empty();
        when(forgotPasswordRepo.findByEmail(Mockito.<String>any())).thenReturn(emptyResult);

        // Act and Assert
        assertThrows(InvalidStepsException.class,
                () -> forgotPasswordImp.setNewPassword(new NewPasswordDto("jane.doe@example.org", "iloveyou")));
        verify(forgotPasswordRepo).findByEmail(eq("jane.doe@example.org"));
    }
}
