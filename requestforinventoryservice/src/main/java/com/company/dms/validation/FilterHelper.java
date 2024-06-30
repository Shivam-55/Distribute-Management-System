package com.company.dms.validation;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.company.dms.validation.fieldvalidation.FieldValidator;
import com.company.dms.validation.fieldvalidatoradaptor.FieldValidatorAdapter;

import static com.company.dms.validation.ReadRequestBody.readRequestBody;

import java.io.IOException;
import java.util.Map;
/**
 * Helper class for handling filters related to HTTP methods (POST, GET, PUT, DELETE).
 */
public class FilterHelper {
    private static final Logger LOGGER = LoggerFactory.getLogger(FilterHelper.class);
    private static final String INVALID_CONTENT_TYPE ="Invalid Content Type";
    private static final String INVALID_METHOD = "Invalid Method" ;
    /**
     * Helper method for POST requests, validates fields and processes the filter chain.
     *
     * @param request        The HTTP servlet request.
     * @param response       The HTTP servlet response.
     * @param fieldValidators A map of field validators.
     * @param aClass          The class associated with the request body.
     * @param filterChain    The filter chain to be processed.
     * @throws IOException      If an I/O error occurs.
     * @throws ServletException If a servlet-related error occurs.
     */
    public void helperForEachFilterForPost (HttpServletRequest request, HttpServletResponse response, Map<String, FieldValidator> fieldValidators, Class<?>aClass, FilterChain filterChain) throws IOException, ServletException {

        if(!request.getMethod().equals("POST")){
            response.sendError(405, INVALID_METHOD);
        }
        if (request.getContentType() != null && request.getContentType().startsWith("application/json")) {
            String requestBody = readRequestBody(request);
            FieldValidatorAdapter fieldValidationService = new FieldValidatorAdapter(fieldValidators, aClass);
            String resultOfAllField= fieldValidationService.validateFields(ConvertMapHelper.convertStringToMap(requestBody), aClass);
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
     * Helper method for GET requests with long parameters.
     *
     * @param request     The HTTP servlet request.
     * @param response    The HTTP servlet response.
     * @param filterChain The filter chain to be processed.
     * @throws IOException      If an I/O error occurs.
     * @throws ServletException If a servlet-related error occurs.
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
     * Helper method for PUT requests, validates fields and processes the filter chain.
     *
     * @param request        The HTTP servlet request.
     * @param response       The HTTP servlet response.
     * @param fieldValidators A map of field validators.
     * @param clazz          The class associated with the request body.
     * @param filterChain    The filter chain to be processed.
     * @throws IOException      If an I/O error occurs.
     * @throws ServletException If a servlet-related error occurs.
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
                LOGGER.info("Exit due to Invalid field {}" , FilterHelper.class.getName());
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
     * Helper method for DELETE requests, validates fields and processes the filter chain.
     *
     * @param request        The HTTP servlet request.
     * @param response       The HTTP servlet response.
     * @param fieldValidators A map of field validators.
     * @param clazz          The class associated with the request body.
     * @param filterChain    The filter chain to be processed.
     * @throws IOException      If an I/O error occurs.
     * @throws ServletException If a servlet-related error occurs.
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
                LOGGER.info("Exiting due to Invalid field {}" , FilterHelper.class.getName());
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
}
