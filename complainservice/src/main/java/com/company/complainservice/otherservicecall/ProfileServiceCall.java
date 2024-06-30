package com.company.complainservice.otherservicecall;

import com.company.complainservice.dto.DistributorAndRetailerDto;
import com.company.complainservice.dto.UserDto;
import com.company.complainservice.exception.NoConnectionFoundException;
import com.company.complainservice.exception.NoSuchUserPresentException;
import com.company.complainservice.exception.ServiceDownException;
import com.company.complainservice.utils.logger.LoggableClass;
import com.company.complainservice.utils.logger.LoggableMethod;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.ClientResponse;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import reactor.core.publisher.Mono;

import java.util.logging.Logger;

/**
 * Service call to Profile service
 */
@LoggableClass
@Component
public class ProfileServiceCall {
    private static final Logger logger = Logger.getLogger(ProfileServiceCall.class.getName());
    private final WebClient.Builder webClient;
    @Autowired
    public ProfileServiceCall(WebClient.Builder webClient){
        this.webClient = webClient;
    }

    /**
     * Handles error responses from the profile service.
     *
     * @param response  the client response
     * @param operation the operation for which the response is handled
     * @return a Mono representing the error response
     */
    private Mono<? extends Throwable> handleErrorResponse(ClientResponse response, String operation) {
        if (response.statusCode().equals(HttpStatus.NOT_FOUND)) {
            if ("getUserInfo".equals(operation)) {
                return Mono.error(new NoSuchUserPresentException("USER NOT FOUND"));
            } else if ("retailerConnection".equals(operation)) {
                return Mono.error(new NoConnectionFoundException("No retailer is connected with distributor"));
            }
        }
        // For other errors, handle them generically
        return response.bodyToMono(String.class)
                .flatMap(body -> Mono.error(new RuntimeException("Error response: " + response.statusCode() + ", " + body)));
    }

    /**
     * Retrieves retailer connection information from the profile service.
     *
     * @param retailerId the ID of the retailer
     * @return the distributor and retailer information
     */
    @LoggableMethod
    public DistributorAndRetailerDto retailerConnection(Long retailerId){
        try{
            DistributorAndRetailerDto response = webClient.build().get()
                    .uri("http://localhost:8082/user/connection/{retailerId}", retailerId)
                    .retrieve()
                    .onStatus(HttpStatusCode::isError, res -> handleErrorResponse(res, "retailerConnection"))
                    .bodyToMono(String.class)
                    .flatMap(json -> {
                        ObjectMapper mapper = new ObjectMapper();
                        try {
                            // Deserialize JSON to UserDto
                            return Mono.just(mapper.readValue(json, DistributorAndRetailerDto.class));
                        } catch (JsonProcessingException e) {
                            // Handle deserialization error
                            return Mono.error(new RuntimeException("Error deserializing response to UserDto: " + e.getMessage(), e));
                        }
                    })
                    .block();
            if (response != null) {
                return response;
            } else {
                throw new NoConnectionFoundException("No retailer is connected with distributor");
            }
        }catch (WebClientResponseException ex) {
            logger.warning("API Error: {} {}"+ ex.getRawStatusCode()+ ex.getStatusText());
        }catch (NoConnectionFoundException noConnectionFoundException){
            throw new NoConnectionFoundException(noConnectionFoundException.getMessage());
        }catch (RuntimeException ex) {
            throw new ServiceDownException("Profile Service down exception : "+ex.getMessage());
        }
        return null;
    }

    /**
     * Retrieves user information from the profile service using the user ID.
     *
     * @param userId the ID of the user
     * @return the user information
     */
    @LoggableMethod
    public UserDto getUserWithId(Long userId){
        try{
            UserDto response = webClient.build().get()
                    .uri("http://localhost:8082/user/info/{userId}", userId)
                    .retrieve()
                    .onStatus(HttpStatusCode::isError, res -> handleErrorResponse(res, "getUserInfo"))
                    .bodyToMono(UserDto.class)
                    .block();
            if (response != null) {
                return response;
            } else {
                throw new NoSuchUserPresentException("No such user is found");
            }
        }catch (WebClientResponseException ex) {
            logger.warning("API Error: {} {}"+ ex.getRawStatusCode()+ ex.getStatusText());
        }catch (NoSuchUserPresentException userNotFound){
            throw new NoSuchUserPresentException(userNotFound.getMessage());
        }catch (RuntimeException ex) {
            throw new ServiceDownException("Profile service down exception : "+ex.getMessage());
        }
        return null;
    }
}
