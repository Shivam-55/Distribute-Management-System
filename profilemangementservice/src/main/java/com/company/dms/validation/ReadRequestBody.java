package com.company.dms.validation;

import jakarta.servlet.http.HttpServletRequest;

import java.io.BufferedReader;
import java.io.IOException;
/**
 * Utility class for reading the request body from an HttpServletRequest.
 */
public class ReadRequestBody {
    /**
     * Reads the request body from the specified HttpServletRequest.
     *
     * @param request The HttpServletRequest object.
     * @return A string containing the request body content.
     * @throws IOException If an I/O error occurs while reading the request body.
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
