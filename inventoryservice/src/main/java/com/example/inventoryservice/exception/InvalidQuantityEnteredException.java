package com.example.inventoryservice.exception;

/**
 * Exception class representing a situation where an invalid quantity is entered.
 */
public class InvalidQuantityEnteredException extends RuntimeException{
    public InvalidQuantityEnteredException(String s){
        super(s);
    }
}
