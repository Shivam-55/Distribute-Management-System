package com.company.dms.validation.filter;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.company.dms.dto.forgotpassworddto.EmailDto;
import com.company.dms.validation.AllFieldValidation;
import com.company.dms.validation.FilterHelper;

import java.io.IOException;
/**
 * Filter for handling forgot password requests.
 */
public class ForgotPasswordFilter implements Filter {
    private static final Logger logger = LoggerFactory.getLogger(ForgotPasswordFilter.class);

    /**
     * Initialisation method for filter
     * @param filterConfig The configuration information associated with the filter instance being initialised
     *
     * @throws ServletException
     */
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        Filter.super.init(filterConfig);
    }
    /**
     * Performs the filtering logic for forgot password requests.
     *
     * @param servletRequest  The servlet request object.
     * @param servletResponse The servlet response object.
     * @param filterChain     The filter chain to continue the request processing.
     * @throws IOException      If an I/O error occurs during the filtering process.
     * @throws ServletException If a servlet exception occurs during the filtering process.
     */
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        logger.info("Enter Filter {}", ForgotPasswordFilter.class.getName());
        HttpServletRequest request=(HttpServletRequest)servletRequest;
        HttpServletResponse response=(HttpServletResponse) servletResponse;
        FilterHelper filterHelper = new FilterHelper();
        filterHelper.helperForEachFilterForPost(request, response, AllFieldValidation.fieldValidation(), EmailDto.class, filterChain);
    }

    /**
     * destroy method for filter
     */
    @Override
    public void destroy() {
        Filter.super.destroy();
    }
}
