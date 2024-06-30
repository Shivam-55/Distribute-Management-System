package com.example.inventoryservice.exception;

/**
 * Exception class representing a situation where a user is not approved for a particular operation.
 */
public class UserNotApprovedException extends RuntimeException{
    public UserNotApprovedException(String s){
        super(s);
    }
}
