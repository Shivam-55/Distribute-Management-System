package com.company.dms.exception;

/**
 * Exception class representing a situation where no inventory is present.
 */
public class NoSuchInventoryPresentException extends RuntimeException {

    /**
     * Constructs a NoSuchInventoryPresentException with the specified detail message.
     *
     * @param message The detail message explaining the exception.
     */
    public NoSuchInventoryPresentException(String message) {
        super(message);
    }
}
