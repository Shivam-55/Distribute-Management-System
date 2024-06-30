package com.company.micro.security.response;

/**
 * Response model for exception handling.
 * This class represents the response format for exceptions thrown within the application.
 */
public class ExceptionResponse {
    private String message;
    private boolean status;

    /**
     * Constructs a new ExceptionResponse with default values.
     */
    public ExceptionResponse() {
    }

    /**
     * Constructs a new ExceptionResponse with the specified message and status.
     *
     * @param message The message describing the exception.
     * @param status  The status indicating the success or failure of the operation.
     */
    public ExceptionResponse(String message, boolean status) {
        this.message = message;
        this.status = status;
    }

    /**
     * Gets the message describing the exception.
     *
     * @return The message describing the exception.
     */
    public String getMessage() {
        return message;
    }

    /**
     * Sets the message describing the exception.
     *
     * @param message The message describing the exception.
     */
    public void setMessage(String message) {
        this.message = message;
    }

    /**
     * Checks if the operation status is successful or not.
     *
     * @return true if the operation was successful, false otherwise.
     */
    public boolean isStatus() {
        return status;
    }

    /**
     * Sets the status indicating the success or failure of the operation.
     *
     * @param status The status indicating the success or failure of the operation.
     */
    public void setStatus(boolean status) {
        this.status = status;
    }

    /**
     * Returns a string representation of the ExceptionResponse.
     *
     * @return A string representation of the ExceptionResponse.
     */
    @Override
    public String toString() {
        return "apiresponse{" +
                "message='" + message + '\'' +
                ", status=" + status +
                '}';
    }
}

