package com.company.dms.validation;

import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
/**
 * Utility class for handling invalid field exceptions.
 */
public class InvalidFieldException {
    /**
     * Sends a 404 error response with the specified message for invalid fields.
     *
     * @param response The HttpServletResponse object.
     * @param message  The message describing the invalid fields.
     * @throws IOException If an I/O error occurs while sending the error response.
     */
    public static void  allInvalidField(HttpServletResponse response, String message) throws IOException, IOException {
        response.setStatus(HttpServletResponse.SC_NOT_FOUND);
        response.sendError(404,message);
    }
}
