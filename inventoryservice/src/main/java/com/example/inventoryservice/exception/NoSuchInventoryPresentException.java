package com.example.inventoryservice.exception;

/**
 * Exception class representing a situation where no inventory is present for a particular operation.
 */
public class NoSuchInventoryPresentException extends RuntimeException{
    public NoSuchInventoryPresentException(String s){
        super(s);
    }
}
