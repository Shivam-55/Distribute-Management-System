package com.company.dms.exception;

/**
 * Exception thrown when no active user is found.
 */
public class NoSuchUserActiveException extends RuntimeException {

    /**
     * Constructs a new exception with the specified detail message.
     *
     * @param message the detail message.
     */
    public NoSuchUserActiveException(String message) {
        super(message);
    }
}
