package com.company.micro.security.exception;

/**
 * Custom exception class for API-related errors.
 * This exception is used to indicate errors that occur during API operations.
 */
public class ApiException extends RuntimeException {

	/**
	 * Constructs a new ApiException with the specified detail message.
	 *
	 * @param message the detail message (which is saved for later retrieval by the getMessage() method)
	 */
	public ApiException(String message) {
		super(message);
	}

}
