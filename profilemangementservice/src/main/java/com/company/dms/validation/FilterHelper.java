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
 * Helper class for servlet filters to handle request validation.
 */
public class FilterHelper {
    private static final Logger logger = LoggerFactory.getLogger(FilterHelper.class);
    /**
     * Handles request validation for POST method in servlet filters.
     *
     * @param request        The HttpServletRequest object.
     * @param response       The HttpServletResponse object.
     * @param fieldValidators A map of field names and their corresponding validators.
     * @param aClass          The class representing the request body structure.
     * @param filterChain    The FilterChain object.
     * @throws IOException      If an I/O error occurs.
     * @throws ServletException If a servlet exception occurs.
     */
    public void helperForEachFilterForPost(HttpServletRequest request, HttpServletResponse response, Map<String, FieldValidator> fieldValidators, Class<?>aClass, FilterChain filterChain) throws IOException, ServletException {

        if(!request.getMethod().equals("POST")){
            response.sendError(405, "Invalid Method");
        }
        if (request.getContentType() != null && request.getContentType().startsWith("application/json")) {
            String requestBody = readRequestBody(request);
            FieldValidatorAdapter fieldValidationService = new FieldValidatorAdapter(fieldValidators, aClass);
            String resultOfAllField= fieldValidationService.validateFields(ConvertMapHelper.convertStringToMap(requestBody), aClass);
            if(!"OK".equalsIgnoreCase(resultOfAllField)){
                InvalidFieldException.allInvalidField(response, resultOfAllField);
                logger.info("Exit due to Invalid field {}", FilterHelper.class.getName());
            }
            else{
                HttpServletRequest wrappedRequest = new CachedBodyHttpServletRequest(request, requestBody);
                filterChain.doFilter(wrappedRequest, response);
                logger.info("Exit because of all field pass");
            }

        }
        else{
            response.sendError(415, "Invalid Content Type");
        }
    }
    /**
     * Handles request validation for GET method with long path variables in servlet filters.
     *
     * @param request     The HttpServletRequest object.
     * @param response    The HttpServletResponse object.
     * @param filterChain The FilterChain object.
     * @throws IOException      If an I/O error occurs.
     * @throws ServletException If a servlet exception occurs.
     */

    public void helperForEachFilterGetMethodForLong(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws IOException, ServletException {

        if(!request.getMethod().equals("GET")){
            response.sendError(405, "Invalid Method");
        }
        String requestUri = request.getRequestURI();
        String[] segments = requestUri.split("/");
        String id = segments[segments.length - 1];

        try {
            Long.parseLong(id);
        }
        catch (Exception exception)
        {
            logger.info("Exit due to Invalid field");
            response.sendError(405,"Invalid Path variable");
        }
        filterChain.doFilter(request, response);
    }
}