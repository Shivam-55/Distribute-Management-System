package com.example.inventoryservice.exception;

/**
 * Exception class representing a situation where no user is present for a particular operation.
 */
public class NoSuchUserPresentException extends RuntimeException{
    public NoSuchUserPresentException(String s){
        super(s);
    }
}
