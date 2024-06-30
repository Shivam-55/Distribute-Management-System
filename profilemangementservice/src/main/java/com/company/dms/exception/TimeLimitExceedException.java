package com.company.dms.exception;

/**
 * Exception thrown when a time limit is exceeded.
 */
public class TimeLimitExceedException extends RuntimeException {

    /**
     * Constructs a new exception with the specified detail message.
     *
     * @param message the detail message.
     */
    public TimeLimitExceedException(String message) {
        super(message);
    }
}