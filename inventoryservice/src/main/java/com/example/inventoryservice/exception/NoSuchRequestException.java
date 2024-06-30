package com.example.inventoryservice.exception;

/**
 * Exception class representing a situation where no request is found for a particular operation.
 */
public class NoSuchRequestException extends RuntimeException{
    public NoSuchRequestException(String s){
        super(s);
    }
}
