package com.company.dms.response;
/**
 * Represents an exception response containing a message and status.
 */
public class ExceptionResponse {
    private String message;
    private Boolean status ;
    public ExceptionResponse() {
    }
    /**
     * Constructs an ExceptionResponse with the given message and status.
     *
     * @param message The message describing the exception.
     * @param status  The status indicating the exception status.
     */
    public ExceptionResponse(String message, Boolean status) {
        this.message = message;
        this.status = status;
    }
    /**
     * Retrieves the message of the exception.
     *
     * @return The message of the exception.
     */
    public String getMessage() {
        return message;
    }

    /**
     * Sets the message of the exception.
     *
     * @param message The message of the exception.
     */
    public void setMessage(String message) {
        this.message = message;
    }
    /**
     * Retrieves the status of the exception.
     *
     * @return The status of the exception.
     */
    public Boolean getStatus() {
        return status;
    }
    /**
     * Sets the status of the exception.
     *
     * @param status The status of the exception.
     */
    public void setStatus(Boolean status) {
        this.status = status;
    }
}


