package com.company.micro.security.config;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.ReactiveSecurityContextHolder;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextImpl;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;

import com.company.micro.security.security.JwtTokenHelper;
import com.company.micro.security.util.DecodeText;
import com.company.micro.security.util.EncodeText;
import com.company.micro.security.util.UserResponse;

import reactor.core.publisher.Mono;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Filter class for JWT authentication.
 * This class intercepts incoming requests, extracts JWT token, validates it, and sets the user details in the SecurityContext.
 */
@Component
public class JwtAuthenticationFilter implements WebFilter {

	private static final Logger logger = LoggerFactory.getLogger(JwtAuthenticationFilter.class);
	private JwtTokenHelper jwtTokenHelper;
	@Autowired
	public JwtAuthenticationFilter(JwtTokenHelper jwtTokenHelper){
		this.jwtTokenHelper =jwtTokenHelper;
	}

	private static final String AUTHORIZATION = "Authorization";

	/**
	 * Filters the incoming server web exchange.
	 * Extracts JWT token from the request, decrypts it, validates it, and sets user details in the SecurityContext.
	 * @param exchange The ServerWebExchange representing the incoming request and outgoing response
	 * @param chain The WebFilterChain to proceed with the request processing
	 * @return A Mono<Void> representing the completion of request processing
	 */
	@Override
	public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
		String token = extractTokenFromRequest(exchange);
		if (token != null) {
			token = DecodeText.decryptText(token);
			UserDetails user = jwtTokenHelper.extractPayloadFromToken(token);
			logger.debug("User: {}", user);

			List<SimpleGrantedAuthority> authorities = user.getAuthorities().stream()
					.map(role -> new SimpleGrantedAuthority("ROLE_" + role)).toList();

			logger.debug("authorities: {}", authorities);

			UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
					user, null, authorities);
			logger.debug("usernamePasswordAuthenticationToken: {}", authenticationToken);

			SecurityContext context = new SecurityContextImpl(authenticationToken);

			logger.debug("Context: {}", context.getAuthentication());

			UserResponse userResponse= jwtTokenHelper.extractPayloadFromToken(token);

			exchange.getRequest().mutate().header("userId", EncodeText.encrytString(String.valueOf(userResponse.getUserId())));
			exchange.getRequest().mutate().header("userEmail", EncodeText.encrytString(userResponse.getEmail()));
			exchange.getRequest().mutate().header("userRole", EncodeText.encrytString(userResponse.getRole()));
			logger.debug("User Role: {}", userResponse.getRole());

			return chain.filter(exchange).contextWrite(
					ReactiveSecurityContextHolder.withSecurityContext(Mono.just(context)));

		}
		logger.debug("This is outside!!");

		return chain.filter(exchange);
	}

	/**
	 * Extracts JWT token from the request.
	 * @param exchange The ServerWebExchange representing the incoming request and outgoing response
	 * @return The extracted JWT token string, or null if not found
	 */
	private String extractTokenFromRequest(ServerWebExchange exchange) {
		String authorizationHeader = exchange.getRequest().getHeaders().getFirst(AUTHORIZATION);
		if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
			return authorizationHeader.substring(7);
		}
		return null;
	}
}
