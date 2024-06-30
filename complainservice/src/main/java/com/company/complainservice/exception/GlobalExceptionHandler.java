package com.company.complainservice.exception;

import com.company.complainservice.response.ExceptionResponse;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;

import org.springframework.beans.factory.BeanCreationException;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.resource.NoResourceFoundException;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

/**
 * Global exception handler for handling exceptions thrown by controllers.
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * Exception handler for ServiceDownException.
     * @param ex ServiceDownException instance
     * @return ResponseEntity containing the exception response and HTTP status
     */
    @ExceptionHandler(ServiceDownException.class)
    public ResponseEntity<ExceptionResponse> serviceDownException(ServiceDownException ex){
        return new ResponseEntity<>(new ExceptionResponse(ex.getMessage(),false), HttpStatus.SERVICE_UNAVAILABLE);
    }

    /**
     * Exception handler for NoComplainReceivedException.
     * @param ex NoComplainReceivedException instance
     * @return ResponseEntity containing the exception response and HTTP status
     */
    @ExceptionHandler(NoComplainReceivedException.class)
    public ResponseEntity<ExceptionResponse> noComplainReceivedException(NoComplainReceivedException ex){
        return new ResponseEntity<>(new ExceptionResponse(ex.getMessage(),false), HttpStatus.NO_CONTENT);
    }

    /**
     * Exception handler for NoFeedbackReceivedException.
     * @param ex NoFeedbackReceivedException instance
     * @return ResponseEntity containing the exception response and HTTP status
     */
    @ExceptionHandler(NoFeedbackReceivedException.class)
    public ResponseEntity<ExceptionResponse> noFeedbackReceivedException(NoFeedbackReceivedException ex){
        return new ResponseEntity<>(new ExceptionResponse(ex.getMessage(),false), HttpStatus.NO_CONTENT);
    }

    /**
     * Exception handler for noConnectionFoundException.
     * @param ex NoConnectionFoundException instance
     * @return ResponseEntity containing the exception response and HTTP status
     */
    @ExceptionHandler(NoConnectionFoundException.class)
    public ResponseEntity<ExceptionResponse> noConnectionFoundException(NoConnectionFoundException ex){
        return new ResponseEntity<>(new ExceptionResponse(ex.getMessage(),false), HttpStatus.NOT_FOUND);
    }

    /**
     * Exception handler for noSuchUserFoundException.
     * @param ex NoSuchUserPresentException instance
     * @return ResponseEntity containing the exception response and HTTP status
     */
    @ExceptionHandler(NoSuchUserPresentException.class)
    public ResponseEntity<ExceptionResponse> noSuchUserFoundException(NoSuchUserPresentException ex){
        return new ResponseEntity<>(new ExceptionResponse(ex.getMessage(),false), HttpStatus.NOT_FOUND);
    }

    /**
     * Exception handler for IllegalStatusArgumentException.
     * @param ex IllegalStatusArgumentException instance
     * @return ResponseEntity containing the exception response and HTTP status
     */
    @ExceptionHandler(IllegalStatusArgumentException.class)
    public ResponseEntity<ExceptionResponse> illegalStatusArgumentException(IllegalStatusArgumentException ex){
        return new ResponseEntity<>(new ExceptionResponse(ex.getMessage(),false), HttpStatus.BAD_REQUEST);
    }

    /**
     * Exception handler for InvalidDataAccessApiUsageException.
     * @param ex InvalidDataAccessApiUsageException instance
     * @return ResponseEntity containing the exception response and HTTP status
     */
    @ExceptionHandler(InvalidDataAccessApiUsageException.class)
    public ResponseEntity<ExceptionResponse> invalidDataAccessApiUsageException(InvalidDataAccessApiUsageException ex){
        return new ResponseEntity<>(new ExceptionResponse(ex.getMessage(),false), HttpStatus.BAD_REQUEST);
    }

    /**
     * Exception handler for HttpRequestMethodNotSupportedException.
     * @param ex HttpRequestMethodNotSupportedException instance
     * @return ResponseEntity containing the exception response and HTTP status
     */
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResponseEntity<ExceptionResponse> httpRequestMethodNotSupportedException(HttpRequestMethodNotSupportedException ex){
        return new ResponseEntity<>(new ExceptionResponse(ex.getMessage(),false), HttpStatus.BAD_REQUEST);
    }

    /**
     * Exception handler for MissingServletRequestParameterException.
     * @param ex MissingServletRequestParameterException instance
     * @return ResponseEntity containing the exception response and HTTP status
     */
    @ExceptionHandler(MissingServletRequestParameterException.class)
    public ResponseEntity<ExceptionResponse> missingServletRequestParameterException(MissingServletRequestParameterException ex){
        return new ResponseEntity<>(new ExceptionResponse(ex.getMessage(),false), HttpStatus.BAD_REQUEST);
    }

    /**
     * Exception handler for HttpMessageNotReadableException.
     * @param ex HttpMessageNotReadableException instance
     * @return ResponseEntity containing the exception response and HTTP status
     */
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ExceptionResponse> httpMessageNotReadableException(HttpMessageNotReadableException ex){
        return new ResponseEntity<>(new ExceptionResponse(ex.getMessage(),false), HttpStatus.BAD_REQUEST);
    }

    /**
     * Exception handler for NoResourceFoundException.
     * @param ex NoResourceFoundException instance
     * @return ResponseEntity containing the exception response and HTTP status
     */
    @ExceptionHandler(NoResourceFoundException.class)
    public ResponseEntity<ExceptionResponse> noResourceFoundException(NoResourceFoundException ex){
        return new ResponseEntity<>(new ExceptionResponse(ex.getMessage(),false), HttpStatus.BAD_REQUEST);
    }

    /**
     * Exception handler for DataAccessException.
     * @param ex DataAccessException instance
     * @return ResponseEntity containing the exception response and HTTP status
     */
    @ExceptionHandler(DataAccessException.class)
    public ResponseEntity<ExceptionResponse> dataAccessException(DataAccessException ex){
        return new ResponseEntity<>(new ExceptionResponse(ex.getMessage(),false), HttpStatus.BAD_REQUEST);
    }

    /**
     * Exception handler for IllegalArgumentException.
     * @param ex IllegalArgumentException instance
     * @return ResponseEntity containing the exception response and HTTP status
     */
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ExceptionResponse> illegalArgumentException(IllegalArgumentException ex){
        return new ResponseEntity<>(new ExceptionResponse(ex.getMessage(),false), HttpStatus.BAD_REQUEST);
    }

    /**
     * Exception handler for InvalidFormatException.
     * @param ex InvalidFormatException instance
     * @return ResponseEntity containing the exception response and HTTP status
     */
    @ExceptionHandler(InvalidFormatException.class)
    public ResponseEntity<ExceptionResponse> invalidFormatException(InvalidFormatException ex){
        return new ResponseEntity<>(new ExceptionResponse(ex.getMessage(),false), HttpStatus.BAD_REQUEST);
    }

    /**
     * Exception handler for SQLException.
     * @param ex SQLException instance
     * @return ResponseEntity containing the exception response and HTTP status
     */
    @ExceptionHandler(SQLException.class)
    public ResponseEntity<ExceptionResponse> sqlexception(SQLException ex){
        return new ResponseEntity<>(new ExceptionResponse(ex.getMessage(),false), HttpStatus.BAD_REQUEST);
    }

    /**
     * Exception handler for BeanCreationException.
     * @param ex BeanCreationException instance
     * @return ResponseEntity containing the exception response and HTTP status
     */
    @ExceptionHandler(BeanCreationException.class)
    public ResponseEntity<ExceptionResponse> beanCreationException(BeanCreationException ex){
        return new ResponseEntity<>(new ExceptionResponse(ex.getMessage(),false), HttpStatus.BAD_REQUEST);
    }

    /**
     * Exception handler for userNotApprovedException.
     * @param ex UserNotApprovedException instance
     * @return ResponseEntity containing the exception response and HTTP status
     */
    @ExceptionHandler(UserNotApprovedException.class)
    public ResponseEntity<ExceptionResponse> userNotApprovedException(UserNotApprovedException ex){
        return new ResponseEntity<>(new ExceptionResponse(ex.getMessage(),false), HttpStatus.BAD_REQUEST);
    }


    /**
     * Exception handler for NoSuchAlgorithmFoundException.
     * @param ex NoSuchAlgorithmFoundException instance
     * @return ResponseEntity containing the exception response and HTTP status
     */
    @ExceptionHandler(NoSuchAlgorithmFoundException.class)
    public ResponseEntity<ExceptionResponse> noSuchAlgorithmFoundException(NoSuchAlgorithmFoundException ex){
        return new ResponseEntity<>(new ExceptionResponse(ex.getMessage(),false), HttpStatus.BAD_REQUEST);
    }

    /**
     * Exception handler for MethodArgumentNotValidException.
     * @param ex MethodArgumentNotValidException instance
     * @return ResponseEntity containing the error map and HTTP status
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String,String>> methodArgumentNotValidException(MethodArgumentNotValidException ex){
        Map<String,String> res = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach(error->{
            String fieldName = ((FieldError)error).getField();
            String message = error.getDefaultMessage();
            res.put(fieldName,message);
        });
        return new ResponseEntity<>(res,HttpStatus.NOT_ACCEPTABLE);
    }
}
