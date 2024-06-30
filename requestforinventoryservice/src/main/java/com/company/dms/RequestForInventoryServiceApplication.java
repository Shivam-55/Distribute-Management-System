package com.company.dms;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

/**
 * Main application class for the Request for Inventory Service.
 */
@SpringBootApplication
@EnableDiscoveryClient
@EnableAspectJAutoProxy
public class RequestForInventoryServiceApplication {
	public static void main(String[] args) {
		SpringApplication.run(RequestForInventoryServiceApplication.class, args);
	}
}
