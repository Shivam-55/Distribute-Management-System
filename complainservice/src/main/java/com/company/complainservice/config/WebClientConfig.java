package com.company.complainservice.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

/**
 * Configuration class for WebClient.
 */
@Configuration
public class WebClientConfig {

    /**
     * Bean definition for WebClient.Builder.
     *
     * @return WebClient.Builder instance.
     */
    @Bean
    public WebClient.Builder webClient() {
        return WebClient.builder();
    }
}
