package com.company.dms.exception;


/**
 * Exception thrown when no request is available.
 */
public class NoRequestAvailableException extends RuntimeException {

    /**
     * Constructs a new exception with the specified detail message.
     *
     * @param message the detail message.
     */
    public NoRequestAvailableException(String message) {
        super(message);
    }
}
