package com.company.complainservice.exception;

/**
 * Custom exception class.
 */
public class ServiceDownException extends RuntimeException{
    public ServiceDownException(String s){
        super(s);
    }
}
