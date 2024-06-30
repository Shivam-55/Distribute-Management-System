package com.company.dms.exception;

/**
 * Exception class representing a situation where no user is present.
 */
public class NoSuchUserPresentException extends RuntimeException {

    /**
     * Constructs a NoSuchUserPresentException with the specified detail message.
     *
     * @param message The detail message explaining the exception.
     */
    public NoSuchUserPresentException(String message) {
        super(message);
    }
}
