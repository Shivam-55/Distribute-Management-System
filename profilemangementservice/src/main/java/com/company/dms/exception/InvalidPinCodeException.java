package com.company.dms.exception;

/**
 * Exception thrown when an invalid PIN code is encountered.
 */
public class InvalidPinCodeException extends RuntimeException {

    /**
     * Constructs a new invalid PIN code exception with the specified detail message.
     *
     * @param message the detail message.
     */
    public InvalidPinCodeException(String message) {
        super(message);
    }
}
