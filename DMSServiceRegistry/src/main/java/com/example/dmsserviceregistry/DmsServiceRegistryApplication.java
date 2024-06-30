package com.example.dmsserviceregistry;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

/**
 * The main class for starting the DMS Service Registry application.
 */
@SpringBootApplication
@EnableEurekaServer
public class DmsServiceRegistryApplication {

    /**
     * The main method to start the DMS Service Registry application.
     * @param args The command line arguments.
     */
    public static void main(String[] args) {
        SpringApplication.run(DmsServiceRegistryApplication.class, args);
    }

}
