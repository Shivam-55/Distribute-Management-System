package com.company.dms.config;

import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.web.reactive.function.client.WebClient;

/**
 * Configuration class for WebClient.
 */
public class WebClientConfig {

    /**
     * Bean definition for WebClient.Builder with load balancing.
     *
     * @return WebClient.Builder instance
     */
    @Bean
    @LoadBalanced
    WebClient.Builder webClientBuilder() {
        return WebClient.builder();
    }
}
