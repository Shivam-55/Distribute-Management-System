package com.example.inventoryservice.otherservicecall;

import com.example.inventoryservice.exception.NoSuchUserPresentException;
import com.example.inventoryservice.exception.ServiceDownException;
import com.example.inventoryservice.requestDto.UserDto;
import com.example.inventoryservice.utility.logger.LoggableClass;
import com.example.inventoryservice.utility.logger.LoggableMethod;
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

import java.util.List;

/**
 * Service class responsible for managing user profiles.
 */
@LoggableClass
@Component
public class ProfileManagementService {
    private static final Logger LOGGER = LoggerFactory.getLogger(ProfileManagementService.class.getName());
    private final WebClient.Builder webClient;
    @Autowired
    public ProfileManagementService(WebClient.Builder webClient){
        this.webClient = webClient;
    }

    /**
     * Handles error response from the external service.
     * @param response the client response from the external service
     * @return a Mono representing the error response
     */
    private Mono<? extends Throwable> handleErrorResponse(ClientResponse response) {
        if (response.statusCode().equals(HttpStatus.NOT_FOUND)) {
            return Mono.error(new NoSuchUserPresentException("USERS NOT FOUND"));
        } else {
            return response.bodyToMono(String.class)
                    .flatMap(body -> Mono.error(new RuntimeException("Error response: " + response.statusCode() + ", " + body)));
        }
    }

    /**
     * Retrieves all active and approved users.
     * @return a list of UserDto representing all active and approved users
     * @throws NoSuchUserPresentException if no user is found
     * @throws ServiceDownException if the profile management service is down
     */
    @LoggableMethod
    public List<UserDto> getAllActiveApprovedUser(){
        try{
            List<UserDto> response = webClient.build().get()
                    .uri("http://localhost:8082/user/active")
                    .retrieve()
                    .onStatus(HttpStatusCode::isError, this::handleErrorResponse)
                    .bodyToFlux(UserDto.class)
                    .collectList()
                    .block();

            if (response != null) {
                LOGGER.info("response {}",response);
                return response;
            } else {
                throw new NoSuchUserPresentException("No such user is present");
            }
        }catch (WebClientResponseException ex) {
            LOGGER.warn("API Error: {} {}", ex.getRawStatusCode(), ex.getStatusText());
        }
        catch (NoSuchUserPresentException userNotFound){
            throw new NoSuchUserPresentException(userNotFound.getMessage());
        }
        catch (RuntimeException ex) {
            throw new ServiceDownException(ex.getMessage());
        }
        return null;
    }

    /**
     * Retrieves user information by user ID.
     * @param userId the ID of the user to retrieve
     * @return a UserDto representing the user with the specified ID
     * @throws NoSuchUserPresentException if no user is found
     * @throws ServiceDownException if the profile management service is down
     */
    @LoggableMethod
    public UserDto getUserWithId(Long userId){
        try{
            UserDto response = webClient.build().get()
                    .uri("http://localhost:8082/user/info/{userId}", userId)
                    .retrieve()
                    .onStatus(HttpStatusCode::isError, this::handleErrorResponse)
                    .bodyToMono(UserDto.class)
                    .block();
            if (response != null) {
                LOGGER.info("response {}",response);
                return response;
            } else {
                throw new NoSuchUserPresentException("No such user is found");
            }
        }catch (WebClientResponseException ex) {
            LOGGER.warn("API Error: {} {}", ex.getRawStatusCode(), ex.getStatusText());
        }catch (NoSuchUserPresentException userNotFound){
            throw new NoSuchUserPresentException(userNotFound.getMessage());
        }catch (RuntimeException ex) {
            throw new ServiceDownException("Profile service down exception :"+ex.getMessage());
        }
        return null;
    }
}
