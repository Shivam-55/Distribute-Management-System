package com.company.dms.validation.filter;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.company.dms.dto.inventorydto.InventoryRequestInputDto;
import com.company.dms.validation.AllFieldValidation;
import com.company.dms.validation.FilterHelper;

import java.io.IOException;

/**
 * A filter that handles canceling requests based on specified criteria.
 */

public class CancelRequestFilter implements Filter {
    private static final Logger logger = LoggerFactory.getLogger(CancelRequestFilter.class);

    /**
     * Initialization method for filter
     * @param filterConfig The configuration information associated with the filter instance being initialised
     *
     * @throws ServletException
     */
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        Filter.super.init(filterConfig);
    }
    /**
     * The main filtering logic for canceling requests.
     * @param servletRequest The request object.
     * @param servletResponse The response object.
     * @param filterChain The filter chain for further processing.
     * @throws IOException If an I/O exception occurs.
     * @throws ServletException If a servlet exception occurs.
     */
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        logger.info("Enter Filter {}", CancelRequestFilter.class.getName());
        HttpServletRequest request=(HttpServletRequest)servletRequest;
        HttpServletResponse response=(HttpServletResponse) servletResponse;
        FilterHelper filterHelper = new FilterHelper();
        filterHelper.helperForEachFilterForDelete(request, response, AllFieldValidation.fieldValidation(), InventoryRequestInputDto.class, filterChain);
    }
    /**
     * destroy method for filter
     */
    @Override
    public void destroy() {
        Filter.super.destroy();
    }
}
