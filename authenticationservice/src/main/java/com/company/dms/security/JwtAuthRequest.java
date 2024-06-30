package com.company.dms.security;

import org.springframework.stereotype.Component;

/**
 * Represents a JWT authentication request containing an email and password.
 */
@Component
public class JwtAuthRequest {
    private String email;
    private String password;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
