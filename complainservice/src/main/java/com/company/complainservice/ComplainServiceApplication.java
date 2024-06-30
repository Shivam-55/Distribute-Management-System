package com.company.complainservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.web.client.RestTemplate;

/**
 * Main application class for the complaint service.
 */
@SpringBootApplication
@EnableDiscoveryClient
@EnableAspectJAutoProxy
public class ComplainServiceApplication {

    /**
     * Main method to start the Spring Boot application.
     * @param args Command line arguments
     */
    public static void main(String[] args) {
        SpringApplication.run(ComplainServiceApplication.class, args);
    }

    /**
     * Bean for RestTemplate with load balancing enabled.
     * @return RestTemplate instance
     */
    @Bean
    @LoadBalanced
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }


}
