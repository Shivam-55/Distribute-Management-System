package com.company.dms.exception;

/**
 * Exception thrown when a user is not active anymore.
 */
public class NotActiveAnyMoreException extends RuntimeException {

    /**
     * Constructs a new exception with the specified detail message.
     *
     * @param message the detail message.
     */
    public NotActiveAnyMoreException(String message) {
        super(message);
    }
}
