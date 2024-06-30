package com.company.dms.exception;

/**
 * Exception class representing a situation where a connection is not found.
 */
public class ConnectionNotFoundException extends RuntimeException {

    /**
     * Constructs a ConnectionNotFoundException with the specified detail message.
     *
     * @param message The detail message explaining the exception.
     */
    public ConnectionNotFoundException(String message) {
        super(message);
    }
}

