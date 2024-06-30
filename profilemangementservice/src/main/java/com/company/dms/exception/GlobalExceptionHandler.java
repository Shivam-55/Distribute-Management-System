package com.company.dms.exception;

import com.company.dms.response.ExceptionResponse;
import com.company.dms.utils.ExcludeFromCodeCoverage;
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
/**
 * Global exception handler to handle all uncaught exceptions in the application.
 */
@ExcludeFromCodeCoverage
@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * Exception handler for NoSuchUserPresentException.
     * @param ex NoSuchUserPresentException instance
     * @return ResponseEntity containing the exception response and HTTP status
     */
    @ExceptionHandler(NoSuchUserPresentException.class)
    public ResponseEntity<ExceptionResponse> noSuchUserPresentException(NoSuchUserPresentException ex){
        ExceptionResponse response = new ExceptionResponse(ex.getMessage(), false);
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    /**
     * Exception handler for NoSuchUserActiveException.
     * @param ex NoSuchUserActiveException instance
     * @return ResponseEntity containing the exception response and HTTP status
     */
    @ExceptionHandler(NoSuchUserActiveException.class)
    public ResponseEntity<ExceptionResponse> noSuchUserActiveException(NoSuchUserActiveException ex){
        ExceptionResponse response = new ExceptionResponse(ex.getMessage(), false);
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    /**
     * Exception handler for PincodeCityMismatchException.
     * @param ex PincodeCityMismatchException instance
     * @return ResponseEntity containing the exception response and HTTP status
     */
    @ExceptionHandler(PincodeCityMismatchException.class)
    public ResponseEntity<ExceptionResponse> pinCodeCityMismatchException(PincodeCityMismatchException ex){
        ExceptionResponse response = new ExceptionResponse(ex.getMessage(), false);
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }


    /**
     * Exception handler for AlreadyDistributorOfThisAreaExist.
     * @param ex AlreadyDistributorOfThisAreaExist instance
     * @return ResponseEntity containing the exception response and HTTP status
     */
    @ExceptionHandler(AlreadyDistributorOfThisAreaExist.class)
    public ResponseEntity<ExceptionResponse> alreadyDistributorOfThisAreaExist(AlreadyDistributorOfThisAreaExist ex){
        ExceptionResponse response = new ExceptionResponse(ex.getMessage(), false);
        return new ResponseEntity<>(response, HttpStatus.CONFLICT);
    }


    /**
     * Exception handler for ConnectionNotFoundException.
     * @param ex ConnectionNotFoundException instance
     * @return ResponseEntity containing the exception response and HTTP status
     */
    @ExceptionHandler(ConnectionNotFoundException.class)
    public ResponseEntity<ExceptionResponse> connectionNotFoundException(ConnectionNotFoundException ex){
        ExceptionResponse response = new ExceptionResponse(ex.getMessage(), false);
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    /**
     * Exception handler for NoRequestAvailableExcption.
     * @param ex NoRequestAvailableExcption instance
     * @return ResponseEntity containing the exception response and HTTP status
     */
    @ExceptionHandler(NoRequestAvailableException.class)
    public ResponseEntity<ExceptionResponse> noRequestAvailableException(NoRequestAvailableException ex){
        ExceptionResponse response = new ExceptionResponse(ex.getMessage(), false);
        return new ResponseEntity<>(response, HttpStatus.NO_CONTENT);
    }


    /**
     * Exception handler for InvalidEmailException.
     * @param ex InvalidEmailException instance
     * @return ResponseEntity containing the exception response and HTTP status
     */
    @ExceptionHandler(InvalidEmailException.class)
    public ResponseEntity<ExceptionResponse> invalidEmailException(InvalidEmailException ex){
        ExceptionResponse response = new ExceptionResponse(ex.getMessage(), false);
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }


    /**
     * Exception handler for AdminCanNotRemoveException.
     * @param ex AdminCanNotRemoveException instance
     * @return ResponseEntity containing the exception response and HTTP status
     */
    @ExceptionHandler(AdminCanNotRemoveException.class)
    public ResponseEntity<ExceptionResponse> adminCanNotRemoveException(AdminCanNotRemoveException ex){
        ExceptionResponse response = new ExceptionResponse(ex.getMessage(), false);
        return new ResponseEntity<>(response, HttpStatus.FORBIDDEN);
    }


    /**
     * Exception handler for UserNotApprovedException.
     * @param ex UserNotApprovedException instance
     * @return ResponseEntity containing the exception response and HTTP status
     */
    @ExceptionHandler(UserNotApprovedException.class)
    public ResponseEntity<ExceptionResponse> userNotApprovedException(UserNotApprovedException ex){
        ExceptionResponse response = new ExceptionResponse(ex.getMessage(), false);
        return new ResponseEntity<>(response, HttpStatus.FORBIDDEN);
    }


    /**
     * Exception handler for NoSuchRolePresentException.
     * @param ex NoSuchRolePresentException instance
     * @return ResponseEntity containing the exception response and HTTP status
     */
    @ExceptionHandler(NoSuchRolePresentException.class)
    public ResponseEntity<ExceptionResponse> noSuchRolePresentException(NoSuchRolePresentException ex){
        ExceptionResponse response = new ExceptionResponse(ex.getMessage(), false);
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }


    /**
     * Exception handler for AlreadyEmailExistException.
     * @param ex AlreadyEmailExistException instance
     * @return ResponseEntity containing the exception response and HTTP status
     */
    @ExceptionHandler(AlreadyEmailExistException.class)
    public ResponseEntity<ExceptionResponse> alreadyEmailExistException(AlreadyEmailExistException ex){
        ExceptionResponse response = new ExceptionResponse(ex.getMessage(), false);
        return new ResponseEntity<>(response, HttpStatus.CONFLICT);
    }


    /**
     * Exception handler for NotActiveAnyMoreException.
     * @param ex NotActiveAnyMoreException instance
     * @return ResponseEntity containing the exception response and HTTP status
     */
    @ExceptionHandler(NotActiveAnyMoreException.class)
    public ResponseEntity<ExceptionResponse> notActiveAnyMoreException(NotActiveAnyMoreException ex){
        ExceptionResponse response = new ExceptionResponse(ex.getMessage(), false);
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    /**
     * Exception handler for RetailerAlreadyExist.
     * @param ex RetailerAlreadyExist instance
     * @return ResponseEntity containing the exception response and HTTP status
     */
    @ExceptionHandler(RetailerAlreadyExist.class)
    public ResponseEntity<ExceptionResponse> retailerAlreadyExist(RetailerAlreadyExist ex){
        ExceptionResponse response = new ExceptionResponse(ex.getMessage(), false);
        return new ResponseEntity<>(response, HttpStatus.CONFLICT);
    }


    /**
     * Exception handler for NotPermissionException.
     * @param ex NotPermissionException instance
     * @return ResponseEntity containing the exception response and HTTP status
     */
    @ExceptionHandler(NotPermissionException.class)
    public ResponseEntity<ExceptionResponse> notPermissionException(NotPermissionException ex){
        ExceptionResponse response = new ExceptionResponse(ex.getMessage(), false);
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }


    /**
     * Exception handler for InvalidStepsException.
     * @param ex InvalidStepsException instance
     * @return ResponseEntity containing the exception response and HTTP status
     */
    @ExceptionHandler(InvalidStepsException.class)
    public ResponseEntity<ExceptionResponse> invalidStepsException(InvalidStepsException ex){
        ExceptionResponse response = new ExceptionResponse(ex.getMessage(), false);
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }


    /**
     * Exception handler for InvalidOtpEnteredException.
     * @param ex InvalidOtpEnteredException instance
     * @return ResponseEntity containing the exception response and HTTP status
     */
    @ExceptionHandler(InvalidOtpEnteredException.class)
    public ResponseEntity<ExceptionResponse> invalidOtpEnteredException(InvalidOtpEnteredException ex){
        ExceptionResponse response = new ExceptionResponse(ex.getMessage(), false);
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }


    /**
     * Exception handler for TimeLimitExceedException.
     * @param ex TimeLimitExceedException instance
     * @return ResponseEntity containing the exception response and HTTP status
     */
    @ExceptionHandler(TimeLimitExceedException.class)
    public ResponseEntity<ExceptionResponse> timeLimitExceedException(TimeLimitExceedException ex){
        ExceptionResponse response = new ExceptionResponse(ex.getMessage(), false);
        return new ResponseEntity<>(response, HttpStatus.REQUEST_TIMEOUT);
    }


    /**
     * Exception handler for ServiceDownException.
     * @param ex ServiceDownException instance
     * @return ResponseEntity containing the exception response and HTTP status
     */
    @ExceptionHandler(ServiceDownException.class)
    public ResponseEntity<ExceptionResponse> serviceDownException(ServiceDownException ex){
        ExceptionResponse response = new ExceptionResponse(ex.getMessage(), false);
        return new ResponseEntity<>(response, HttpStatus.SERVICE_UNAVAILABLE);
    }


    /**
     * Exception handler for InvalidCityNameException.
     * @param ex InvalidCityNameException instance
     * @return ResponseEntity containing the exception response and HTTP status
     */
    @ExceptionHandler(InvalidCityNameException.class)
    public ResponseEntity<ExceptionResponse> invalidCityNameException(InvalidCityNameException ex){
        ExceptionResponse response = new ExceptionResponse(ex.getMessage(), false);
        return new ResponseEntity<>(response, HttpStatus.SERVICE_UNAVAILABLE);
    }


    /**
     * Exception handler for InvalidRoleException.
     * @param ex InvalidRoleException instance
     * @return ResponseEntity containing the exception response and HTTP status
     */
    @ExceptionHandler(InvalidRoleException.class)
    public ResponseEntity<ExceptionResponse> invalidRoleException(InvalidRoleException ex){
        ExceptionResponse response = new ExceptionResponse(ex.getMessage(), false);
        return new ResponseEntity<>(response, HttpStatus.SERVICE_UNAVAILABLE);
    }


    /**
     * Exception handler for NoSuchConnectionFoundException.
     * @param ex NoSuchConnectionFoundException instance
     * @return ResponseEntity containing the exception response and HTTP status
     */
    @ExceptionHandler(NoSuchConnectionFoundException.class)
    public ResponseEntity<ExceptionResponse> noSuchConnectionFoundException(NoSuchConnectionFoundException ex){
        ExceptionResponse response = new ExceptionResponse(ex.getMessage(), false);
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
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
     * Exception handler for MessageParsingException.
     * @param ex MessageParsingException instance
     * @return ResponseEntity containing the exception response and HTTP status
     */
    @ExceptionHandler(MessageParsingException.class)
    public ResponseEntity<ExceptionResponse> messageParsingException(MessageParsingException ex){
        return new ResponseEntity<>(new ExceptionResponse(ex.getMessage(),false), HttpStatus.BAD_REQUEST);
    }

    /**
     * Exception handler for NoAuthorizedException.
     * @param ex NoAuthorizedException instance
     * @return ResponseEntity containing the exception response and HTTP status
     */
    @ExceptionHandler(NoAuthorizedException.class)
    public ResponseEntity<ExceptionResponse> noAuthorizedException(NoAuthorizedException ex){
        return new ResponseEntity<>(new ExceptionResponse(ex.getMessage(),false), HttpStatus.UNAUTHORIZED);
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
