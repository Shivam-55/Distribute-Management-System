package com.company.dms.exception;

/**
 * Exception thrown when a user does not have permission for a specific operation.
 */
public class NotPermissionException extends RuntimeException {

    /**
     * Constructs a new exception with the specified detail message.
     *
     * @param message the detail message.
     */
    public NotPermissionException(String message) {
        super(message);
    }
}
