package com.company.dms.exception;

/**
 * Exception thrown when a retailer already exists.
 */
public class RetailerAlreadyExist extends RuntimeException {

    /**
     * Constructs a new exception with the specified detail message.
     *
     * @param message the detail message.
     */
    public RetailerAlreadyExist(String message) {
        super(message);
    }
}
