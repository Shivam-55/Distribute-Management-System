package com.company.dms.exception;

/**
 * Exception thrown when there is a mismatch between a pin code and a city.
 */
public class PincodeCityMismatchException extends RuntimeException {

    /**
     * Constructs a new exception with the specified detail message.
     *
     * @param message the detail message.
     */
    public PincodeCityMismatchException(String message) {
        super(message);
    }
}
