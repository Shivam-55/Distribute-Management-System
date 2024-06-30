package com.company.dms.exception;

/**
 * Exception thrown when a user is not present.
 */
public class NoSuchUserPresentException extends RuntimeException{

    /**
     * Constructs a new NoSuchUserPresentException with the specified detail message.
     *
     * @param message the detail message.
     */
    public NoSuchUserPresentException(String message){
        super(message);
    }
}
