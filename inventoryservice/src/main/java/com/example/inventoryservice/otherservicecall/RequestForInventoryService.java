package com.example.inventoryservice.otherservicecall;

import com.example.inventoryservice.exception.FailedStatusUpdateException;
import com.example.inventoryservice.exception.NoSuchRequestException;
import com.example.inventoryservice.exception.NoSuchUserPresentException;
import com.example.inventoryservice.exception.ServiceDownException;
import com.example.inventoryservice.requestDto.InventoryRequestDto;
import com.example.inventoryservice.responseDto.RequestForInventoryResDto;
import com.example.inventoryservice.utility.logger.LoggableClass;
import com.example.inventoryservice.utility.logger.LoggableMethod;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.client.ClientResponse;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import reactor.core.publisher.Mono;


/**
 * Service class responsible for managing requests for inventory.
 */
@LoggableClass
@Component
public class RequestForInventoryService {
    private static final Logger LOGGER = LoggerFactory.getLogger(RequestForInventoryService.class.getName());
    private final WebClient.Builder webClient;
    @Autowired
    public RequestForInventoryService(WebClient.Builder webClient){
        this.webClient = webClient;
    }

    /**
     * Handles error response from the external service.
     * @param response the client response from the external service
     * @return a Mono representing the error response
     */
    private Mono<? extends Throwable> handleErrorResponse(ClientResponse response) {
        if (response.statusCode().equals(HttpStatus.BAD_REQUEST)) {
            return Mono.error(new FailedStatusUpdateException("Failed to update status"));
        } else {
            return response.bodyToMono(String.class)
                    .flatMap(body -> Mono.error(new RuntimeException("Error response: " + response.statusCode() + ", " + body)));
        }
    }

    /**
     * Updates the status in the request for inventory.
     * @param request the request for inventory
     * @return a Mono containing the updated request for inventory
     * @throws FailedStatusUpdateException if the status update fails
     * @throws ServiceDownException if the request for inventory service is down
     */
    @LoggableMethod
    @Transactional
    public Mono<RequestForInventoryResDto> updateStatusInRequest(RequestForInventoryResDto request) {
        try{
            return webClient.build().put()
                    .uri("http://localhost:8084/inventory/request/statusUpdate")
                    .bodyValue(request)
                    .retrieve()
                    .onStatus(HttpStatusCode::isError, res -> Mono.error(new RuntimeException("Error response: " + res.statusCode())))
                    .bodyToMono(String.class)
                    .flatMap(json -> {
                        ObjectMapper mapper = new ObjectMapper();
                        try {
                            RequestForInventoryResDto response = mapper.readValue(json, RequestForInventoryResDto.class);
                            return Mono.just(response);
                        } catch (JsonProcessingException e) {
                            return Mono.error(new RuntimeException("Error deserializing response to RequestForInventoryResDto: " + e.getMessage(), e));
                        }
                    })
                    .doOnError(error ->
                        LOGGER.warn("An error occurred: {}" , error.getMessage())
                    );
        }catch (WebClientResponseException ex) {
            LOGGER.warn("API Error: {} {}", ex.getRawStatusCode(), ex.getStatusText());
        }
        catch (FailedStatusUpdateException ex){
            throw new FailedStatusUpdateException(ex.getMessage());
        }
        catch (RuntimeException ex) {
            throw new ServiceDownException("Request for inventory service down exception : "+ex.getMessage());
        }
        return null;
    }

    /**
     * Retrieves the inventory request raised by a user for a specific inventory item.
     * @param userId the ID of the user
     * @param inventoryId the ID of the inventory item
     * @return the inventory request DTO
     * @throws NoSuchRequestException if no request is found
     * @throws NoSuchUserPresentException if no user is found
     * @throws ServiceDownException if the inventory request service is down
     */
    @LoggableMethod
    public InventoryRequestDto getUserRaisedPendingRequest(Long userId, Long inventoryId){
        try{
            InventoryRequestDto response = webClient.build().get()
                    .uri("http://localhost:8084/inventory/request/pending/{userId}/{inventoryId}", userId, inventoryId)
                    .retrieve()
                    .onStatus(HttpStatusCode::isError, this::handleErrorResponse)
                    .bodyToMono(InventoryRequestDto.class)
                    .block();
            if (response != null) {
                LOGGER.info("response {}",response);
                return response;
            } else {
                throw new NoSuchRequestException("No such request is found");
            }
        }catch (WebClientResponseException ex) {
            LOGGER.warn("API Error: {} {}", ex.getRawStatusCode(), ex.getStatusText());
        }catch (NoSuchUserPresentException userNotFound){
            throw new NoSuchUserPresentException(userNotFound.getMessage());
        }catch (RuntimeException ex) {
            throw new ServiceDownException("Inventory Request service down exception :"+ex.getMessage());
        }
        return null;
    }
}
