package com.company.dms.validation;

import jakarta.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.IOException;

/**
 * Read request body validation
 */
public class ReadRequestBody {

    /**
     * Reads the request body from the provided HttpServletRequest.
     *
     * @param request The HttpServletRequest from which to read the body.
     * @return A string containing the contents of the request body.
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
