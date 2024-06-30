package com.company.dms.exception;
/**
 * Exception thrown when an invalid OTP (One-Time Password) is entered.
 */
public class InvalidOtpEnteredException extends RuntimeException{
    /**
     * Constructs a new invalid OTP exception with the specified detail message.
     *
     * @param message the detail message.
     */
    public InvalidOtpEnteredException(String message) {
        super(message);
    }
}
