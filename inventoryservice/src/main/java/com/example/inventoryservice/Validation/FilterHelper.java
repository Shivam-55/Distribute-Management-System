package com.example.inventoryservice.Validation;

import com.example.inventoryservice.Validation.fieldvalidation.FieldValidator;
import com.example.inventoryservice.Validation.filedvalidatoradaptor.FieldValidatorAdapter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Map;

import static com.example.inventoryservice.Validation.ReadRequestBody.readRequestBody;
/**
 * Helper class for filtering HTTP requests.
 */
public class FilterHelper {
    private static final Logger LOGGER = LoggerFactory.getLogger(FilterHelper.class);
    private static final String INVALID_METHOD = "Invalid Method" ;
    private static final String INVALID_CONTENT_TYPE = "Invalid Content Type";
    /**
     * Filter helper method for POST requests.
     * Validates request body fields and forwards the request if all fields are valid.
     * @param request the HTTP servlet request
     * @param response the HTTP servlet response
     * @param fieldValidators a map of field validators
     * @param aClass the class associated with the request
     * @param filterChain the filter chain
     * @throws IOException if an I/O error occurs
     * @throws ServletException if a servlet error occurs
     */
    public void helperForEachFilterForPost(HttpServletRequest request, HttpServletResponse response, Map<String, FieldValidator> fieldValidators, Class<?>aClass, FilterChain filterChain) throws IOException, ServletException {

        if(!request.getMethod().equals("POST")){
            response.sendError(405, INVALID_METHOD);
        }
        if (request.getContentType() != null && request.getContentType().startsWith("application/json")) {
            String requestBody = readRequestBody(request);
            FieldValidatorAdapter fieldValidationService = new FieldValidatorAdapter(fieldValidators, aClass);
            String resultOfAllField= fieldValidationService.validateFields(ConvertMapHelper.convertStringToMap(requestBody), aClass);
            if(!"OK".equalsIgnoreCase(resultOfAllField)){
                InvalidFieldException.allInvalidField(response, resultOfAllField);
                LOGGER.info("Exiting due to Invalid field {}", FilterHelper.class.getName());
            }
            else{
                HttpServletRequest wrappedRequest = new CachedBodyHttpServletRequest(request, requestBody);
                filterChain.doFilter(wrappedRequest, response);
                LOGGER.info("Exiting because of all field pass");
            }

        }
        else{
            response.sendError(415, INVALID_CONTENT_TYPE);
        }
    }

    /**
     * Filter helper method for GET requests with a long path variable.
     * Validates the path variable to ensure it's a valid long value.
     * @param request the HTTP servlet request
     * @param response the HTTP servlet response
     * @param filterChain the filter chain
     * @throws IOException if an I/O error occurs
     * @throws ServletException if a servlet error occurs
     */
    public void helperForEachFilterGetMethodForLong(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws IOException, ServletException {

        if(!request.getMethod().equals("GET")){
            response.sendError(405, INVALID_METHOD);
        }
        String requestUri = request.getRequestURI();
        String[] segments = requestUri.split("/");
        String id = segments[segments.length - 1];

        try {
            Long.parseLong(id);
        }
        catch (Exception exception)
        {
            LOGGER.info("Exit due to Invalid field");
            response.sendError(405,"Invalid Path variable");
        }
        filterChain.doFilter(request, response);
    }
    /**
     * Filter helper method for PUT requests.
     * Validates request body fields and forwards the request if all fields are valid.
     * @param request the HTTP servlet request
     * @param response the HTTP servlet response
     * @param fieldValidators a map of field validators
     * @param clazz the class associated with the request
     * @param filterChain the filter chain
     * @throws IOException if an I/O error occurs
     * @throws ServletException if a servlet error occurs
     */
    public void helperForEachFilterForPut(HttpServletRequest request, HttpServletResponse response, Map<String, FieldValidator> fieldValidators, Class<?>clazz, FilterChain filterChain) throws IOException, ServletException {

        if(!request.getMethod().equals("PUT")){
            response.sendError(405, INVALID_METHOD);
        }
        if (request.getContentType() != null && request.getContentType().startsWith("application/json")) {
            String requestBody = readRequestBody(request);
            FieldValidatorAdapter fieldValidationService = new FieldValidatorAdapter(fieldValidators, clazz);
            String resultOfAllField= fieldValidationService.validateFields(ConvertMapHelper.convertStringToMap(requestBody), clazz);
            if(!"OK".equalsIgnoreCase(resultOfAllField)){
                InvalidFieldException.allInvalidField(response, resultOfAllField);
                LOGGER.info("Exit due to Invalid field {}", FilterHelper.class.getName());
            }
            else{
                HttpServletRequest wrappedRequest = new CachedBodyHttpServletRequest(request, requestBody);
                filterChain.doFilter(wrappedRequest, response);
                LOGGER.info("Exit because of all field pass");
            }

        }
        else{
            response.sendError(415, INVALID_CONTENT_TYPE);
        }
    }

    /**
     * Filter helper method for DELETE requests.
     * Validates request body fields and forwards the request if all fields are valid.
     * @param request the HTTP servlet request
     * @param response the HTTP servlet response
     * @param fieldValidators a map of field validators
     * @param clazz the class associated with the request
     * @param filterChain the filter chain
     * @throws IOException if an I/O error occurs
     * @throws ServletException if a servlet error occurs
     */
    public void helperForEachFilterForDelete(HttpServletRequest request, HttpServletResponse response, Map<String, FieldValidator> fieldValidators, Class<?>clazz, FilterChain filterChain) throws IOException, ServletException {

        if(!request.getMethod().equals("DELETE")){
            response.sendError(405, INVALID_METHOD);
        }
        if (request.getContentType() != null && request.getContentType().startsWith("application/json")) {
            String requestBody = readRequestBody(request);
            FieldValidatorAdapter fieldValidationService = new FieldValidatorAdapter(fieldValidators, clazz);
            String resultOfAllField= fieldValidationService.validateFields(ConvertMapHelper.convertStringToMap(requestBody), clazz);
            if(!"OK".equalsIgnoreCase(resultOfAllField)){
                InvalidFieldException.allInvalidField(response, resultOfAllField);
                LOGGER.info("Exit due to Invalid field {}", FilterHelper.class.getName());
            }
            else{
                HttpServletRequest wrappedRequest = new CachedBodyHttpServletRequest(request, requestBody);
                filterChain.doFilter(wrappedRequest, response);
                LOGGER.info("Exit because of all field pass");
            }

        }
        else{
            response.sendError(415, INVALID_CONTENT_TYPE);
        }
    }
}
