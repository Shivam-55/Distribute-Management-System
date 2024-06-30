package com.company.dms.exception;
/**
 * Exception thrown when an invalid city name is encountered.
 */
public class InvalidCityNameException extends RuntimeException{
    /**
     * Constructs a new invalid city name exception with the specified detail message.
     *
     * @param message the detail message.
     */
    public InvalidCityNameException(String message){
        super(message);
    }
}
