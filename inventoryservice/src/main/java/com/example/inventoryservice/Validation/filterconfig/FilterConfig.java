package com.example.inventoryservice.Validation.filterconfig;

import com.example.inventoryservice.Validation.filter.AddInventoryFilter;
import com.example.inventoryservice.Validation.filter.ApproveRequestFilter;
import com.example.inventoryservice.Validation.filter.StockUpdateRetailerFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
/**
 * Configuration class for registering filters.
 */
@Configuration
public class FilterConfig {
    /**
     * Registers the ApproveRequestFilter for "/request/inProgress" URL pattern.
     * @return FilterRegistrationBean for ApproveRequestFilter
     */
    @Bean
    public FilterRegistrationBean<ApproveRequestFilter> approveRequestForInventoryFilterRegistrationBean() {
        FilterRegistrationBean<ApproveRequestFilter> registrationBean = new FilterRegistrationBean<>();
        registrationBean.setFilter(new ApproveRequestFilter());
        registrationBean.addUrlPatterns("/request/inProgress");
        registrationBean.setName("ApproveRequestFilter");
        return registrationBean;
    }

    /**
     * Registers the AddInventoryFilter for "/inventory/add" URL pattern.
     * @return FilterRegistrationBean for AddInventoryFilter
     */
    @Bean
    public FilterRegistrationBean<AddInventoryFilter> addInventoryFilterRegistrationBean() {
        FilterRegistrationBean<AddInventoryFilter> registrationBean = new FilterRegistrationBean<>();
        registrationBean.setFilter(new AddInventoryFilter());
        registrationBean.addUrlPatterns("/inventory/add");
        registrationBean.setName("AddInventoryFilter");
        return registrationBean;
    }

    /**
     * Registers the StockUpdateRetailerFilter for "/stock/retailer/update" URL pattern.
     * @return FilterRegistrationBean for StockUpdateRetailerFilter
     */
    @Bean
    public FilterRegistrationBean<StockUpdateRetailerFilter> retailerStockUpdateFilterRegistrationBean() {
        FilterRegistrationBean<StockUpdateRetailerFilter> registrationBean = new FilterRegistrationBean<>();
        registrationBean.setFilter(new StockUpdateRetailerFilter());
        registrationBean.addUrlPatterns("/stock/retailer/update");
        registrationBean.setName("StockUpdateRetailerFilter");
        return registrationBean;
    }
}

