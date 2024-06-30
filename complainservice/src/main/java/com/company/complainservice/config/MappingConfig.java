package com.company.complainservice.config;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Configuration class for mapping configurations.
 */
@Configuration
public class MappingConfig {

    /**
     * Bean definition for ModelMapper.
     *
     * @return ModelMapper instance
     */
    @Bean
    public ModelMapper mapper(){
        return new ModelMapper();
    }
}
