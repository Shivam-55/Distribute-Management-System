package com.company.dms.exception;

/**
 * Exception thrown when no user is found.
 */
public class NoSuchUserPresentException extends RuntimeException {

    /**
     * Constructs a new exception with the specified detail message.
     *
     * @param message the detail message.
     */
    public NoSuchUserPresentException(String message) {
        super(message);
    }
}
