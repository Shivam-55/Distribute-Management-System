package com.company.dms.validation;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.Collections;
import java.util.Map;

/**
 * Helper class for converting JSON strings to maps.
 */
public class ConvertMapHelper {
    /**
     * Converts a JSON string to a map of string keys and object values.
     *
     * @param jsonString The JSON string to convert.
     * @return A map containing the key-value pairs parsed from the JSON string.
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
