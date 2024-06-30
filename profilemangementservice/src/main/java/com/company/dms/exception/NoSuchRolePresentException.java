package com.company.dms.exception;

/**
 * Exception thrown when no role is found.
 */
public class NoSuchRolePresentException extends RuntimeException {

    /**
     * Constructs a new exception with the specified detail message.
     *
     * @param message the detail message.
     */
    public NoSuchRolePresentException(String message) {
        super(message);
    }
}
