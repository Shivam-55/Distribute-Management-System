package com.company.dms.otherservicecalls;

import com.company.dms.dto.DistributorAndRetailerDto;
import com.company.dms.dto.UserDto;
import com.company.dms.exception.ConnectionNotFoundException;
import com.company.dms.exception.NoSuchUserPresentException;
import com.company.dms.exception.ServiceDownException;
import com.company.dms.utils.logger.LoggableClass;
import com.company.dms.utils.logger.LoggableMethod;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.ClientResponse;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import reactor.core.publisher.Mono;
/**
 * Service class for making calls related to user profiles.
 */
@LoggableClass
@Component
public class ProfileServiceCall {
    private static final Logger logger = LoggerFactory.getLogger(ProfileServiceCall.class.getName());
    private final WebClient.Builder webClient;
    /**
     * Constructor for ProfileServiceCall.
     *
     * @param webClientBuilder The WebClient builder to be injected.
     */
    @Autowired
    public ProfileServiceCall(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder;
    }

    /**
     * handle error response
     * @param response
     * @param operation
     * @return
     */
    private Mono<? extends Throwable> handleErrorResponse(ClientResponse response, String operation) {
        if (response.statusCode().equals(HttpStatus.NOT_FOUND)) {
            if ("getUserInfo".equals(operation)) {
                return Mono.error(new NoSuchUserPresentException("USER NOT FOUND"));
            } else if ("retailerConnection".equals(operation)) {
                return Mono.error(new ConnectionNotFoundException("No retailer is connected with distributor"));
            }
        }
        // For other errors, handle them generically
        return response.bodyToMono(String.class)
                .flatMap(body -> Mono.error(new RuntimeException("Error response: " + response.statusCode() + ", " + body)));
    }
    /**
     * Retrieves user information by user ID.
     *
     * @param userId The ID of the user to retrieve information for.
     * @return The UserDto object representing the user information.
     */
    @LoggableMethod
    public UserDto getUserWithId(Long userId) {
        try{
            UserDto response = webClient.build().get()
                    .uri("http://localhost:8082/user/info/{userId}", userId)
                    .retrieve()
                    .onStatus(HttpStatusCode::isError, res -> handleErrorResponse(res, "getUserInfo"))
                    .bodyToMono(String.class)
                    .flatMap(json -> {
                        ObjectMapper mapper = new ObjectMapper();
                        try {
                            // Deserialize JSON to UserDto
                            return Mono.just(mapper.readValue(json, UserDto.class));
                        } catch (JsonProcessingException e) {
                            // Handle deserialization error
                            return Mono.error(new RuntimeException("Error deserializing response to UserDto: " + e.getMessage(), e));
                        }
                    })
                    .block();
            if (response != null) {
                logger.info("{}",response);
                return response;
            } else {
                throw new NoSuchUserPresentException("No such user is found");
            }
        }catch (WebClientResponseException ex) {
            logger.warn("API Error: {} {}", ex.getRawStatusCode(), ex.getStatusText());
        }catch (NoSuchUserPresentException userNotFound){
            throw new NoSuchUserPresentException(userNotFound.getMessage());
        }catch (RuntimeException ex) {
            throw new ServiceDownException("Profile service down exception : "+ex.getMessage());
        }
        return null;
    }
    /**
     * Retrieves the connection information between a retailer and a distributor.
     *
     * @param retailerId The ID of the retailer to retrieve connection information for.
     * @return The DistributorAndRetailerDto object representing the connection information.
     */
    @LoggableMethod
    public DistributorAndRetailerDto retailerConnection(Long retailerId){
        try {
            DistributorAndRetailerDto response = webClient.build().get()
                    .uri("http://localhost:8082/user/connection/{retailerId}", retailerId)
                    .retrieve()
                    .onStatus(HttpStatusCode::isError, res -> handleErrorResponse(res, "retailerConnection"))
                    .bodyToMono(String.class)
                    .flatMap(json -> {
                        ObjectMapper mapper = new ObjectMapper();
                        try {
                            return Mono.just(mapper.readValue(json, DistributorAndRetailerDto.class));
                        } catch (JsonProcessingException e) {
                            return Mono.error(new RuntimeException("Error deserializing response to UserDto: " + e.getMessage(), e));
                        }
                    })
                    .block();
            if (response != null) {
                logger.info("{}",response);
                return response;
            } else {
                throw new ConnectionNotFoundException("No retailer is connected with distributor");
            }
        }catch (WebClientResponseException ex) {
            logger.warn("API Error: {} {}", ex.getRawStatusCode(), ex.getStatusText());
        }catch (ConnectionNotFoundException noConnectionFoundException){
            throw new ConnectionNotFoundException(noConnectionFoundException.getMessage());
        }catch (RuntimeException ex) {
            throw new ServiceDownException("Profile service down exception : "+ex.getMessage());
        }
        return null;
    }
}
