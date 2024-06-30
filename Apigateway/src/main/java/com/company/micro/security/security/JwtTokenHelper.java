package com.company.micro.security.security;

import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import com.company.micro.security.exception.ApiException;
import com.company.micro.security.util.UserResponse;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.security.SignatureException;

/**
 * Helper class for managing JWT tokens used in security.
 */
@Component
public class JwtTokenHelper {

	@Value("${secret-key}")
	private String secretKey;

	/**
	 * Validates the JWT token.
	 *
	 * @param token The JWT token to validate
	 * @return true if the token is valid, false otherwise
	 */
	public boolean validateToken(String token) {
		try {
			Jws<Claims> claimsJws = Jwts.parserBuilder()
					.setSigningKey(secretKey)
					.build()
					.parseClaimsJws(token);

			// Check if the token has expired
			if (claimsJws.getBody().getExpiration().before(new Date())) {
				return false;
			}
			return true; // Token is valid
		} catch (Exception e) {
			// Token validation failed
			return false;
		}
	}

	/**
	 * Extracts payload information from the JWT token.
	 *
	 * @param token The JWT token
	 * @return UserResponse containing user information extracted from the token
	 */
	public UserResponse extractPayloadFromToken(String token) {
		Claims claims = getAllClaimsFromToken(token);
		return UserResponse.builder()
				.userId(Long.parseLong(String.valueOf(claims.get("userId"))))
				.email(String.valueOf(claims.get("email")))
				.role(String.valueOf(claims.get("role")))
				.build();
	}
	/**
	 * Retrieves all claims from the JWT token.
	 *
	 * @param token The JWT token
	 * @return Claims containing all the claims extracted from the token
	 * @throws ApiException if the token is invalid
	 */
	public Claims getAllClaimsFromToken(String token) {
		try {
			return Jwts.parserBuilder().setSigningKey(secretKey)
					.build().parseClaimsJws(token).getBody();
		} catch (ExpiredJwtException | SignatureException e) {
			throw new ApiException("Invalid Token: " + e.getMessage());
		}
	}

	/**
	 * Retrieves authentication details from the JWT token.
	 *
	 * @param token The JWT token
	 * @return Authentication containing user authentication details extracted from the token
	 */
	public Authentication getAuthentication(String token) {
		UserResponse user = extractPayloadFromToken(token);
		UserDetails userDetails = new User(user.getUsername(), "", user.getAuthorities());
		return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
	}
}
