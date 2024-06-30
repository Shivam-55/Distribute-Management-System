package com.example.inventoryservice.exception;


/**
 * Exception class representing a situation where an invalid request quantity is encountered.
 */
public class InvalidRequestQuantityException extends RuntimeException{
    public InvalidRequestQuantityException(String s){
        super(s);
    }
}
