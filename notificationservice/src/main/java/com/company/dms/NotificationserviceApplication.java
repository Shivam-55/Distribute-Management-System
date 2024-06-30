package com.company.dms;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

/**
 * Main class to start the Notification service application.
 */
@SpringBootApplication
@EnableDiscoveryClient
@EnableAspectJAutoProxy
public class NotificationserviceApplication {
	/**
	 * Main method to start the Notification Service application.
	 * @param args command-line arguments
	 */
	public static void main(String[] args) {
		SpringApplication.run(NotificationserviceApplication.class, args);
	}
}
