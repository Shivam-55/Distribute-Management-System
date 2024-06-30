package com.company.micro.security.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.SecurityWebFiltersOrder;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;

import java.util.List;

/**
 * Configuration class for security settings.
 * This class configures security settings for the application using Spring Security.
 */
@Configuration
@EnableWebFluxSecurity
public class SecurityConfig {
    private static final String ADMIN =  "admin" ;
    private static final String DISTRIBUTOR =  "distributor" ;
    private static final String RETAILER =  "retailer" ;
    private final JwtAuthenticationFilter jwtAuthenticationFilter;
    @Autowired
    public SecurityConfig(JwtAuthenticationFilter jwtAuthenticationFilter){
        this.jwtAuthenticationFilter = jwtAuthenticationFilter;
    }

    // Define endpoints that are open for public access
    private static final List<String> openAPiEndPoints=List.of(
            "/user/login","/user/registration","/new/password", "/user/password/recover",
            "/password/forgot","/otp/verify","/location/{pinCode}", "/location/{city}"
    );

    private static final String[] adminAPiEndPoints= {
            "/connection/get/complain","/inventory/add","/inventory/remove"
    };

    private static final String[] adminDistributorAPiEndPoints = {
            "/connection/get/feedback","/inventory/{inventoryName}","/stock/**",
            "/user/connection/**","user/request/approve","/inventory/request/showRequests","/request/inProgress","/inventory/request/pending/{userId}/{inventoryId}"
    };

    private static final String[] adminDistributorRetailerEndPoints = {
            "/inventory/request/statusUpdate"
    };

    private static final String[] distributorRetailerEndPoints = {
            "connection/feedback" ,"/inventory/request/raise","/inventory/request/allRequests",
            "/inventory/request/cancel","/inventory/request/update"
    };

    private static final String[] retailerEndPoints = {
            "/complain/complain"
    };
    /**
     * Configures the security filters chain.
     * @param http The ServerHttpSecurity to configure
     * @return The SecurityWebFilterChain
     */
    @Bean
    SecurityWebFilterChain securityWebFilterChain(ServerHttpSecurity http) {
        return http
                .csrf(ServerHttpSecurity.CsrfSpec::disable)
                .authorizeExchange(authorizeExchangeSpec -> authorizeExchangeSpec
                        .pathMatchers("/eureka/**", "/api/login/**", "/swagger-ui/**",
                                "/v3/api-docs/**", "/swagger-ui.html", "/swagger-ui/**", "/swagger-resources/**",
                                "/webjars/**", "/complain-service/v3/api-docs","/inventory-management-service/v3/api-docs",
                                "/profile-management-service/v3/api-docs","/request-management-service/v3/api-docs")
                        .permitAll()
                        .pathMatchers(adminAPiEndPoints).hasRole(ADMIN)
                        .pathMatchers(adminDistributorAPiEndPoints).hasAnyRole(ADMIN, DISTRIBUTOR)
                        .pathMatchers(adminDistributorRetailerEndPoints).hasAnyRole(ADMIN, DISTRIBUTOR, RETAILER)
                        .pathMatchers(retailerEndPoints).hasRole(RETAILER)
                        .pathMatchers(distributorRetailerEndPoints).hasAnyRole(DISTRIBUTOR, RETAILER)
                        .pathMatchers(openAPiEndPoints.toArray(new String[0])).permitAll()
                        .anyExchange().authenticated())
                .addFilterBefore(jwtAuthenticationFilter, SecurityWebFiltersOrder.AUTHENTICATION)
                .build();
    }
}