package com.company.dms.exception;

/**
 * Exception thrown when no connection is found.
 */
public class NoSuchConnectionFoundException extends RuntimeException {

    /**
     * Constructs a new exception with the specified detail message.
     *
     * @param message the detail message.
     */
    public NoSuchConnectionFoundException(String message) {
        super(message);
    }
}
