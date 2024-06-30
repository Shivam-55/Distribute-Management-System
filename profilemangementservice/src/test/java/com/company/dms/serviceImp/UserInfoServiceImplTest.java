package com.company.dms.serviceImp;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ConfigurationException;
import org.modelmapper.MappingException;
import org.modelmapper.ModelMapper;
import org.springframework.dao.OptimisticLockingFailureException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.company.dms.customenum.IsApproved;
import com.company.dms.dto.RoleDto;
import com.company.dms.dto.TokenDto;
import com.company.dms.dto.userdto.*;
import com.company.dms.entity.DistributorToRetailer;
import com.company.dms.entity.Role;
import com.company.dms.entity.User;
import com.company.dms.exception.AlreadyEmailExistException;
import com.company.dms.exception.InvalidEmailException;
import com.company.dms.exception.NoRequestAvailableException;
import com.company.dms.exception.NoSuchUserPresentException;
import com.company.dms.otherservicecall.LocationService;
import com.company.dms.repository.UserInfoRepo;
import com.company.dms.service.RetailerConnectionService;
import com.company.dms.service.RoleInfoService;
import com.company.dms.serviceImp.UserInfoServiceImpl;

import reactor.core.publisher.Mono;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserInfoServiceImplTest {

    @Mock
    private ModelMapper mockMapper;
    @Mock
    private LocationService mockLocationService;
    @Mock
    private RetailerConnectionService mockRetailerConnectionService;
    @Mock
    private RoleInfoService mockRoleInfoService;
    @Mock
    private UserInfoRepo mockUserRepo;
    @Mock
    private BCryptPasswordEncoder mockBCryptPasswordEncoder;

    @InjectMocks
    private UserInfoServiceImpl userInfoServiceImplUnderTest;

    @Test
    void testGetUserInfo() {
        // Setup
        // Configure UserInfoRepo.findById(...).
        final Optional<User> user = Optional.of(User.builder()
                .username("username")
                .password("password")
                .role(Role.builder().build())
                .area("area")
                .pinCode("pinCode")
                .isApproved(IsApproved.NOTAPPROVED)
                .isActive(false)
                .build());
        when(mockUserRepo.findById(0L)).thenReturn(user);

        // Configure ModelMapper.map(...).
        final UserDto userDto = UserDto.builder()
                .password("password")
                .build();
        when(mockMapper.map(any(Object.class), eq(UserDto.class))).thenReturn(userDto);

        // Run the test
        final UserDto result = userInfoServiceImplUnderTest.getUserInfo(0L);

        // Verify the results
    }

    @Test
    void testGetUserInfo_UserInfoRepoReturnsAbsent() {
        // Setup
        when(mockUserRepo.findById(0L)).thenReturn(Optional.empty());

        // Run the test
        assertThatThrownBy(() -> userInfoServiceImplUnderTest.getUserInfo(0L))
                .isInstanceOf(NoSuchUserPresentException.class);
    }

    @Test
    void testGetUserInfo_ModelMapperThrowsConfigurationException() {
        // Setup
        // Configure UserInfoRepo.findById(...).
        final Optional<User> user = Optional.of(User.builder()
                .username("username")
                .password("password")
                .role(Role.builder().build())
                .area("area")
                .pinCode("pinCode")
                .isApproved(IsApproved.NOTAPPROVED)
                .isActive(false)
                .build());
        when(mockUserRepo.findById(0L)).thenReturn(user);

        when(mockMapper.map(any(Object.class), eq(UserDto.class))).thenThrow(ConfigurationException.class);

        // Run the test
        assertThatThrownBy(() -> userInfoServiceImplUnderTest.getUserInfo(0L))
                .isInstanceOf(ConfigurationException.class);
    }

    @Test
    void testGetUserInfo_ModelMapperThrowsMappingException() {
        // Setup
        // Configure UserInfoRepo.findById(...).
        final Optional<User> user = Optional.of(User.builder()
                .username("username")
                .password("password")
                .role(Role.builder().build())
                .area("area")
                .pinCode("pinCode")
                .isApproved(IsApproved.NOTAPPROVED)
                .isActive(false)
                .build());
        when(mockUserRepo.findById(0L)).thenReturn(user);

        when(mockMapper.map(any(Object.class), eq(UserDto.class))).thenThrow(MappingException.class);

        // Run the test
        assertThatThrownBy(() -> userInfoServiceImplUnderTest.getUserInfo(0L)).isInstanceOf(MappingException.class);
    }

    @Test
    void testGetUserInfoByMail() {
        // Setup
        // Configure UserInfoRepo.findByEmail(...).
        final Optional<User> user = Optional.of(User.builder()
                .username("username")
                .password("password")
                .role(Role.builder().build())
                .area("area")
                .pinCode("pinCode")
                .isApproved(IsApproved.NOTAPPROVED)
                .isActive(false)
                .build());
        when(mockUserRepo.findByEmail("mail")).thenReturn(user);

        when(mockMapper.map(any(Object.class), eq(UserServiceResponseDto.class)))
                .thenReturn(UserServiceResponseDto.builder().build());

        // Run the test
        final UserServiceResponseDto result = userInfoServiceImplUnderTest.getUserInfoByMail("mail");

        // Verify the results
    }

    @Test
    void testGetUserInfoByMail_UserInfoRepoReturnsAbsent() {
        // Setup
        when(mockUserRepo.findByEmail("mail")).thenReturn(Optional.empty());

        // Run the test
        assertThatThrownBy(() -> userInfoServiceImplUnderTest.getUserInfoByMail("mail"))
                .isInstanceOf(NoSuchUserPresentException.class);
    }

    @Test
    void testGetUserInfoByMail_ModelMapperThrowsConfigurationException() {
        // Setup
        // Configure UserInfoRepo.findByEmail(...).
        final Optional<User> user = Optional.of(User.builder()
                .username("username")
                .password("password")
                .role(Role.builder().build())
                .area("area")
                .pinCode("pinCode")
                .isApproved(IsApproved.NOTAPPROVED)
                .isActive(false)
                .build());
        when(mockUserRepo.findByEmail("mail")).thenReturn(user);

        when(mockMapper.map(any(Object.class), eq(UserServiceResponseDto.class)))
                .thenThrow(ConfigurationException.class);

        // Run the test
        assertThatThrownBy(() -> userInfoServiceImplUnderTest.getUserInfoByMail("mail"))
                .isInstanceOf(ConfigurationException.class);
    }

    @Test
    void testGetUserInfoByMail_ModelMapperThrowsMappingException() {
        // Setup
        // Configure UserInfoRepo.findByEmail(...).
        final Optional<User> user = Optional.of(User.builder()
                .username("username")
                .password("password")
                .role(Role.builder().build())
                .area("area")
                .pinCode("pinCode")
                .isApproved(IsApproved.NOTAPPROVED)
                .isActive(false)
                .build());
        when(mockUserRepo.findByEmail("mail")).thenReturn(user);

        when(mockMapper.map(any(Object.class), eq(UserServiceResponseDto.class))).thenThrow(MappingException.class);

        // Run the test
        assertThatThrownBy(() -> userInfoServiceImplUnderTest.getUserInfoByMail("mail"))
                .isInstanceOf(MappingException.class);
    }

    @Test
    void testSaveUser() {
        // Setup
        // Configure UserInfoRepo.findByEmail(...).
        final Optional<User> user = Optional.of(User.builder()
                .username("username")
                .password("password")
                .role(Role.builder().build())
                .area("area")
                .pinCode("pinCode")
                .isApproved(IsApproved.NOTAPPROVED)
                .isActive(false)
                .build());
        when(mockUserRepo.findByEmail("mail")).thenReturn(user);

        when(mockBCryptPasswordEncoder.encode("password")).thenReturn("password");

        // Configure UserInfoRepo.save(...).
        final User user1 = User.builder()
                .username("username")
                .password("password")
                .role(Role.builder().build())
                .area("area")
                .pinCode("pinCode")
                .isApproved(IsApproved.NOTAPPROVED)
                .isActive(false)
                .build();
        when(mockUserRepo.save(any(User.class))).thenReturn(user1);

        // Configure ModelMapper.map(...).
        final UserDto userDto = UserDto.builder()
                .password("password")
                .build();
        when(mockMapper.map(any(Object.class), eq(UserDto.class))).thenReturn(userDto);

        // Run the test
        final UserDto result = userInfoServiceImplUnderTest.saveUser("mail", "password");

        // Verify the results
    }

    @Test
    void testSaveUser_UserInfoRepoFindByEmailReturnsAbsent() {
        // Setup
        when(mockUserRepo.findByEmail("mail")).thenReturn(Optional.empty());

        // Run the test
        assertThatThrownBy(() -> userInfoServiceImplUnderTest.saveUser("mail", "password"))
                .isInstanceOf(InvalidEmailException.class);
    }

    @Test
    void testSaveUser_UserInfoRepoSaveThrowsOptimisticLockingFailureException() {
        // Setup
        // Configure UserInfoRepo.findByEmail(...).
        final Optional<User> user = Optional.of(User.builder()
                .username("username")
                .password("password")
                .role(Role.builder().build())
                .area("area")
                .pinCode("pinCode")
                .isApproved(IsApproved.NOTAPPROVED)
                .isActive(false)
                .build());
        when(mockUserRepo.findByEmail("mail")).thenReturn(user);

        when(mockBCryptPasswordEncoder.encode("password")).thenReturn("password");
        when(mockUserRepo.save(any(User.class))).thenThrow(OptimisticLockingFailureException.class);

        // Run the test
        assertThatThrownBy(() -> userInfoServiceImplUnderTest.saveUser("mail", "password"))
                .isInstanceOf(OptimisticLockingFailureException.class);
    }

    @Test
    void testSaveUser_ModelMapperThrowsConfigurationException() {
        // Setup
        // Configure UserInfoRepo.findByEmail(...).
        final Optional<User> user = Optional.of(User.builder()
                .username("username")
                .password("password")
                .role(Role.builder().build())
                .area("area")
                .pinCode("pinCode")
                .isApproved(IsApproved.NOTAPPROVED)
                .isActive(false)
                .build());
        when(mockUserRepo.findByEmail("mail")).thenReturn(user);

        when(mockBCryptPasswordEncoder.encode("password")).thenReturn("password");

        // Configure UserInfoRepo.save(...).
        final User user1 = User.builder()
                .username("username")
                .password("password")
                .role(Role.builder().build())
                .area("area")
                .pinCode("pinCode")
                .isApproved(IsApproved.NOTAPPROVED)
                .isActive(false)
                .build();
        when(mockUserRepo.save(any(User.class))).thenReturn(user1);

        when(mockMapper.map(any(Object.class), eq(UserDto.class))).thenThrow(ConfigurationException.class);

        // Run the test
        assertThatThrownBy(() -> userInfoServiceImplUnderTest.saveUser("mail", "password"))
                .isInstanceOf(ConfigurationException.class);
    }

    @Test
    void testSaveUser_ModelMapperThrowsMappingException() {
        // Setup
        // Configure UserInfoRepo.findByEmail(...).
        final Optional<User> user = Optional.of(User.builder()
                .username("username")
                .password("password")
                .role(Role.builder().build())
                .area("area")
                .pinCode("pinCode")
                .isApproved(IsApproved.NOTAPPROVED)
                .isActive(false)
                .build());
        when(mockUserRepo.findByEmail("mail")).thenReturn(user);

        when(mockBCryptPasswordEncoder.encode("password")).thenReturn("password");

        // Configure UserInfoRepo.save(...).
        final User user1 = User.builder()
                .username("username")
                .password("password")
                .role(Role.builder().build())
                .area("area")
                .pinCode("pinCode")
                .isApproved(IsApproved.NOTAPPROVED)
                .isActive(false)
                .build();
        when(mockUserRepo.save(any(User.class))).thenReturn(user1);

        when(mockMapper.map(any(Object.class), eq(UserDto.class))).thenThrow(MappingException.class);

        // Run the test
        assertThatThrownBy(() -> userInfoServiceImplUnderTest.saveUser("mail", "password"))
                .isInstanceOf(MappingException.class);
    }

    @Test
    void testRegistration_ThrowsAlreadyEmailExistException() {
        // Setup
        final UserRegistrationDto userRegistrationDto = UserRegistrationDto.builder()
                .role("role")
                .mobile(0L)
                .email("email")
                .area("area")
                .pinCode(0L)
                .build();
        when(mockLocationService.getCityByPincode("pincode")).thenReturn(Mono.just("value"));

        // Configure UserInfoRepo.findByEmail(...).
        final Optional<User> user = Optional.of(User.builder()
                .username("username")
                .password("password")
                .role(Role.builder().build())
                .area("area")
                .pinCode("pinCode")
                .isApproved(IsApproved.NOTAPPROVED)
                .isActive(false)
                .build());
        when(mockUserRepo.findByEmail("email")).thenReturn(user);

        // Run the test
        assertThatThrownBy(() -> userInfoServiceImplUnderTest.registration(userRegistrationDto))
                .isInstanceOf(AlreadyEmailExistException.class);
    }

    @Test
    void testRegistration_LocationServiceReturnsNoItem() {
        // Setup
        final UserRegistrationDto userRegistrationDto = UserRegistrationDto.builder()
                .role("role")
                .mobile(0L)
                .email("email")
                .area("area")
                .pinCode(0L)
                .build();
        when(mockLocationService.getCityByPincode("pincode")).thenReturn(Mono.empty());

        // Configure UserInfoRepo.findByEmail(...).
        final Optional<User> user = Optional.of(User.builder()
                .username("username")
                .password("password")
                .role(Role.builder().build())
                .area("area")
                .pinCode("pinCode")
                .isApproved(IsApproved.NOTAPPROVED)
                .isActive(false)
                .build());
        when(mockUserRepo.findByEmail("email")).thenReturn(user);

        // Run the test
        assertThatThrownBy(() -> userInfoServiceImplUnderTest.registration(userRegistrationDto))
                .isInstanceOf(AlreadyEmailExistException.class);
    }

    @Test
    void testRegistration_LocationServiceReturnsError() {
        // Setup
        final UserRegistrationDto userRegistrationDto = UserRegistrationDto.builder()
                .role("role")
                .mobile(0L)
                .email("email")
                .area("area")
                .pinCode(0L)
                .build();
        when(mockLocationService.getCityByPincode("pincode")).thenReturn(Mono.error(new Exception("message")));

        // Configure UserInfoRepo.findByEmail(...).
        final Optional<User> user = Optional.of(User.builder()
                .username("username")
                .password("password")
                .role(Role.builder().build())
                .area("area")
                .pinCode("pinCode")
                .isApproved(IsApproved.NOTAPPROVED)
                .isActive(false)
                .build());
        when(mockUserRepo.findByEmail("email")).thenReturn(user);

        // Run the test
        assertThatThrownBy(() -> userInfoServiceImplUnderTest.registration(userRegistrationDto))
                .isInstanceOf(AlreadyEmailExistException.class);
    }

    @Test
    void testRegistration_UserInfoRepoFindByEmailReturnsAbsent() {
        // Setup
        final UserRegistrationDto userRegistrationDto = UserRegistrationDto.builder()
                .role("role")
                .mobile(0L)
                .email("email")
                .area("area")
                .pinCode(0L)
                .build();
        when(mockLocationService.getCityByPincode("pincode")).thenReturn(Mono.just("value"));
        when(mockUserRepo.findByEmail("email")).thenReturn(Optional.empty());

        // Configure UserInfoRepo.findByMobile(...).
        final Optional<User> user = Optional.of(User.builder()
                .username("username")
                .password("password")
                .role(Role.builder().build())
                .area("area")
                .pinCode("pinCode")
                .isApproved(IsApproved.NOTAPPROVED)
                .isActive(false)
                .build());
        when(mockUserRepo.findByMobile(0L)).thenReturn(user);

        // Run the test
        assertThatThrownBy(() -> userInfoServiceImplUnderTest.registration(userRegistrationDto))
                .isInstanceOf(AlreadyEmailExistException.class);
    }

    @Test
    void testRegistration_UserInfoRepoFindByMobileReturnsAbsent() {
        // Setup
        final UserRegistrationDto userRegistrationDto = UserRegistrationDto.builder()
                .role("role")
                .mobile(0L)
                .email("email")
                .area("area")
                .pinCode(0L)
                .build();
        when(mockLocationService.getCityByPincode("pincode")).thenReturn(Mono.just("value"));
        when(mockUserRepo.findByEmail("email")).thenReturn(Optional.empty());
        when(mockUserRepo.findByMobile(0L)).thenReturn(Optional.empty());

        // Configure ModelMapper.map(...).
        final User user = User.builder()
                .username("username")
                .password("password")
                .role(Role.builder().build())
                .area("area")
                .pinCode("pinCode")
                .isApproved(IsApproved.NOTAPPROVED)
                .isActive(false)
                .build();
        when(mockMapper.map(any(Object.class), eq(User.class))).thenReturn(user);

        when(mockBCryptPasswordEncoder.encode("password")).thenReturn("password");
        when(mockRoleInfoService.findByRoleName("role")).thenReturn(Role.builder().build());

        // Configure UserInfoRepo.save(...).
        final User user1 = User.builder()
                .username("username")
                .password("password")
                .role(Role.builder().build())
                .area("area")
                .pinCode("pinCode")
                .isApproved(IsApproved.NOTAPPROVED)
                .isActive(false)
                .build();
        when(mockUserRepo.save(any(User.class))).thenReturn(user1);

        // Run the test
        final String result = userInfoServiceImplUnderTest.registration(userRegistrationDto);

        // Verify the results
        assertThat(result).isEqualTo("username");
    }

    @Test
    void testRegistration_ModelMapperThrowsConfigurationException() {
        // Setup
        final UserRegistrationDto userRegistrationDto = UserRegistrationDto.builder()
                .role("role")
                .mobile(0L)
                .email("email")
                .area("area")
                .pinCode(0L)
                .build();
        when(mockLocationService.getCityByPincode("pincode")).thenReturn(Mono.just("value"));
        when(mockUserRepo.findByEmail("email")).thenReturn(Optional.empty());
        when(mockUserRepo.findByMobile(0L)).thenReturn(Optional.empty());
        when(mockMapper.map(any(Object.class), eq(User.class))).thenThrow(ConfigurationException.class);

        // Run the test
        assertThatThrownBy(() -> userInfoServiceImplUnderTest.registration(userRegistrationDto))
                .isInstanceOf(ConfigurationException.class);
    }

    @Test
    void testRegistration_ModelMapperThrowsMappingException() {
        // Setup
        final UserRegistrationDto userRegistrationDto = UserRegistrationDto.builder()
                .role("role")
                .mobile(0L)
                .email("email")
                .area("area")
                .pinCode(0L)
                .build();
        when(mockLocationService.getCityByPincode("pincode")).thenReturn(Mono.just("value"));
        when(mockUserRepo.findByEmail("email")).thenReturn(Optional.empty());
        when(mockUserRepo.findByMobile(0L)).thenReturn(Optional.empty());
        when(mockMapper.map(any(Object.class), eq(User.class))).thenThrow(MappingException.class);

        // Run the test
        assertThatThrownBy(() -> userInfoServiceImplUnderTest.registration(userRegistrationDto))
                .isInstanceOf(MappingException.class);
    }

    @Test
    void testRegistration_UserInfoRepoSaveThrowsOptimisticLockingFailureException() {
        // Setup
        final UserRegistrationDto userRegistrationDto = UserRegistrationDto.builder()
                .role("role")
                .mobile(0L)
                .email("email")
                .area("area")
                .pinCode(0L)
                .build();
        when(mockLocationService.getCityByPincode("pincode")).thenReturn(Mono.just("value"));
        when(mockUserRepo.findByEmail("email")).thenReturn(Optional.empty());
        when(mockUserRepo.findByMobile(0L)).thenReturn(Optional.empty());

        // Configure ModelMapper.map(...).
        final User user = User.builder()
                .username("username")
                .password("password")
                .role(Role.builder().build())
                .area("area")
                .pinCode("pinCode")
                .isApproved(IsApproved.NOTAPPROVED)
                .isActive(false)
                .build();
        when(mockMapper.map(any(Object.class), eq(User.class))).thenReturn(user);

        when(mockBCryptPasswordEncoder.encode("password")).thenReturn("password");
        when(mockRoleInfoService.findByRoleName("role")).thenReturn(Role.builder().build());
        when(mockUserRepo.save(any(User.class))).thenThrow(OptimisticLockingFailureException.class);

        // Run the test
        assertThatThrownBy(() -> userInfoServiceImplUnderTest.registration(userRegistrationDto))
                .isInstanceOf(OptimisticLockingFailureException.class);
    }

    @Test
    void testLogin() {
        // Setup
        final UserLoginDto userLoginDto = UserLoginDto.builder()
                .password("password")
                .email("mail")
                .build();

        // Configure UserInfoRepo.findByEmail(...).
        final Optional<User> user = Optional.of(User.builder()
                .username("username")
                .password("password")
                .role(Role.builder().build())
                .area("area")
                .pinCode("pinCode")
                .isApproved(IsApproved.NOTAPPROVED)
                .isActive(false)
                .build());
        when(mockUserRepo.findByEmail("mail")).thenReturn(user);

        when(mockBCryptPasswordEncoder.matches("password", "password")).thenReturn(false);

        // Run the test
        final Boolean result = userInfoServiceImplUnderTest.login(userLoginDto);

        // Verify the results
        assertThat(result).isFalse();
    }

    @Test
    void testLogin_UserInfoRepoReturnsAbsent() {
        // Setup
        final UserLoginDto userLoginDto = UserLoginDto.builder()
                .password("password")
                .email("mail")
                .build();
        when(mockUserRepo.findByEmail("mail")).thenReturn(Optional.empty());

        // Run the test
        assertThatThrownBy(() -> userInfoServiceImplUnderTest.login(userLoginDto))
                .isInstanceOf(InvalidEmailException.class);
    }

    @Test
    void testLogin_BCryptPasswordEncoderReturnsTrue() {
        // Setup
        final UserLoginDto userLoginDto = UserLoginDto.builder()
                .password("password")
                .email("mail")
                .build();

        // Configure UserInfoRepo.findByEmail(...).
        final Optional<User> user = Optional.of(User.builder()
                .username("username")
                .password("password")
                .role(Role.builder().build())
                .area("area")
                .pinCode("pinCode")
                .isApproved(IsApproved.NOTAPPROVED)
                .isActive(false)
                .build());
        when(mockUserRepo.findByEmail("mail")).thenReturn(user);

        when(mockBCryptPasswordEncoder.matches("password", "password")).thenReturn(true);

        // Run the test
        final Boolean result = userInfoServiceImplUnderTest.login(userLoginDto);

        // Verify the results
        assertThat(result).isTrue();
    }

    @Test
    void testUserApproval() {
        // Setup
        final UserDataDto userDataDto = UserDataDto.builder()
                .userId(0L)
                .role("role")
                .area("area")
                .build();

        // Configure UserInfoRepo.findByUserId(...).
        final Optional<User> user = Optional.of(User.builder()
                .username("username")
                .password("password")
                .role(Role.builder().build())
                .area("area")
                .pinCode("pinCode")
                .isApproved(IsApproved.NOTAPPROVED)
                .isActive(false)
                .build());
        when(mockUserRepo.findByUserId(0L)).thenReturn(user);

        when(mockRoleInfoService.findByRoleName("roleName")).thenReturn(Role.builder().build());

        // Configure UserInfoRepo.findByAreaAndRoleAndIsActiveAndIsApproved(...).
        final Optional<User> user1 = Optional.of(User.builder()
                .username("username")
                .password("password")
                .role(Role.builder().build())
                .area("area")
                .pinCode("pinCode")
                .isApproved(IsApproved.NOTAPPROVED)
                .isActive(false)
                .build());
        when(mockUserRepo.findByAreaAndRoleAndIsActiveAndIsApproved(eq("area"), any(Role.class), eq(true),
                eq(IsApproved.APPROVED))).thenReturn(user1);

        // Configure UserInfoRepo.findByAreaAndRoleAndIsActive(...).
        final Optional<List<User>> users = Optional.of(List.of(User.builder()
                .username("username")
                .password("password")
                .role(Role.builder().build())
                .area("area")
                .pinCode("pinCode")
                .isApproved(IsApproved.NOTAPPROVED)
                .isActive(false)
                .build()));
        when(mockUserRepo.findByAreaAndRoleAndIsActive(eq("area"), any(Role.class), eq(true))).thenReturn(users);

        // Configure RetailerConnectionService.distributorToRetailerConnection(...).
        final DistributorToRetailer distributorToRetailer = DistributorToRetailer.builder()
                .distributor(User.builder()
                        .username("username")
                        .password("password")
                        .role(Role.builder().build())
                        .area("area")
                        .pinCode("pinCode")
                        .isApproved(IsApproved.NOTAPPROVED)
                        .isActive(false)
                        .build())
                .retailers(List.of(User.builder()
                        .username("username")
                        .password("password")
                        .role(Role.builder().build())
                        .area("area")
                        .pinCode("pinCode")
                        .isApproved(IsApproved.NOTAPPROVED)
                        .isActive(false)
                        .build()))
                .build();
        when(mockRetailerConnectionService.distributorToRetailerConnection(any(User.class)))
                .thenReturn(distributorToRetailer);

        // Configure UserInfoRepo.findByRoleAndIsActiveAndIsApproved(...).
        final Optional<List<User>> users1 = Optional.of(List.of(User.builder()
                .username("username")
                .password("password")
                .role(Role.builder().build())
                .area("area")
                .pinCode("pinCode")
                .isApproved(IsApproved.NOTAPPROVED)
                .isActive(false)
                .build()));
        when(mockUserRepo.findByRoleAndIsActiveAndIsApproved(any(Role.class), eq(true),
                eq(IsApproved.APPROVED))).thenReturn(users1);

        // Run the test
        userInfoServiceImplUnderTest.userApproval(0L, userDataDto);

        // Verify the results
        verify(mockUserRepo).save(any(User.class));
        verify(mockRetailerConnectionService).saveDistributorConnection(any(DistributorToRetailer.class));
    }

    @Test
    void testUserApproval_UserInfoRepoFindByUserIdReturnsAbsent() {
        // Setup
        final UserDataDto userDataDto = UserDataDto.builder()
                .userId(0L)
                .role("role")
                .area("area")
                .build();
        when(mockUserRepo.findByUserId(0L)).thenReturn(Optional.empty());

        // Run the test
        assertThatThrownBy(() -> userInfoServiceImplUnderTest.userApproval(0L, userDataDto))
                .isInstanceOf(NoSuchUserPresentException.class);
    }

    @Test
    void testUserApproval_UserInfoRepoFindByAreaAndRoleAndIsActiveAndIsApprovedReturnsAbsent() {
        // Setup
        final UserDataDto userDataDto = UserDataDto.builder()
                .userId(0L)
                .role("role")
                .area("area")
                .build();

        // Configure UserInfoRepo.findByUserId(...).
        final Optional<User> user = Optional.of(User.builder()
                .username("username")
                .password("password")
                .role(Role.builder().build())
                .area("area")
                .pinCode("pinCode")
                .isApproved(IsApproved.NOTAPPROVED)
                .isActive(false)
                .build());
        when(mockUserRepo.findByUserId(0L)).thenReturn(user);

        when(mockRoleInfoService.findByRoleName("roleName")).thenReturn(Role.builder().build());
        when(mockUserRepo.findByAreaAndRoleAndIsActiveAndIsApproved(eq("area"), any(Role.class), eq(true),
                eq(IsApproved.APPROVED))).thenReturn(Optional.empty());

        // Configure UserInfoRepo.findByAreaAndRoleAndIsActive(...).
        final Optional<List<User>> users = Optional.of(List.of(User.builder()
                .username("username")
                .password("password")
                .role(Role.builder().build())
                .area("area")
                .pinCode("pinCode")
                .isApproved(IsApproved.NOTAPPROVED)
                .isActive(false)
                .build()));
        when(mockUserRepo.findByAreaAndRoleAndIsActive(eq("area"), any(Role.class), eq(true))).thenReturn(users);

        // Run the test
        userInfoServiceImplUnderTest.userApproval(0L, userDataDto);

        // Verify the results
        verify(mockUserRepo).save(any(User.class));
        verify(mockRetailerConnectionService).saveDistributorConnection(any(DistributorToRetailer.class));
    }

    @Test
    void testUserApproval_UserInfoRepoSaveThrowsOptimisticLockingFailureException() {
        // Setup
        final UserDataDto userDataDto = UserDataDto.builder()
                .userId(0L)
                .role("role")
                .area("area")
                .build();

        // Configure UserInfoRepo.findByUserId(...).
        final Optional<User> user = Optional.of(User.builder()
                .username("username")
                .password("password")
                .role(Role.builder().build())
                .area("area")
                .pinCode("pinCode")
                .isApproved(IsApproved.NOTAPPROVED)
                .isActive(false)
                .build());
        when(mockUserRepo.findByUserId(0L)).thenReturn(user);

        when(mockRoleInfoService.findByRoleName("roleName")).thenReturn(Role.builder().build());
        when(mockUserRepo.findByAreaAndRoleAndIsActiveAndIsApproved(eq("area"), any(Role.class), eq(true),
                eq(IsApproved.APPROVED))).thenReturn(Optional.empty());
        when(mockUserRepo.save(any(User.class))).thenThrow(OptimisticLockingFailureException.class);

        // Run the test
        assertThatThrownBy(() -> userInfoServiceImplUnderTest.userApproval(0L, userDataDto))
                .isInstanceOf(OptimisticLockingFailureException.class);
    }

    @Test
    void testUserApproval_UserInfoRepoFindByAreaAndRoleAndIsActiveReturnsAbsent() {
        // Setup
        final UserDataDto userDataDto = UserDataDto.builder()
                .userId(0L)
                .role("role")
                .area("area")
                .build();

        // Configure UserInfoRepo.findByUserId(...).
        final Optional<User> user = Optional.of(User.builder()
                .username("username")
                .password("password")
                .role(Role.builder().build())
                .area("area")
                .pinCode("pinCode")
                .isApproved(IsApproved.NOTAPPROVED)
                .isActive(false)
                .build());
        when(mockUserRepo.findByUserId(0L)).thenReturn(user);

        when(mockRoleInfoService.findByRoleName("roleName")).thenReturn(Role.builder().build());
        when(mockUserRepo.findByAreaAndRoleAndIsActiveAndIsApproved(eq("area"), any(Role.class), eq(true),
                eq(IsApproved.APPROVED))).thenReturn(Optional.empty());
        when(mockUserRepo.findByAreaAndRoleAndIsActive(eq("area"), any(Role.class), eq(true)))
                .thenReturn(Optional.empty());

        // Run the test
        userInfoServiceImplUnderTest.userApproval(0L, userDataDto);

        // Verify the results
        verify(mockUserRepo).save(any(User.class));
    }

    @Test
    void testUserApproval_UserInfoRepoFindByAreaAndRoleAndIsActiveReturnsNoItems() {
        // Setup
        final UserDataDto userDataDto = UserDataDto.builder()
                .userId(0L)
                .role("role")
                .area("area")
                .build();

        // Configure UserInfoRepo.findByUserId(...).
        final Optional<User> user = Optional.of(User.builder()
                .username("username")
                .password("password")
                .role(Role.builder().build())
                .area("area")
                .pinCode("pinCode")
                .isApproved(IsApproved.NOTAPPROVED)
                .isActive(false)
                .build());
        when(mockUserRepo.findByUserId(0L)).thenReturn(user);

        when(mockRoleInfoService.findByRoleName("roleName")).thenReturn(Role.builder().build());
        when(mockUserRepo.findByAreaAndRoleAndIsActiveAndIsApproved(eq("area"), any(Role.class), eq(true),
                eq(IsApproved.APPROVED))).thenReturn(Optional.empty());
        when(mockUserRepo.findByAreaAndRoleAndIsActive(eq("area"), any(Role.class), eq(true)))
                .thenReturn(Optional.of(Collections.emptyList()));

        // Run the test
        userInfoServiceImplUnderTest.userApproval(0L, userDataDto);

        // Verify the results
        verify(mockUserRepo).save(any(User.class));
        verify(mockRetailerConnectionService).saveDistributorConnection(any(DistributorToRetailer.class));
    }

    @Test
    void testUserApproval_UserInfoRepoFindByRoleAndIsActiveAndIsApprovedReturnsAbsent() {
        // Setup
        final UserDataDto userDataDto = UserDataDto.builder()
                .userId(0L)
                .role("role")
                .area("area")
                .build();

        // Configure UserInfoRepo.findByUserId(...).
        final Optional<User> user = Optional.of(User.builder()
                .username("username")
                .password("password")
                .role(Role.builder().build())
                .area("area")
                .pinCode("pinCode")
                .isApproved(IsApproved.NOTAPPROVED)
                .isActive(false)
                .build());
        when(mockUserRepo.findByUserId(0L)).thenReturn(user);

        when(mockRoleInfoService.findByRoleName("roleName")).thenReturn(Role.builder().build());

        // Configure UserInfoRepo.findByAreaAndRoleAndIsActiveAndIsApproved(...).
        final Optional<User> user1 = Optional.of(User.builder()
                .username("username")
                .password("password")
                .role(Role.builder().build())
                .area("area")
                .pinCode("pinCode")
                .isApproved(IsApproved.NOTAPPROVED)
                .isActive(false)
                .build());
        when(mockUserRepo.findByAreaAndRoleAndIsActiveAndIsApproved(eq("area"), any(Role.class), eq(true),
                eq(IsApproved.APPROVED))).thenReturn(user1);

        // Configure RetailerConnectionService.distributorToRetailerConnection(...).
        final DistributorToRetailer distributorToRetailer = DistributorToRetailer.builder()
                .distributor(User.builder()
                        .username("username")
                        .password("password")
                        .role(Role.builder().build())
                        .area("area")
                        .pinCode("pinCode")
                        .isApproved(IsApproved.NOTAPPROVED)
                        .isActive(false)
                        .build())
                .retailers(List.of(User.builder()
                        .username("username")
                        .password("password")
                        .role(Role.builder().build())
                        .area("area")
                        .pinCode("pinCode")
                        .isApproved(IsApproved.NOTAPPROVED)
                        .isActive(false)
                        .build()))
                .build();
        when(mockRetailerConnectionService.distributorToRetailerConnection(any(User.class)))
                .thenReturn(distributorToRetailer);

        when(mockUserRepo.findByRoleAndIsActiveAndIsApproved(any(Role.class), eq(true),
                eq(IsApproved.APPROVED))).thenReturn(Optional.empty());

        // Run the test
        userInfoServiceImplUnderTest.userApproval(0L, userDataDto);

        // Verify the results
        verify(mockUserRepo).save(any(User.class));
        verify(mockRetailerConnectionService).saveDistributorConnection(any(DistributorToRetailer.class));
    }

    @Test
    void testUserApproval_UserInfoRepoFindByRoleAndIsActiveAndIsApprovedReturnsNoItems() {
        // Setup
        final UserDataDto userDataDto = UserDataDto.builder()
                .userId(0L)
                .role("role")
                .area("area")
                .build();

        // Configure UserInfoRepo.findByUserId(...).
        final Optional<User> user = Optional.of(User.builder()
                .username("username")
                .password("password")
                .role(Role.builder().build())
                .area("area")
                .pinCode("pinCode")
                .isApproved(IsApproved.NOTAPPROVED)
                .isActive(false)
                .build());
        when(mockUserRepo.findByUserId(0L)).thenReturn(user);

        when(mockRoleInfoService.findByRoleName("roleName")).thenReturn(Role.builder().build());

        // Configure UserInfoRepo.findByAreaAndRoleAndIsActiveAndIsApproved(...).
        final Optional<User> user1 = Optional.of(User.builder()
                .username("username")
                .password("password")
                .role(Role.builder().build())
                .area("area")
                .pinCode("pinCode")
                .isApproved(IsApproved.NOTAPPROVED)
                .isActive(false)
                .build());
        when(mockUserRepo.findByAreaAndRoleAndIsActiveAndIsApproved(eq("area"), any(Role.class), eq(true),
                eq(IsApproved.APPROVED))).thenReturn(user1);

        // Configure RetailerConnectionService.distributorToRetailerConnection(...).
        final DistributorToRetailer distributorToRetailer = DistributorToRetailer.builder()
                .distributor(User.builder()
                        .username("username")
                        .password("password")
                        .role(Role.builder().build())
                        .area("area")
                        .pinCode("pinCode")
                        .isApproved(IsApproved.NOTAPPROVED)
                        .isActive(false)
                        .build())
                .retailers(List.of(User.builder()
                        .username("username")
                        .password("password")
                        .role(Role.builder().build())
                        .area("area")
                        .pinCode("pinCode")
                        .isApproved(IsApproved.NOTAPPROVED)
                        .isActive(false)
                        .build()))
                .build();
        when(mockRetailerConnectionService.distributorToRetailerConnection(any(User.class)))
                .thenReturn(distributorToRetailer);

        when(mockUserRepo.findByRoleAndIsActiveAndIsApproved(any(Role.class), eq(true),
                eq(IsApproved.APPROVED))).thenReturn(Optional.of(Collections.emptyList()));

        // Run the test
        userInfoServiceImplUnderTest.userApproval(0L, userDataDto);

        // Verify the results
        verify(mockUserRepo).save(any(User.class));
        verify(mockRetailerConnectionService).saveDistributorConnection(any(DistributorToRetailer.class));
    }

    @Test
    void testAllRequest() {
        // Setup
        final TokenDto tokenDto = TokenDto.builder()
                .userId(0L)
                .role(RoleDto.builder()
                        .name("name")
                        .build())
                .build();
        when(mockRoleInfoService.findByRoleName("distributor")).thenReturn(Role.builder().build());

        // Configure UserInfoRepo.findByRoleAndIsActiveAndIsApproved(...).
        final Optional<List<User>> users = Optional.of(List.of(User.builder()
                .username("username")
                .password("password")
                .role(Role.builder().build())
                .area("area")
                .pinCode("pinCode")
                .isApproved(IsApproved.NOTAPPROVED)
                .isActive(false)
                .build()));
        when(mockUserRepo.findByRoleAndIsActiveAndIsApproved(any(Role.class), eq(false),
                eq(IsApproved.NOTAPPROVED))).thenReturn(users);

        // Configure ModelMapper.map(...).
        final UserDto userDto = UserDto.builder()
                .password("password")
                .build();
        when(mockMapper.map(any(Object.class), eq(UserDto.class))).thenReturn(userDto);

        // Run the test
        final List<UserDto> result = userInfoServiceImplUnderTest.allRequest(tokenDto);

        // Verify the results
    }

    @Test
    void testAllRequest_UserInfoRepoFindByRoleAndIsActiveAndIsApprovedReturnsAbsent() {
        // Setup
        final TokenDto tokenDto = TokenDto.builder()
                .userId(0L)
                .role(RoleDto.builder()
                        .name("name")
                        .build())
                .build();
        when(mockRoleInfoService.findByRoleName("distributor")).thenReturn(Role.builder().build());
        when(mockUserRepo.findByRoleAndIsActiveAndIsApproved(any(Role.class), eq(false),
                eq(IsApproved.NOTAPPROVED))).thenReturn(Optional.empty());

        // Run the test
        assertThatThrownBy(() -> userInfoServiceImplUnderTest.allRequest(tokenDto))
                .isInstanceOf(NoRequestAvailableException.class);
    }

    @Test
    void testAllRequest_UserInfoRepoFindByRoleAndIsActiveAndIsApprovedReturnsNoItems() {
        // Setup
        final TokenDto tokenDto = TokenDto.builder()
                .userId(0L)
                .role(RoleDto.builder()
                        .name("name")
                        .build())
                .build();
        when(mockRoleInfoService.findByRoleName("distributor")).thenReturn(Role.builder().build());
        when(mockUserRepo.findByRoleAndIsActiveAndIsApproved(any(Role.class), eq(false),
                eq(IsApproved.NOTAPPROVED))).thenReturn(Optional.of(Collections.emptyList()));

        // Run the test
        final List<UserDto> result = userInfoServiceImplUnderTest.allRequest(tokenDto);

        // Verify the results
        assertThat(result).isEqualTo(Collections.emptyList());
    }

    @Test
    void testAllRequest_UserInfoRepoFindByIdReturnsAbsent() {
        // Setup
        final TokenDto tokenDto = TokenDto.builder()
                .userId(0L)
                .role(RoleDto.builder()
                        .name("name")
                        .build())
                .build();
        when(mockUserRepo.findById(0L)).thenReturn(Optional.empty());

        // Run the test
        assertThatThrownBy(() -> userInfoServiceImplUnderTest.allRequest(tokenDto))
                .isInstanceOf(NoSuchUserPresentException.class);
    }

    @Test
    void testAllRequest_UserInfoRepoFindByRoleAndAreaAndIsActiveAndIsApprovedReturnsAbsent() {
        // Setup
        final TokenDto tokenDto = TokenDto.builder()
                .userId(0L)
                .role(RoleDto.builder()
                        .name("name")
                        .build())
                .build();
        when(mockRoleInfoService.findByRoleName("distributor")).thenReturn(Role.builder().build());

        // Configure UserInfoRepo.findById(...).
        final Optional<User> user = Optional.of(User.builder()
                .username("username")
                .password("password")
                .role(Role.builder().build())
                .area("area")
                .pinCode("pinCode")
                .isApproved(IsApproved.NOTAPPROVED)
                .isActive(false)
                .build());
        when(mockUserRepo.findById(0L)).thenReturn(user);

        when(mockUserRepo.findByRoleAndAreaAndIsActiveAndIsApproved(any(Role.class), eq("area"), eq(false),
                eq(IsApproved.NOTAPPROVED))).thenReturn(Optional.empty());

        // Run the test
        assertThatThrownBy(() -> userInfoServiceImplUnderTest.allRequest(tokenDto))
                .isInstanceOf(NoRequestAvailableException.class);
    }

    @Test
    void testAllRequest_UserInfoRepoFindByRoleAndAreaAndIsActiveAndIsApprovedReturnsNoItems() {
        // Setup
        final TokenDto tokenDto = TokenDto.builder()
                .userId(0L)
                .role(RoleDto.builder()
                        .name("name")
                        .build())
                .build();
        when(mockRoleInfoService.findByRoleName("distributor")).thenReturn(Role.builder().build());

        // Configure UserInfoRepo.findById(...).
        final Optional<User> user = Optional.of(User.builder()
                .username("username")
                .password("password")
                .role(Role.builder().build())
                .area("area")
                .pinCode("pinCode")
                .isApproved(IsApproved.NOTAPPROVED)
                .isActive(false)
                .build());
        when(mockUserRepo.findById(0L)).thenReturn(user);

        when(mockUserRepo.findByRoleAndAreaAndIsActiveAndIsApproved(any(Role.class), eq("area"), eq(false),
                eq(IsApproved.NOTAPPROVED))).thenReturn(Optional.of(Collections.emptyList()));

        // Run the test
        final List<UserDto> result = userInfoServiceImplUnderTest.allRequest(tokenDto);

        // Verify the results
        assertThat(result).isEqualTo(Collections.emptyList());
    }

    @Test
    void testAllRequest_ModelMapperThrowsConfigurationException() {
        // Setup
        final TokenDto tokenDto = TokenDto.builder()
                .userId(0L)
                .role(RoleDto.builder()
                        .name("name")
                        .build())
                .build();
        when(mockRoleInfoService.findByRoleName("distributor")).thenReturn(Role.builder().build());

        // Configure UserInfoRepo.findByRoleAndIsActiveAndIsApproved(...).
        final Optional<List<User>> users = Optional.of(List.of(User.builder()
                .username("username")
                .password("password")
                .role(Role.builder().build())
                .area("area")
                .pinCode("pinCode")
                .isApproved(IsApproved.NOTAPPROVED)
                .isActive(false)
                .build()));
        when(mockUserRepo.findByRoleAndIsActiveAndIsApproved(any(Role.class), eq(false),
                eq(IsApproved.NOTAPPROVED))).thenReturn(users);

        when(mockMapper.map(any(Object.class), eq(UserDto.class))).thenThrow(ConfigurationException.class);

        // Run the test
        assertThatThrownBy(() -> userInfoServiceImplUnderTest.allRequest(tokenDto))
                .isInstanceOf(ConfigurationException.class);
    }

    @Test
    void testAllRequest_ModelMapperThrowsMappingException() {
        // Setup
        final TokenDto tokenDto = TokenDto.builder()
                .userId(0L)
                .role(RoleDto.builder()
                        .name("name")
                        .build())
                .build();
        when(mockRoleInfoService.findByRoleName("distributor")).thenReturn(Role.builder().build());

        // Configure UserInfoRepo.findByRoleAndIsActiveAndIsApproved(...).
        final Optional<List<User>> users = Optional.of(List.of(User.builder()
                .username("username")
                .password("password")
                .role(Role.builder().build())
                .area("area")
                .pinCode("pinCode")
                .isApproved(IsApproved.NOTAPPROVED)
                .isActive(false)
                .build()));
        when(mockUserRepo.findByRoleAndIsActiveAndIsApproved(any(Role.class), eq(false),
                eq(IsApproved.NOTAPPROVED))).thenReturn(users);

        when(mockMapper.map(any(Object.class), eq(UserDto.class))).thenThrow(MappingException.class);

        // Run the test
        assertThatThrownBy(() -> userInfoServiceImplUnderTest.allRequest(tokenDto))
                .isInstanceOf(MappingException.class);
    }

    @Test
    void testRemoveUser() {
        // Setup
        final TokenDto tokenDto = TokenDto.builder()
                .userId(0L)
                .role(RoleDto.builder()
                        .name("name")
                        .build())
                .build();
        final UserDataDto userDataDto = UserDataDto.builder()
                .userId(0L)
                .role("role")
                .area("area")
                .build();

        // Configure UserInfoRepo.findById(...).
        final Optional<User> user = Optional.of(User.builder()
                .username("username")
                .password("password")
                .role(Role.builder().build())
                .area("area")
                .pinCode("pinCode")
                .isApproved(IsApproved.NOTAPPROVED)
                .isActive(false)
                .build());
        when(mockUserRepo.findById(0L)).thenReturn(user);

        // Configure RetailerConnectionService.distributorToRetailerConnection(...).
        final DistributorToRetailer distributorToRetailer = DistributorToRetailer.builder()
                .distributor(User.builder()
                        .username("username")
                        .password("password")
                        .role(Role.builder().build())
                        .area("area")
                        .pinCode("pinCode")
                        .isApproved(IsApproved.NOTAPPROVED)
                        .isActive(false)
                        .build())
                .retailers(List.of(User.builder()
                        .username("username")
                        .password("password")
                        .role(Role.builder().build())
                        .area("area")
                        .pinCode("pinCode")
                        .isApproved(IsApproved.NOTAPPROVED)
                        .isActive(false)
                        .build()))
                .build();
        when(mockRetailerConnectionService.distributorToRetailerConnection(any(User.class)))
                .thenReturn(distributorToRetailer);

        // Configure UserInfoRepo.save(...).
        final User user1 = User.builder()
                .username("username")
                .password("password")
                .role(Role.builder().build())
                .area("area")
                .pinCode("pinCode")
                .isApproved(IsApproved.NOTAPPROVED)
                .isActive(false)
                .build();
        when(mockUserRepo.save(any(User.class))).thenReturn(user1);

        // Configure ModelMapper.map(...).
        final UserDto userDto = UserDto.builder()
                .password("password")
                .build();
        when(mockMapper.map(any(Object.class), eq(UserDto.class))).thenReturn(userDto);

        // Run the test
        final UserDto result = userInfoServiceImplUnderTest.removeUser(tokenDto, userDataDto);

        // Verify the results
        verify(mockRetailerConnectionService).saveDistributorConnection(any(DistributorToRetailer.class));
        verify(mockRetailerConnectionService).removeDistributorRetailerConnection(any(User.class));
    }

    @Test
    void testRemoveUser_UserInfoRepoFindByIdReturnsAbsent() {
        // Setup
        final TokenDto tokenDto = TokenDto.builder()
                .userId(0L)
                .role(RoleDto.builder()
                        .name("name")
                        .build())
                .build();
        final UserDataDto userDataDto = UserDataDto.builder()
                .userId(0L)
                .role("role")
                .area("area")
                .build();
        when(mockUserRepo.findById(0L)).thenReturn(Optional.empty());

        // Run the test
        assertThatThrownBy(() -> userInfoServiceImplUnderTest.removeUser(tokenDto, userDataDto))
                .isInstanceOf(NoSuchUserPresentException.class);
    }

    @Test
    void testRemoveUser_UserInfoRepoSaveThrowsOptimisticLockingFailureException() {
        // Setup
        final TokenDto tokenDto = TokenDto.builder()
                .userId(0L)
                .role(RoleDto.builder()
                        .name("name")
                        .build())
                .build();
        final UserDataDto userDataDto = UserDataDto.builder()
                .userId(0L)
                .role("role")
                .area("area")
                .build();

        // Configure UserInfoRepo.findById(...).
        final Optional<User> user = Optional.of(User.builder()
                .username("username")
                .password("password")
                .role(Role.builder().build())
                .area("area")
                .pinCode("pinCode")
                .isApproved(IsApproved.NOTAPPROVED)
                .isActive(false)
                .build());
        when(mockUserRepo.findById(0L)).thenReturn(user);

        // Configure RetailerConnectionService.distributorToRetailerConnection(...).
        final DistributorToRetailer distributorToRetailer = DistributorToRetailer.builder()
                .distributor(User.builder()
                        .username("username")
                        .password("password")
                        .role(Role.builder().build())
                        .area("area")
                        .pinCode("pinCode")
                        .isApproved(IsApproved.NOTAPPROVED)
                        .isActive(false)
                        .build())
                .retailers(List.of(User.builder()
                        .username("username")
                        .password("password")
                        .role(Role.builder().build())
                        .area("area")
                        .pinCode("pinCode")
                        .isApproved(IsApproved.NOTAPPROVED)
                        .isActive(false)
                        .build()))
                .build();
        when(mockRetailerConnectionService.distributorToRetailerConnection(any(User.class)))
                .thenReturn(distributorToRetailer);

        when(mockUserRepo.save(any(User.class))).thenThrow(OptimisticLockingFailureException.class);

        // Run the test
        assertThatThrownBy(() -> userInfoServiceImplUnderTest.removeUser(tokenDto, userDataDto))
                .isInstanceOf(OptimisticLockingFailureException.class);
    }

    @Test
    void testRemoveUser_ModelMapperThrowsConfigurationException() {
        // Setup
        final TokenDto tokenDto = TokenDto.builder()
                .userId(0L)
                .role(RoleDto.builder()
                        .name("name")
                        .build())
                .build();
        final UserDataDto userDataDto = UserDataDto.builder()
                .userId(0L)
                .role("role")
                .area("area")
                .build();

        // Configure UserInfoRepo.findById(...).
        final Optional<User> user = Optional.of(User.builder()
                .username("username")
                .password("password")
                .role(Role.builder().build())
                .area("area")
                .pinCode("pinCode")
                .isApproved(IsApproved.NOTAPPROVED)
                .isActive(false)
                .build());
        when(mockUserRepo.findById(0L)).thenReturn(user);

        // Configure RetailerConnectionService.distributorToRetailerConnection(...).
        final DistributorToRetailer distributorToRetailer = DistributorToRetailer.builder()
                .distributor(User.builder()
                        .username("username")
                        .password("password")
                        .role(Role.builder().build())
                        .area("area")
                        .pinCode("pinCode")
                        .isApproved(IsApproved.NOTAPPROVED)
                        .isActive(false)
                        .build())
                .retailers(List.of(User.builder()
                        .username("username")
                        .password("password")
                        .role(Role.builder().build())
                        .area("area")
                        .pinCode("pinCode")
                        .isApproved(IsApproved.NOTAPPROVED)
                        .isActive(false)
                        .build()))
                .build();
        when(mockRetailerConnectionService.distributorToRetailerConnection(any(User.class)))
                .thenReturn(distributorToRetailer);

        // Configure UserInfoRepo.save(...).
        final User user1 = User.builder()
                .username("username")
                .password("password")
                .role(Role.builder().build())
                .area("area")
                .pinCode("pinCode")
                .isApproved(IsApproved.NOTAPPROVED)
                .isActive(false)
                .build();
        when(mockUserRepo.save(any(User.class))).thenReturn(user1);

        when(mockMapper.map(any(Object.class), eq(UserDto.class))).thenThrow(ConfigurationException.class);

        // Run the test
        assertThatThrownBy(() -> userInfoServiceImplUnderTest.removeUser(tokenDto, userDataDto))
                .isInstanceOf(ConfigurationException.class);
        verify(mockRetailerConnectionService).saveDistributorConnection(any(DistributorToRetailer.class));
    }

    @Test
    void testRemoveUser_ModelMapperThrowsMappingException() {
        // Setup
        final TokenDto tokenDto = TokenDto.builder()
                .userId(0L)
                .role(RoleDto.builder()
                        .name("name")
                        .build())
                .build();
        final UserDataDto userDataDto = UserDataDto.builder()
                .userId(0L)
                .role("role")
                .area("area")
                .build();

        // Configure UserInfoRepo.findById(...).
        final Optional<User> user = Optional.of(User.builder()
                .username("username")
                .password("password")
                .role(Role.builder().build())
                .area("area")
                .pinCode("pinCode")
                .isApproved(IsApproved.NOTAPPROVED)
                .isActive(false)
                .build());
        when(mockUserRepo.findById(0L)).thenReturn(user);

        // Configure RetailerConnectionService.distributorToRetailerConnection(...).
        final DistributorToRetailer distributorToRetailer = DistributorToRetailer.builder()
                .distributor(User.builder()
                        .username("username")
                        .password("password")
                        .role(Role.builder().build())
                        .area("area")
                        .pinCode("pinCode")
                        .isApproved(IsApproved.NOTAPPROVED)
                        .isActive(false)
                        .build())
                .retailers(List.of(User.builder()
                        .username("username")
                        .password("password")
                        .role(Role.builder().build())
                        .area("area")
                        .pinCode("pinCode")
                        .isApproved(IsApproved.NOTAPPROVED)
                        .isActive(false)
                        .build()))
                .build();
        when(mockRetailerConnectionService.distributorToRetailerConnection(any(User.class)))
                .thenReturn(distributorToRetailer);

        // Configure UserInfoRepo.save(...).
        final User user1 = User.builder()
                .username("username")
                .password("password")
                .role(Role.builder().build())
                .area("area")
                .pinCode("pinCode")
                .isApproved(IsApproved.NOTAPPROVED)
                .isActive(false)
                .build();
        when(mockUserRepo.save(any(User.class))).thenReturn(user1);

        when(mockMapper.map(any(Object.class), eq(UserDto.class))).thenThrow(MappingException.class);

        // Run the test
        assertThatThrownBy(() -> userInfoServiceImplUnderTest.removeUser(tokenDto, userDataDto))
                .isInstanceOf(MappingException.class);
        verify(mockRetailerConnectionService).saveDistributorConnection(any(DistributorToRetailer.class));
    }

    @Test
    void testGetActiveApprovedUsers() {
        // Setup
        // Configure UserInfoRepo.findByIsActiveAndIsApproved(...).
        final Optional<List<User>> users = Optional.of(List.of(User.builder()
                .username("username")
                .password("password")
                .role(Role.builder().build())
                .area("area")
                .pinCode("pinCode")
                .isApproved(IsApproved.NOTAPPROVED)
                .isActive(false)
                .build()));
        when(mockUserRepo.findByIsActiveAndIsApproved(true, IsApproved.APPROVED)).thenReturn(users);

        // Configure ModelMapper.map(...).
        final UserDto userDto = UserDto.builder()
                .password("password")
                .build();
        when(mockMapper.map(any(Object.class), eq(UserDto.class))).thenReturn(userDto);

        // Run the test
        final List<UserDto> result = userInfoServiceImplUnderTest.getActiveApprovedUsers();

        // Verify the results
    }

    @Test
    void testGetActiveApprovedUsers_UserInfoRepoReturnsAbsent() {
        // Setup
        when(mockUserRepo.findByIsActiveAndIsApproved(true, IsApproved.APPROVED)).thenReturn(Optional.empty());

        // Run the test
        assertThatThrownBy(() -> userInfoServiceImplUnderTest.getActiveApprovedUsers())
                .isInstanceOf(NoSuchUserPresentException.class);
    }

    @Test
    void testGetActiveApprovedUsers_UserInfoRepoReturnsNoItems() {
        // Setup
        when(mockUserRepo.findByIsActiveAndIsApproved(true, IsApproved.APPROVED))
                .thenReturn(Optional.of(Collections.emptyList()));

        // Run the test
        final List<UserDto> result = userInfoServiceImplUnderTest.getActiveApprovedUsers();

        // Verify the results
        assertThat(result).isEqualTo(Collections.emptyList());
    }

    @Test
    void testGetActiveApprovedUsers_ModelMapperThrowsConfigurationException() {
        // Setup
        // Configure UserInfoRepo.findByIsActiveAndIsApproved(...).
        final Optional<List<User>> users = Optional.of(List.of(User.builder()
                .username("username")
                .password("password")
                .role(Role.builder().build())
                .area("area")
                .pinCode("pinCode")
                .isApproved(IsApproved.NOTAPPROVED)
                .isActive(false)
                .build()));
        when(mockUserRepo.findByIsActiveAndIsApproved(true, IsApproved.APPROVED)).thenReturn(users);

        when(mockMapper.map(any(Object.class), eq(UserDto.class))).thenThrow(ConfigurationException.class);

        // Run the test
        assertThatThrownBy(() -> userInfoServiceImplUnderTest.getActiveApprovedUsers())
                .isInstanceOf(ConfigurationException.class);
    }

    @Test
    void testGetActiveApprovedUsers_ModelMapperThrowsMappingException() {
        // Setup
        // Configure UserInfoRepo.findByIsActiveAndIsApproved(...).
        final Optional<List<User>> users = Optional.of(List.of(User.builder()
                .username("username")
                .password("password")
                .role(Role.builder().build())
                .area("area")
                .pinCode("pinCode")
                .isApproved(IsApproved.NOTAPPROVED)
                .isActive(false)
                .build()));
        when(mockUserRepo.findByIsActiveAndIsApproved(true, IsApproved.APPROVED)).thenReturn(users);

        when(mockMapper.map(any(Object.class), eq(UserDto.class))).thenThrow(MappingException.class);

        // Run the test
        assertThatThrownBy(() -> userInfoServiceImplUnderTest.getActiveApprovedUsers())
                .isInstanceOf(MappingException.class);
    }

    @Test
    void testFindUserByRoleAndIsActiveAndIsApproved() {
        // Setup
        final Role role = Role.builder().build();

        // Configure UserInfoRepo.findByRoleAndIsActiveAndIsApproved(...).
        final Optional<List<User>> users = Optional.of(List.of(User.builder()
                .username("username")
                .password("password")
                .role(Role.builder().build())
                .area("area")
                .pinCode("pinCode")
                .isApproved(IsApproved.NOTAPPROVED)
                .isActive(false)
                .build()));
        when(mockUserRepo.findByRoleAndIsActiveAndIsApproved(any(Role.class), eq(false),
                eq(IsApproved.NOTAPPROVED))).thenReturn(users);

        // Run the test
        final List<User> result = userInfoServiceImplUnderTest.findUserByRoleAndIsActiveAndIsApproved(role, false,
                IsApproved.NOTAPPROVED);

        // Verify the results
    }

    @Test
    void testFindUserByRoleAndIsActiveAndIsApproved_UserInfoRepoReturnsAbsent() {
        // Setup
        final Role role = Role.builder().build();
        when(mockUserRepo.findByRoleAndIsActiveAndIsApproved(any(Role.class), eq(false),
                eq(IsApproved.NOTAPPROVED))).thenReturn(Optional.empty());

        // Run the test
        assertThatThrownBy(() -> userInfoServiceImplUnderTest.findUserByRoleAndIsActiveAndIsApproved(role, false,
                IsApproved.NOTAPPROVED)).isInstanceOf(NoRequestAvailableException.class);
    }

    @Test
    void testFindUserByRoleAndIsActiveAndIsApproved_UserInfoRepoReturnsNoItems() {
        // Setup
        final Role role = Role.builder().build();
        when(mockUserRepo.findByRoleAndIsActiveAndIsApproved(any(Role.class), eq(false),
                eq(IsApproved.NOTAPPROVED))).thenReturn(Optional.of(Collections.emptyList()));

        // Run the test
        final List<User> result = userInfoServiceImplUnderTest.findUserByRoleAndIsActiveAndIsApproved(role, false,
                IsApproved.NOTAPPROVED);

        // Verify the results
        assertThat(result).isEqualTo(Collections.emptyList());
    }
}
