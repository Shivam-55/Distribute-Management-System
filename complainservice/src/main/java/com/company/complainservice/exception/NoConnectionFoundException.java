package com.company.complainservice.exception;

/**
 * Custom exception class.
 */
public class NoConnectionFoundException extends RuntimeException{
    public NoConnectionFoundException(String s){
        super(s);
    }
}
