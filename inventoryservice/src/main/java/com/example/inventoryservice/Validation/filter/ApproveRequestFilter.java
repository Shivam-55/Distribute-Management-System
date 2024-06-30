package com.example.inventoryservice.Validation.filter;

import com.example.inventoryservice.requestDto.RequestForInventoryDto;
import com.example.inventoryservice.Validation.AllFieldValidation;
import com.example.inventoryservice.Validation.FilterHelper;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
/**
 * Filter class for approving requests.
 */
public class ApproveRequestFilter implements Filter {
    private static final Logger logger = LoggerFactory.getLogger(ApproveRequestFilter.class);
    /**
     * Init method to initialise the filter
     * @param filterConfig The configuration information associated with the filter instance being initialised
     *
     * @throws ServletException
     */
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        Filter.super.init(filterConfig);
    }
    /**
     * Filters the request to approve a request.
     * @param servletRequest the servlet request
     * @param servletResponse the servlet response
     * @param filterChain the filter chain
     * @throws IOException if an I/O error occurs
     * @throws ServletException if a servlet exception occurs
     */
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        logger.info("Enter Filter {}", ApproveRequestFilter.class.getName());
        HttpServletRequest request=(HttpServletRequest)servletRequest;
        HttpServletResponse response=(HttpServletResponse) servletResponse;
        FilterHelper filterHelper = new FilterHelper();
        filterHelper.helperForEachFilterForPost(request, response, AllFieldValidation.fieldValidation(), RequestForInventoryDto.class, filterChain);
    }
    /**
     * destroy method to destroy the filter
     */
    @Override
    public void destroy() {
        Filter.super.destroy();
    }
}
