package com.example.inventoryservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * The main entry point for the Inventory Service application.
 * This class bootstraps the Spring Boot application, enabling various features such as auto-configuration,
 * service discovery, scheduling, aspect-oriented programming (AOP), and defining beans.
 */
@SpringBootApplication
@EnableDiscoveryClient
@EnableScheduling
@EnableAspectJAutoProxy
public class InventoryServiceApplication {

	/**
	 * The main method to bootstrap the Inventory Service application.
	 * It launches the Spring Boot application.
	 *
	 * @param args The command-line arguments passed to the application.
	 */
	public static void main(String[] args) {
		SpringApplication.run(InventoryServiceApplication.class, args);
	}
}
