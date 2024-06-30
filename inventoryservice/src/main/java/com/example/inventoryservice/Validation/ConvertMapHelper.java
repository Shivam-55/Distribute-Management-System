package com.example.inventoryservice.Validation;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.Collections;
import java.util.Map;
/**
 * Helper class to convert JSON string to a map.
 */
public class ConvertMapHelper {
    /**
     * Converts a JSON string to a map.
     * @param jsonString the JSON string to convert
     * @return a map representation of the JSON string
     */
    public static Map<String, Object> convertStringToMap(String jsonString) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            return objectMapper.readValue(jsonString, new TypeReference<Map<String, Object>>() {});
        } catch (IOException e) {
            e.printStackTrace();
            return Collections.emptyMap();
        }
    }
}
