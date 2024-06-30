package com.example.inventoryservice.Validation;

import jakarta.servlet.http.HttpServletRequest;

import java.io.BufferedReader;
import java.io.IOException;
/**
 * Utility class for reading the request body.
 */
public class ReadRequestBody {
    /**
     * Reads the request body from the provided HttpServletRequest.
     * @param request the HttpServletRequest from which to read the body
     * @return a String representing the request body
     * @throws IOException if an I/O error occurs while reading the request body
     */
    public static String readRequestBody(HttpServletRequest request) throws IOException {
        StringBuilder requestBody = new StringBuilder();
        try (BufferedReader reader = request.getReader()) {
            String line;
            while ((line = reader.readLine()) != null) {
                requestBody.append(line);
            }
        }
        return requestBody.toString();
    }
}
