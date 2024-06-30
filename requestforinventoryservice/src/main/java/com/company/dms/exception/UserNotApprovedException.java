package com.company.dms.exception;

/**
 * Exception class representing a situation where a user is not approved.
 */
public class UserNotApprovedException extends RuntimeException {

    /**
     * Constructs a UserNotApprovedException with the specified detail message.
     *
     * @param message The detail message explaining the exception.
     */
    public UserNotApprovedException(String message) {
        super(message);
    }
}
