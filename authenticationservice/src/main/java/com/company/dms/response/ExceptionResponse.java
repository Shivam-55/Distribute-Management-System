package com.company.dms.response;


/**
 * Class representing an exception response.
 */
public class ExceptionResponse {
    private String message;
    private Boolean status ;
    public ExceptionResponse() {
    }

    /**
     * Constructs an ExceptionResponse with the given message and status.
     *
     * @param message the message associated with the exception
     * @param status  the status of the response
     */
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
