package com.company.dms.otherservicecalls;

import com.company.dms.dto.inventorydto.InventoryDto;
import com.company.dms.exception.MessageParsingException;
import com.company.dms.exception.NoSuchInventoryPresentException;
import com.company.dms.exception.ServiceDownException;
import com.company.dms.utils.logger.LoggableClass;
import com.company.dms.utils.logger.LoggableMethod;
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
 * Service class for making calls to the Inventory service.
 */
@LoggableClass
@Component
public class InventoryServiceCall {
    private final Logger logger = Logger.getLogger(InventoryServiceCall.class.getName());
    private final WebClient.Builder webClient;
    @Autowired
    public InventoryServiceCall(WebClient.Builder webClient){
        this.webClient = webClient;
    }
    /**
     * handle error response
     * @param response
     * @return
     */
    private Mono<? extends Throwable> handleErrorResponse(ClientResponse response) {
        if (response.statusCode().equals(HttpStatus.NOT_FOUND)) {
            return Mono.error(new NoSuchInventoryPresentException("No such inventory is present"));
        } else {
            return response.bodyToMono(String.class)
                    .flatMap(body -> Mono.error(new RuntimeException("Error response: " + response.statusCode() + ", " + body)));
        }
    }
    /**
     * Retrieves an inventory item by its name from the Inventory service.
     *
     * @param inventoryName The name of the inventory item to retrieve.
     * @return The InventoryDto object representing the retrieved inventory item.
     */
    @LoggableMethod
    public InventoryDto getInventoryByName(String inventoryName) {
        try {
            String json = webClient.build().get()
                    .uri("http://localhost:8083/inventory/{inventoryName}", inventoryName)
                    .retrieve()
                    .onStatus(HttpStatusCode::isError, this::handleErrorResponse)
                    .bodyToMono(String.class)
                    .block();
            if (json != null && !json.isEmpty()) {
                ObjectMapper mapper = new ObjectMapper();
                return mapper.readValue(json, InventoryDto.class);
            } else {
                throw new NoSuchInventoryPresentException("No such inventory is present");
            }
        }catch (WebClientResponseException ex) {
            logger.warning("API Error: {} {}"+ ex.getRawStatusCode()+ ex.getStatusText());
        }catch (NoSuchInventoryPresentException userNotFound){
            throw new NoSuchInventoryPresentException(userNotFound.getMessage());
        }catch (JsonProcessingException e) {
            throw new MessageParsingException(e.getLocalizedMessage());
        }catch (RuntimeException ex) {
            throw new ServiceDownException("Inventory service down exception :"+ex.getMessage());
        }
        return null;
    }
}
