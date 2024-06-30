package com.example.inventoryservice.exception;

/**
 * Exception class representing a situation where a service is down or unavailable.
 */
public class ServiceDownException extends RuntimeException{
    public ServiceDownException(String s){
        super(s);
    }
}
