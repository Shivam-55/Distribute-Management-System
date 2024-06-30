package com.example.inventoryservice.exception;

/**
 * Exception class representing a situation where no stock is available for a particular operation.
 */
public class NoStockAvailableException extends RuntimeException{
    public NoStockAvailableException(String s){
        super(s);
    }
}
