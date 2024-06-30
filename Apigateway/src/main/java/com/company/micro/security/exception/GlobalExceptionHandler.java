package com.company.micro.security.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.company.micro.security.response.ExceptionResponse;

/**
 * Global exception handler for API-related exceptions.
 * This class handles exceptions thrown within the application and provides appropriate responses.
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * Handles ApiException and returns a ResponseEntity with an ExceptionResponse.
     *
     * @param ex The ApiException instance.
     * @return A ResponseEntity containing the ExceptionResponse and HttpStatus.BAD_REQUEST.
     */
    @ExceptionHandler(ApiException.class)
    public ResponseEntity<ExceptionResponse> apiException(ApiException ex){
        return new ResponseEntity<>(new ExceptionResponse(ex.getLocalizedMessage(),false), HttpStatus.BAD_REQUEST);
    }

    /**
     * Handles NoSuchAlgorithmFoundException and returns a ResponseEntity with an ExceptionResponse.
     *
     * @param ex The NoSuchAlgorithmFoundException instance.
     * @return A ResponseEntity containing the ExceptionResponse and HttpStatus.BAD_REQUEST.
     */
    @ExceptionHandler(NoSuchAlgorithmFoundException.class)
    public ResponseEntity<ExceptionResponse> noSuchAlgorithmFoundException(NoSuchAlgorithmFoundException ex){
        return new ResponseEntity<>(new ExceptionResponse(ex.getLocalizedMessage(),false), HttpStatus.NOT_FOUND);
    }
}
