package com.example.inventoryservice.exception;

/**
 * Exception class representing a situation where a higher quantity demand is encountered.
 */
public class HigherQuantityDemandException extends RuntimeException{
    public HigherQuantityDemandException(String s){
        super(s);
    }
}
