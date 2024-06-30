package com.company.dms.validation;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.Collections;
import java.util.Map;
/**
 * Helper class to convert a JSON string to a Map<String, Object>.
 */
public class ConvertMapHelper {
    /**
     * Converts a JSON string to a Map<String, Object>.
     *
     * @param jsonString The JSON string to be converted.
     * @return A Map<String, Object> representing the JSON data.
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
