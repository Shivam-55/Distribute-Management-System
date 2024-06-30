package com.company.micro.security;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
/**
 * The main class for the API Gateway application.
 * This class initializes and starts the API Gateway service.
 */
@SpringBootApplication
@EnableAspectJAutoProxy
@OpenAPIDefinition(info = @Info(title = "API Gateway", version = "1.0", description = "Documentation API Gateway v1.0"))
public class ApiGatewayApplication {
    /**
     * The entry point for the API Gateway application.
     * Starts the API Gateway service.
     * @param args Command line arguments (not used)
     */
    public static void main(String[] args) {
        SpringApplication.run(ApiGatewayApplication.class, args);
    }

    /**
     * Configures the route locator for the API Gateway.
     * Defines routes to various microservices for Swagger API documentation.
     * @param builder The RouteLocatorBuilder used to build routes
     * @return The configured RouteLocator
     */
    @Bean
    public RouteLocator routeLocator(RouteLocatorBuilder builder) {
        return builder.routes()
                .route(r -> r.path("/complain-service/v3/api-docs")
                        .uri("lb://COMPLAIN-SERVICE")) // Route for complain-service Swagger API documentation
                .route(r -> r.path("/inventory-management-service/v3/api-docs")
                        .uri("lb://INVENTORY-MANAGEMENT-SERVICE")) // Route for inventory-service Swagger API documentation
                .route(r -> r.path("/profile-management-service/v3/api-docs")
                        .uri("lb://PROFILE-MANAGEMENT-SERVICE")) // Route for profile-service Swagger API documentation
                .route(r -> r.path("/request-management-service/v3/api-docs")
                        .uri("lb://REQUEST-MANAGEMENT-SERVICE")) // Route for request-service Swagger API documentation
                .build();
    }

}
