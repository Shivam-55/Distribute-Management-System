package com.company.dms.validation.filterconfig;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.company.dms.validation.filter.*;
/**
 * Configuration class for registering filters in the servlet container.
 */
@Configuration
public class FilterConfig {
    /**
     * Registers the RegistrationFilter for "/user/registration" URL pattern.
     *
     * @return The FilterRegistrationBean for the RegistrationFilter.
     */
    @Bean
    public FilterRegistrationBean<RegistrationFilter> userRegistrationFilterRegistrationBean(){
        FilterRegistrationBean<RegistrationFilter> registrationBean = new FilterRegistrationBean<>();
        registrationBean.setFilter(new RegistrationFilter());
        registrationBean.addUrlPatterns("/user/registration");
        registrationBean.setName("RegistrationFilter");
        return registrationBean;
    }
    /**
     * Registers the LoginFilter for "/user/login" URL pattern.
     *
     * @return The FilterRegistrationBean for the LoginFilter.
     */
    @Bean
    public FilterRegistrationBean<LoginFilter> userLoginFilterRegistrationBean() {
        FilterRegistrationBean<LoginFilter> registrationBean = new FilterRegistrationBean<>();
        registrationBean.setFilter(new LoginFilter());
        registrationBean.addUrlPatterns("/user/login");
        registrationBean.setName("LoginFilter");
        return registrationBean;
    }
    /**
     * Registers the RequestApproveFilter for "/user/requests/approve" URL pattern.
     *
     * @return The FilterRegistrationBean for the RequestApproveFilter.
     */
    @Bean
    public FilterRegistrationBean<RequestApproveFilter> requestApprovedFilterRegistrationBean(){
        FilterRegistrationBean<RequestApproveFilter> registrationBean = new FilterRegistrationBean<>();
        registrationBean.setFilter(new RequestApproveFilter());
        registrationBean.addUrlPatterns("/user/requests/approve");
        registrationBean.setName("RequestApproveFilter");
        return registrationBean;
    }

    /**
     * Registers the RemoveUserFilter for "/user/remove" URL pattern.
     *
     * @return The FilterRegistrationBean for the RemoveUserFilter.
     */
    @Bean
    public FilterRegistrationBean<RemoveUserFilter> removeUserFilterRegistrationBean(){
        FilterRegistrationBean<RemoveUserFilter> registrationBean = new FilterRegistrationBean<>();
        registrationBean.setFilter(new RemoveUserFilter());
        registrationBean.addUrlPatterns("/user/remove");
        registrationBean.setName("RemoveUserFilter");
        return registrationBean;
    }

    /**
     * Registers the UserInfoFilter for "/user/info/{userId}" URL pattern.
     *
     * @return The FilterRegistrationBean for the UserInfoFilter.
     */
    @Bean
    public FilterRegistrationBean<UserInfoFilter> userInfoFilterRegistrationBean(){
        FilterRegistrationBean<UserInfoFilter> registrationBean = new FilterRegistrationBean<>();
        registrationBean.setFilter(new UserInfoFilter());
        registrationBean.addUrlPatterns("/user/info/{userId}");
        registrationBean.setName("UserInfoFilter");
        return registrationBean;
    }
    /**
     * Registers the ForgotPasswordFilter for "/password/forgot" URL pattern.
     *
     * @return The FilterRegistrationBean for the ForgotPasswordFilter.
     */
    @Bean
    public FilterRegistrationBean<ForgotPasswordFilter> forgotPasswordFilterRegistrationBean(){
        FilterRegistrationBean<ForgotPasswordFilter> registrationBean = new FilterRegistrationBean<>();
        registrationBean.setFilter(new ForgotPasswordFilter());
        registrationBean.addUrlPatterns("/password/forgot");
        registrationBean.setName("ForgotPasswordFilter");
        return registrationBean;
    }
    /**
     * Registers the VerifyOtpFilter for "/otp/verify" URL pattern.
     *
     * @return The FilterRegistrationBean for the VerifyOtpFilter.
     */
    @Bean
    public FilterRegistrationBean<VerifyOtpFilter> otpFilterRegistrationBean(){
        FilterRegistrationBean<VerifyOtpFilter> registrationBean = new FilterRegistrationBean<>();
        registrationBean.setFilter(new VerifyOtpFilter());
        registrationBean.addUrlPatterns("/otp/verify");
        registrationBean.setName("VerifyOtpFilter");
        return registrationBean;
    }
    /**
     * Registers the PasswordRecoverFilter for "/new/password" URL pattern.
     *
     * @return The FilterRegistrationBean for the PasswordRecoverFilter.
     */
    @Bean
    public FilterRegistrationBean<PasswordRecoverFilter> recoverPasswordRegistrationBean(){
        FilterRegistrationBean<PasswordRecoverFilter> registrationBean = new FilterRegistrationBean<>();
        registrationBean.setFilter(new PasswordRecoverFilter());
        registrationBean.addUrlPatterns("/new/password");
        registrationBean.setName("PasswordRecoverFilter");
        return registrationBean;
    }
}
