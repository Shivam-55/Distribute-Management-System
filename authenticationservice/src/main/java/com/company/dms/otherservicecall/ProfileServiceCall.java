package com.company.dms.otherservicecall;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.ClientResponse;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;

import com.company.dms.dto.UserDto;
import com.company.dms.exception.NoSuchUserPresentException;
import com.company.dms.exception.ServiceDownException;
import com.company.dms.utils.logger.LoggableClass;
import com.company.dms.utils.logger.LoggableMethod;

import reactor.core.publisher.Mono;


/**
 * Service class responsible for making calls to the profile service.
 */
@LoggableClass
@Component
public class ProfileServiceCall {
    private final WebClient.Builder webClient;
    private static final Logger logger = LoggerFactory.getLogger(ProfileServiceCall.class.getName());

    /**
     * Constructor to inject WebClient.Builder dependency.
     *
     * @param webClientBuilder WebClient.Builder instance
     */
    @Autowired
    public ProfileServiceCall(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder;
    }

    /**
     * Handles error response from the profile service.
     *
     * @param response ClientResponse instance
     * @return Mono containing the error response
     */
    private Mono<? extends Throwable> handleErrorResponse(ClientResponse response) {
        if (response.statusCode().equals(HttpStatus.NOT_FOUND)) {
            return Mono.error(new NoSuchUserPresentException("USER NOT FOUND"));
        } else {
            return response.bodyToMono(String.class)
                    .flatMap(body -> Mono.error(new RuntimeException("Error response: " + response.statusCode() + ", " + body)));
        }
    }

    /**
     * Retrieves user information by email from the profile service.
     *
     * @param email Email address of the user
     * @return UserDto object representing the user information
     * @throws WebClientResponseException if an error occurs while making the request to the profile service
     * @throws NoSuchUserPresentException if the user is not found
     * @throws ServiceDownException       if the profile service is down
     */
    @LoggableMethod
    public UserDto getUserWithMail(String email) {
        try {
            return webClient.build().get()
                    .uri("http://localhost:8082/user/info/mail/{email}", email)
                    .retrieve()
                    .onStatus(HttpStatusCode::isError, this::handleErrorResponse)
                    .bodyToMono(UserDto.class)
                    .switchIfEmpty(Mono.error(new NoSuchUserPresentException("No such user is present")))
                    .block(); // Blocking operation
        } catch (WebClientResponseException ex) {
            throw ex;
        } catch (NoSuchUserPresentException userNotFound) {
            throw new NoSuchUserPresentException(userNotFound.getMessage());
        } catch (RuntimeException ex) {
            throw new ServiceDownException("Profile service Down : "+ex.getMessage());
        }
    }
}
