package com.company.dms.exception;

/**
 * Exception class representing a situation where no request is available.
 */
public class NoSuchRequestAvailableException extends RuntimeException {

    /**
     * Constructs a NoSuchRequestAvailableException with the specified detail message.
     *
     * @param message The detail message explaining the exception.
     */
    public NoSuchRequestAvailableException(String message) {
        super(message);
    }
}
