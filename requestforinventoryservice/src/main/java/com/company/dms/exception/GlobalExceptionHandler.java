package com.company.dms.exception;

import com.company.dms.response.ExceptionResponse;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;

import org.springframework.beans.factory.BeanCreationException;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.resource.NoResourceFoundException;

import java.sql.SQLException;

@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * Exception handler for ServiceDownException.
     * @param ex ServiceDownException instance
     * @return ResponseEntity containing the exception response and HTTP status
     */
    @ExceptionHandler(ServiceDownException.class)
    public ResponseEntity<ExceptionResponse> noSuchInventoryPresentException(ServiceDownException ex){
        return new ResponseEntity<>(new ExceptionResponse(ex.getMessage(),false), HttpStatus.SERVICE_UNAVAILABLE);
    }

    /**
     * Exception handler for AlreadyRequestPresentException.
     * @param ex AlreadyRequestPresentException instance
     * @return ResponseEntity containing the exception response and HTTP status
     */
    @ExceptionHandler(AlreadyRequestPresentException.class)
    public ResponseEntity<ExceptionResponse> noSuchInventoryPresentException(AlreadyRequestPresentException ex){
        return new ResponseEntity<>(new ExceptionResponse(ex.getMessage(),false), HttpStatus.CONFLICT);
    }

    /**
     * Exception handler for NoSuchRequestAvailableException.
     * @param ex NoSuchRequestAvailableException instance
     * @return ResponseEntity containing the exception response and HTTP status
     */
    @ExceptionHandler(NoSuchRequestAvailableException.class)
    public ResponseEntity<ExceptionResponse> noSuchRequestAvailableException(NoSuchRequestAvailableException ex){
        return new ResponseEntity<>(new ExceptionResponse(ex.getMessage(),false),HttpStatus.BAD_REQUEST);
    }

    /**
     * Exception handler for NoSuchInventoryPresentException.
     * @param ex NoSuchInventoryPresentException instance
     * @return ResponseEntity containing the exception response and HTTP status
     */
    @ExceptionHandler(NoSuchInventoryPresentException.class)
    public ResponseEntity<ExceptionResponse> noSuchInventoryPresentException(NoSuchInventoryPresentException ex){
        return new ResponseEntity<>(new ExceptionResponse(ex.getMessage(),false),HttpStatus.NOT_FOUND);
    }

    /**
     * Exception handler for NoSuchUserPresentException.
     * @param ex NoSuchUserPresentException instance
     * @return ResponseEntity containing the exception response and HTTP status
     */
    @ExceptionHandler(NoSuchUserPresentException.class)
    public ResponseEntity<ExceptionResponse> noSuchUserFoundException(NoSuchUserPresentException ex){
        return new ResponseEntity<>(new ExceptionResponse(ex.getMessage(),false),HttpStatus.NOT_FOUND);
    }

    /**
     * Exception handler for ConnectionNotFoundException.
     * @param ex ConnectionNotFoundException instance
     * @return ResponseEntity containing the exception response and HTTP status
     */
    @ExceptionHandler(ConnectionNotFoundException.class)
    public ResponseEntity<ExceptionResponse> noConnectionFoundException(ConnectionNotFoundException ex){
        return new ResponseEntity<>(new ExceptionResponse(ex.getMessage(),false),HttpStatus.NOT_FOUND);
    }

    /**
     * Exception handler for UserNotApprovedException.
     * @param ex UserNotApprovedException instance
     * @return ResponseEntity containing the exception response and HTTP status
     */
    @ExceptionHandler(UserNotApprovedException.class)
    public ResponseEntity<ExceptionResponse> userNotApprovedException(UserNotApprovedException ex){
        return new ResponseEntity<>(new ExceptionResponse(ex.getMessage(),false),HttpStatus.BAD_REQUEST);
    }

    /**
     * Exception handler for IllegalArgumentException.
     * @param ex IllegalArgumentException instance
     * @return ResponseEntity containing the exception response and HTTP status
     */
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ExceptionResponse> illegalArgumentException(IllegalArgumentException ex){
        return new ResponseEntity<>(new ExceptionResponse(ex.getMessage(),false),HttpStatus.BAD_REQUEST);
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
     * Exception handler for MessageParsingException.
     * @param ex MessageParsingException instance
     * @return ResponseEntity containing the exception response and HTTP status
     */
    @ExceptionHandler(MessageParsingException.class)
    public ResponseEntity<ExceptionResponse> messageParsingException(MessageParsingException ex){
        return new ResponseEntity<>(new ExceptionResponse(ex.getMessage(),false), HttpStatus.BAD_REQUEST);
    }

    /**
     * Exception handler for NoSuchAlgorithmFoundException.
     * @param ex NoSuchAlgorithmFoundException instance
     * @return ResponseEntity containing the exception response and HTTP status
     */
    @ExceptionHandler(NoSuchAlgorithmFoundException.class)
    public ResponseEntity<ExceptionResponse> noSuchAlgorithmFoundException(NoSuchAlgorithmFoundException ex){
        return new ResponseEntity<>(new ExceptionResponse(ex.getMessage(),false), HttpStatus.NOT_FOUND);
    }

}
