package com.company.dms.exception;


/**
 * Exception thrown when a service is down.
 */
public class ServiceDownException extends RuntimeException{

    /**
     * Constructs a new ServiceDownException with the specified detail message.
     *
     * @param message the detail message.
     */
    public ServiceDownException(String message){
        super(message);
    }
}
