package com.company.dms;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

/**
 * The main entry point for the authentication service application.
 * This class initializes and starts the Spring Boot application.
 */
@SpringBootApplication
@EnableAspectJAutoProxy
public class AuthenticationServiceApplication {

	/**
	 * The main method that bootstraps the Spring Boot application.
	 * It starts the application by running the SpringApplication.
	 *
	 * @param args The command line arguments passed to the application.
	 */
	public static void main(String[] args) {
		SpringApplication.run(AuthenticationServiceApplication.class, args);
	}
}
