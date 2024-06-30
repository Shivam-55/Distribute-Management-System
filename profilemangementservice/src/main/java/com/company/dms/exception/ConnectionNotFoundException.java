package com.company.dms.exception;
/**
 * Exception thrown when a connection is not found.
 */
public class ConnectionNotFoundException extends RuntimeException{
    /**
     * Constructs a new connection not found exception with the specified detail message.
     *
     * @param message the detail message.
     */
    public ConnectionNotFoundException(String message){
        super(message);
    }
}
