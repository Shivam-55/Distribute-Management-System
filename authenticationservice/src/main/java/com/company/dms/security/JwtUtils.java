package com.company.dms.security;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;


/**
 * Utility class for generating JWT tokens.
 */
@Component
public class JwtUtils {

    /** The secret key used for signing the JWT token. */
    private static String key = "afafasfafafasfasfasfafacasdasfasxASFACASDFACASDFASFASFDAFASFASDAADSCSDFADCVSGCFVADXCcadwavfsfarvf";

    /** The expiration time for the JWT token (1 day). */
    private static final long EXPIRATION_TIME = 86400000; // 1 day

    /**
     * Generates a JWT token using the provided email, user ID, and role.
     *
     * @param email the email associated with the token
     * @param userId the user ID associated with the token
     * @param role the role associated with the token
     * @return the generated JWT token
     */
    public String generateToken(String email, Long userId, String role) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("userId",userId);
        claims.put("role",role);
        claims.put("email",email);
        return doGenerateToken(claims, email);
    }

    /**
     * Generates a JWT token with the provided claims and subject.
     *
     * @param claims the claims to include in the token
     * @param subject the subject of the token
     * @return the generated JWT token
     */
    private String doGenerateToken(Map<String, Object> claims, String subject) {
        return Jwts.builder().setClaims(claims).setSubject(subject).setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .signWith(SignatureAlgorithm.HS512, key).compact();
    }

}
