package com.company.complainservice.exception;

/**
 * Custom exception class.
 */
public class UserNotApprovedException extends RuntimeException{
    public UserNotApprovedException(String s){
        super(s);
    }
}
