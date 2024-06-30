package com.company.dms.exception;

/**
 * Exception thrown when an invalid role is encountered.
 */
public class InvalidRoleException extends RuntimeException {

    /**
     * Constructs a new invalid role exception with the specified detail message.
     *
     * @param message the detail message.
     */
    public InvalidRoleException(String message) {
        super(message);
    }
}
