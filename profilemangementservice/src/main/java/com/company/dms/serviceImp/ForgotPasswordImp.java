package com.company.dms.serviceImp;

import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.company.dms.customenum.IsApproved;
import com.company.dms.dto.NotificationDto;
import com.company.dms.dto.forgotpassworddto.EmailDto;
import com.company.dms.dto.forgotpassworddto.ForgotPasswordDto;
import com.company.dms.dto.forgotpassworddto.NewPasswordDto;
import com.company.dms.dto.forgotpassworddto.OtpDto;
import com.company.dms.dto.userdto.UserDto;
import com.company.dms.dto.userdto.UserServiceResponseDto;
import com.company.dms.entity.ForgotPassword;
import com.company.dms.exception.InvalidOtpEnteredException;
import com.company.dms.exception.InvalidStepsException;
import com.company.dms.exception.NotActiveAnyMoreException;
import com.company.dms.exception.TimeLimitExceedException;
import com.company.dms.otherservicecall.NotificationService;
import com.company.dms.repository.ForgotPasswordRepo;
import com.company.dms.service.ForgotPasswordService;
import com.company.dms.service.UserInfoService;
import com.company.dms.utils.logger.LoggableClass;
import com.company.dms.utils.logger.LoggableMethod;
import com.company.dms.utils.logger.NotLoggableMethod;

import java.time.Duration;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.Optional;
import java.util.Random;

/**
 * Implementation of the ForgotPasswordService interface.
 */
@LoggableClass
@Slf4j
@Service
public class ForgotPasswordImp implements ForgotPasswordService {
    private final UserInfoService userInfoService;
    private final NotificationService notificationService;
    private final ForgotPasswordRepo forgotPasswordRepo;
    private final ModelMapper mapper;
    @Autowired
    public ForgotPasswordImp(UserInfoService userInfoService,
                             NotificationService notificationService,
                             ForgotPasswordRepo forgotPasswordRepo,
                             ModelMapper mapper){
        this.userInfoService = userInfoService;
        this.notificationService = notificationService;
        this.forgotPasswordRepo = forgotPasswordRepo;
        this.mapper = mapper;
    }
    /**
     * Initiates the forgot password process by sending an OTP to the user's email.
     *
     * @param emailDto The email DTO containing the user's email.
     * @return ForgotPasswordDto The DTO containing the forgot password information.
     */
    @LoggableMethod
    @Override
    public ForgotPasswordDto forgotPassword(EmailDto emailDto) {
        userVerification(emailDto.getEmail());
        ForgotPassword forgotPassword = new ForgotPassword();
        int otp = generateOTP();
        log.info("otp : "+otp);
        forgotPassword.setEmail(emailDto.getEmail());
        forgotPassword.setOtp(otp);
        NotificationDto notificationDto = new NotificationDto();
        notificationDto.setMessage("your otp is :"+ otp);
        notificationDto.setSubject("Password reset mail");
        notificationService.sendNotification(emailDto.getEmail(), notificationDto);
        return forgotPasswordDto(forgotPasswordRepo.save(forgotPassword));
    }

    /**
     * Verifies the OTP entered by the user.
     *
     * @param otpDto The OTP DTO containing the email and OTP.
     */
    @LoggableMethod
    @Override
    public void verifyOtp(OtpDto otpDto) {
        userVerification(otpDto.getEmail());
        Optional<ForgotPassword> forgotPasswordOptional = forgotPasswordRepo.findByEmail(otpDto.getEmail());
        if(forgotPasswordOptional.isEmpty()) throw new InvalidStepsException("follow correct steps to verify otp");
        if(otpDto.getOtp()==forgotPasswordOptional.get().getOtp()){
            LocalDateTime currentTime = LocalDateTime.now();
            Date storedTime = forgotPasswordOptional.get().getDate();
            Instant instant = storedTime.toInstant();
            ZoneId zoneId = ZoneId.systemDefault();
            LocalDateTime storedLocalDateTime = instant.atZone(zoneId).toLocalDateTime();
            Duration duration = Duration.between(storedLocalDateTime, currentTime);
            long minutesDifference = duration.toMinutes();
            if(minutesDifference>5){
                throw new TimeLimitExceedException("time lapsed for entering the otp");
            }
            forgotPasswordOptional.get().setVerified(true);
            forgotPasswordRepo.save(forgotPasswordOptional.get());
        }else{
            throw new InvalidOtpEnteredException("Invalid otp entered by user");
        }
    }

    /**
     * Sets a new password for the user.
     *
     * @param newPasswordDto The DTO containing the new password and email.
     * @return UserDto The DTO containing the updated user information.
     */
    @LoggableMethod
    @Transactional
    @Override
    public UserDto setNewPassword(NewPasswordDto newPasswordDto){
        Optional<ForgotPassword> existUser = forgotPasswordRepo.findByEmail(newPasswordDto.getEmail());
        if (existUser.isEmpty() || !existUser.get().isVerified()) throw new InvalidStepsException("Follow proper steps to recover password");
        userVerification(newPasswordDto.getEmail());
        forgotPasswordRepo.deleteByEmail(newPasswordDto.getEmail());
        return userInfoService.saveUser(newPasswordDto.getEmail(), newPasswordDto.getPassword());
    }

    /**
     * Generates a random OTP.
     *
     * @return int The generated OTP.
     */
    @NotLoggableMethod
    private int generateOTP() {
        Random random = new Random();
        return 100000 + random.nextInt(900000);
    }

    /**
     * Verifies the user's eligibility for password recovery.
     *
     * @param email The email of the user.
     */
    @LoggableMethod
    private void userVerification(String email){
        UserServiceResponseDto user = userInfoService.getUserInfoByMail(email);
        if(IsApproved.NOTAPPROVED.equals(user.getIsApproved()) || !user.isActive()) throw new NotActiveAnyMoreException("Not the part of organisation anymore");
    }

    /**
     * Converts a ForgotPassword entity to DTO.
     *
     * @param forgotPassword The ForgotPassword entity.
     * @return ForgotPasswordDto The corresponding DTO.
     */
    @NotLoggableMethod
    private ForgotPasswordDto forgotPasswordDto(ForgotPassword forgotPassword) {
        return mapper.map(forgotPassword, ForgotPasswordDto.class);
    }
}
