package com.company.complainservice.exception;

/**
 * Custom exception class.
 */
public class IllegalStatusArgumentException extends RuntimeException{
    public IllegalStatusArgumentException(String s){
        super(s);
    }
}
