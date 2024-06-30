package com.company.dms.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
/**
 * Configuration class for security-related beans.
 */
@Configuration
public class SecurityConfig {
    /**
     * Creates a BCryptPasswordEncoder bean.
     * @return BCryptPasswordEncoder instance
     */
    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
