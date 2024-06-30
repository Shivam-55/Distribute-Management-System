package com.company.complainservice.exception;

/**
 * Custom exception class.
 */
public class NoSuchUserPresentException extends RuntimeException{
    public NoSuchUserPresentException(String s){
        super(s);
    }
}
