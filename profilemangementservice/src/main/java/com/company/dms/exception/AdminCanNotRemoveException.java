package com.company.dms.exception;
/**
 * Exception thrown when an attempt is made to remove an entity by an admin user
 * where removal is not allowed.
 */
public class AdminCanNotRemoveException extends RuntimeException{

    /**
     * Constructs a new AdminCanNotRemoveException with the specified detail message.
     *
     * @param message the detail message.
     */
    public AdminCanNotRemoveException(String message){
        super(message);
    }
}
