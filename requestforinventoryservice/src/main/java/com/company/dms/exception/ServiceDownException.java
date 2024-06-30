package com.company.dms.exception;

/**
 * Exception class representing a situation where a service is down.
 */
public class ServiceDownException extends RuntimeException {

    /**
     * Constructs a ServiceDownException with the specified detail message.
     *
     * @param message The detail message explaining the exception.
     */
    public ServiceDownException(String message) {
        super(message);
    }
}
