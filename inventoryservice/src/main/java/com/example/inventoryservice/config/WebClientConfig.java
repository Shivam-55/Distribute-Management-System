package com.example.inventoryservice.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

/**
 * Configuration class for WebClient bean.
 */
@Configuration
public class WebClientConfig {

    /**
     * Provides a WebClient.Builder bean.
     * @return WebClient.Builder object.
     */
    @Bean
    public WebClient.Builder webClient(){
        return WebClient.builder();
    }
}
