package com.company.complainservice.exception;

/**
 * Custom exception class.
 */
public class NoComplainReceivedException extends RuntimeException{
    public NoComplainReceivedException(String s){
        super(s);
    }
}
