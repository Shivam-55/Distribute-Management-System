package com.example.inventoryservice.exception;

/**
 * Exception class representing a situation where no authority is present for an operation.
 */
public class NoAuthorityException extends RuntimeException{
    public NoAuthorityException(String s){
        super(s);
    }
}
