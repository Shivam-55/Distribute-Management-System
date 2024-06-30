package com.example.inventoryservice.Validation;

import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
/**
 * Exception handler class for handling invalid field exceptions.
 */
public class InvalidFieldException {
    /**
     * Sends a 404 error response with the provided message to indicate invalid fields.
     * @param response the HTTP servlet response
     * @param message the message indicating the invalid fields
     * @throws IOException if an I/O error occurs while sending the response
     */
    public static void  allInvalidField(HttpServletResponse response, String message) throws IOException, IOException {
        response.setStatus(HttpServletResponse.SC_NOT_FOUND);
        response.sendError(404,message);
    }
}
