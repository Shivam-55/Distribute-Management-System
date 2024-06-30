package com.example.inventoryservice.exception;

/**
 * Exception class representing a failed status update operation.
 */
public class FailedStatusUpdateException extends RuntimeException{
    public FailedStatusUpdateException(String s){
        super(s);
    }
}
