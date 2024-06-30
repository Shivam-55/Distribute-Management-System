package com.example.inventoryservice.response;

/**
 * Represents an exception response containing a message and a status indicating success or failure.
 */
public class ExceptionResponse {
    private String message;
    private Boolean status ;
    public ExceptionResponse() {
    }
    public ExceptionResponse(String message, Boolean status) {
        this.message = message;
        this.status = status;
    }
    public String getMessage() {
        return message;
    }
    public void setMessage(String message) {
        this.message = message;
    }
    public Boolean getStatus() {
        return status;
    }
    public void setStatus(Boolean status) {
        this.status = status;
    }
}

