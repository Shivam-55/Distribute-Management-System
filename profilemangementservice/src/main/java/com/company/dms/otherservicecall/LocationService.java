package com.company.dms.otherservicecall;

import com.company.dms.exception.MessageParsingException;
import com.company.dms.exception.ServiceDownException;
import com.company.dms.utils.ExcludeFromCodeCoverage;
import com.company.dms.utils.logger.LoggableClass;
import com.company.dms.utils.logger.LoggableMethod;
import com.fasterxml.jackson.core.JsonProcessingException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.ArrayList;
import java.util.List;
/**
 * Service class for interacting with location-related APIs.
 */
@ExcludeFromCodeCoverage
@LoggableClass
@Component
public class LocationService {
    private final WebClient webClient;
    private final ObjectMapper objectMapper;
    @Autowired
    public LocationService(WebClient.Builder webClientBuilder, ObjectMapper objectMapper) {
        this.webClient = webClientBuilder.baseUrl("https://api.postalpincode.in").build();
        this.objectMapper = objectMapper;
    }

    /**
     * Retrieves city information based on a given pincode.
     *
     * @param pincode the pincode to search for.
     * @return a Mono containing the city information.
     */
    @LoggableMethod
    public Mono<String> getCityByPincode(String pincode) {
        try {
            return webClient.get()
                    .uri("/pincode/{pincode}", pincode)
                    .retrieve()
                    .bodyToMono(String.class)
                    .map(responseBody -> {
                        try {
                            String postOffice = "PostOffice" ;
                            JsonNode jsonNode = objectMapper.readTree(responseBody);
                            if (jsonNode != null && jsonNode.isArray() && jsonNode.size() > 0) {
                                JsonNode firstNode = jsonNode.get(0);
                                if (firstNode != null && firstNode.has(postOffice) && firstNode.get(postOffice).isArray()) {
                                    JsonNode postOfficeNode = firstNode.get(postOffice).get(0);
                                    if (postOfficeNode != null && postOfficeNode.has("District") && postOfficeNode.has("State")) {
                                        String city = postOfficeNode.get("District").asText();
                                        String state = postOfficeNode.get("State").asText();
                                        return city + "," + state;
                                    }
                                }
                            }
                        } catch (JsonProcessingException e) {
                            throw new MessageParsingException(e.getMessage());
                        }
                        return "City information not found";
                    });
        } catch (ServiceDownException ex) {
            throw new ServiceDownException("Location service is down");
        }
    }

    /**
     * Retrieves pincode information based on a given city.
     *
     * @param city the city to search for.
     * @return a Mono containing a list of pincodes.
     */
    @LoggableMethod
    public Mono<List<String>> getPincodesByCity(String city) {
        try{
            return webClient.get()
                    .uri("/postoffice/{city}", city)
                    .retrieve()
                    .bodyToMono(String.class)
                    .map(responseBody -> {
                        JsonNode jsonNode = null;
                        try {
                            jsonNode = objectMapper.readTree(responseBody);
                        } catch (JsonProcessingException e) {
                            throw new MessageParsingException(e.getMessage());
                        }
                        List<String> pincodes = new ArrayList<>();
                        JsonNode postOffices = jsonNode.get(0).get("PostOffice");
                        if (postOffices != null) {
                            for (JsonNode postOffice : postOffices) {
                                pincodes.add(postOffice.get("Pincode").asText());
                            }
                        }
                        return pincodes;
                    });
        }catch (ServiceDownException ex){
            throw new ServiceDownException("Location service is down");
        }
    }
}
