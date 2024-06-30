package com.company.dms.validation;

import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

/**
 * Invalid field exception class validation
 */
public class InvalidFieldException {
    /**
     * Handles invalid field exceptions by setting the response status and sending an error message.
     *
     * @param response The HTTP servlet response.
     * @param message  The error message to be sent.
     * @throws IOException If an I/O error occurs while sending the error response.
     */
    public static void  allInvalidField(HttpServletResponse response, String message) throws IOException, IOException {
        response.setStatus(HttpServletResponse.SC_NOT_FOUND);
        response.sendError(404,message);
    }
}
