package com.company.dms.exception;

/**
 * Exception thrown when a user is not approved.
 */
public class UserNotApprovedException extends RuntimeException {

    /**
     * Constructs a new exception with the specified detail message.
     *
     * @param message the detail message.
     */
    public UserNotApprovedException(String message) {
        super(message);
    }
}
