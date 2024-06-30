package com.company.dms.validation.filterconfig;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.company.dms.validation.filter.CancelRequestFilter;
import com.company.dms.validation.filter.RaiseInventoryFilter;
import com.company.dms.validation.filter.UpdateRequestFilter;
/**
 * Configuration class for registering filters in the servlet container.
 */
@Configuration
public class FilterConfig {
    /**
     * Registers the RaiseInventoryFilter for "/inventory/request/raise" URL pattern.
     */
    @Bean
    public FilterRegistrationBean<RaiseInventoryFilter>raiseInventoryFilterRegistrationBean(){
        FilterRegistrationBean<RaiseInventoryFilter> registrationBean = new FilterRegistrationBean<>();
        registrationBean.setFilter(new RaiseInventoryFilter());
        registrationBean.addUrlPatterns("/inventory/request/raise");
        registrationBean.setName("RaiseInventoryFilter");
        return registrationBean;
    }

    /**
     * Registers the CancelRequestFilter for "/inventory/request/cancel" URL pattern.
     */
    @Bean
    public FilterRegistrationBean<CancelRequestFilter> cancelRequestFilterRegistrationBean(){
        FilterRegistrationBean<CancelRequestFilter> registrationBean = new FilterRegistrationBean<>();
        registrationBean.setFilter(new CancelRequestFilter());
        registrationBean.addUrlPatterns("/inventory/request/cancel");
        registrationBean.setName("CancelRequestFilter");
        return registrationBean;
    }
    /**
     * Registers the UpdateRequestFilter for "/inventory/request/update" URL pattern.
     */
    @Bean
    public FilterRegistrationBean<UpdateRequestFilter> updateRequestFilterRegistrationBean(){
        FilterRegistrationBean<UpdateRequestFilter> registrationBean = new FilterRegistrationBean<>();
        registrationBean.setFilter(new UpdateRequestFilter());
        registrationBean.addUrlPatterns("/inventory/request/update");
        registrationBean.setName("UpdateRequestFilter");
        return registrationBean;
    }
}
