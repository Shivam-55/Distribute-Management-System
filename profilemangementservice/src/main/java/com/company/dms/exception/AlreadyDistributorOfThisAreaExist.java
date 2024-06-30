package com.company.dms.exception;
/**
 * Exception thrown when attempting to create a distributor for an area where
 * another distributor already exists.
 */
public class AlreadyDistributorOfThisAreaExist extends RuntimeException{
    /**
     * Constructs a new AlreadyDistributorOfThisAreaExist with the specified detail message.
     *
     * @param message the detail message.
     */
    public AlreadyDistributorOfThisAreaExist(String message){
        super(message);
    }
}
