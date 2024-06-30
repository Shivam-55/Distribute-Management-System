package com.company.dms.exception;
/**
 * Exception thrown when an invalid email address is encountered.
 */
public class InvalidEmailException extends RuntimeException{
    /**
     * Constructs a new invalid email exception with the specified detail message.
     *
     * @param message the detail message.
     */
    public InvalidEmailException(String message){
        super(message);
    }
}
