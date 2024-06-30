package com.company.complainservice.exception;

/**
 * Custom exception class.
 */
public class NoFeedbackReceivedException extends RuntimeException{
    public NoFeedbackReceivedException(String s){
        super(s);
    }
}
