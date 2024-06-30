package com.company.complainservice.response;

/**
 * Represents an exception response containing a message and a status indicating success or failure.
 */
public class ExceptionResponse {
    private String message;
    private boolean status;

    public ExceptionResponse() {
    }

    public ExceptionResponse(String message, boolean status) {
        this.message = message;
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "apiresponse{" +
                "message='" + message + '\'' +
                ", status=" + status +
                '}';
    }
}
