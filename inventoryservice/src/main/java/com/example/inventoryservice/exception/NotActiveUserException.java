package com.example.inventoryservice.exception;

/**
 * Exception class representing a situation where a user is not active for a particular operation.
 */
public class NotActiveUserException extends RuntimeException{
    public NotActiveUserException(String s){
        super(s);
    }
}
