package com.company.dms.exception;

/**
 * Exception class representing a situation where a request is already present.
 */
public class AlreadyRequestPresentException extends RuntimeException {

    /**
     * Constructs an AlreadyRequestPresentException with the specified detail message.
     *
     * @param message The detail message explaining the exception.
     */
    public AlreadyRequestPresentException(String message) {
        super(message);
    }
}
