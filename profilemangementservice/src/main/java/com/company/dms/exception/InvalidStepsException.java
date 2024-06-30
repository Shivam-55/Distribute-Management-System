package com.company.dms.exception;


/**
 * Exception thrown when invalid steps are encountered.
 */
public class InvalidStepsException extends RuntimeException {

    /**
     * Constructs a new invalid steps exception with the specified detail message.
     *
     * @param message the detail message.
     */
    public InvalidStepsException(String message) {
        super(message);
    }
}
