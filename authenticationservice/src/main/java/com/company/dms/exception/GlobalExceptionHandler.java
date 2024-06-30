package com.company.dms.exception;

import org.apache.http.auth.InvalidCredentialsException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.company.dms.response.ExceptionResponse;

/**
 * Global exception handler for handling specific exceptions and returning appropriate responses.
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * Handle NoSuchUserPresentException and return a ResponseEntity with an ExceptionResponse.
     *
     * @param ex The NoSuchUserPresentException to handle
     * @return A ResponseEntity with an ExceptionResponse
     */
    @ExceptionHandler(NoSuchUserPresentException.class)
    public ResponseEntity<ExceptionResponse> noSuchUserPresentException(NoSuchUserPresentException ex){
        ExceptionResponse response = new ExceptionResponse(ex.getMessage(), false);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    /**
     * Handle InvalidCredentialsException and return a ResponseEntity with an ExceptionResponse.
     *
     * @param ex The InvalidCredentialsException to handle
     * @return A ResponseEntity with an ExceptionResponse
     */
    @ExceptionHandler(InvalidCredentialsException.class)
    public ResponseEntity<ExceptionResponse> invalidCredentialsException(InvalidCredentialsException ex){
        ExceptionResponse response = new ExceptionResponse(ex.getMessage(), false);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    /**
     * Handle ServiceDownException and return a ResponseEntity with an ExceptionResponse.
     *
     * @param ex The ServiceDownException to handle
     * @return A ResponseEntity with an ExceptionResponse
     */
    @ExceptionHandler(ServiceDownException.class)
    public ResponseEntity<ExceptionResponse> serviceDownException(ServiceDownException ex){
        ExceptionResponse response = new ExceptionResponse(ex.getMessage(), false);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    /**
     * Handle NoSuchAlgorithmFoundException and return a ResponseEntity with an ExceptionResponse.
     *
     * @param ex The NoSuchAlgorithmFoundException to handle
     * @return A ResponseEntity with an ExceptionResponse
     */
    @ExceptionHandler(NoSuchAlgorithmFoundException.class)
    public ResponseEntity<ExceptionResponse> noSuchAlgorithmFoundException(NoSuchAlgorithmFoundException ex){
        ExceptionResponse response = new ExceptionResponse(ex.getMessage(), false);
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }
}
