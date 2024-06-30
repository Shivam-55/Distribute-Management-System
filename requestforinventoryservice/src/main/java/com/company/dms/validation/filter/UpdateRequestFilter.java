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
 * A filter that handles updating inventory requests based on specified criteria.
 */
public class UpdateRequestFilter implements Filter {
    private static final Logger logger = LoggerFactory.getLogger(UpdateRequestFilter.class);
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
     * The main filtering logic for updating inventory requests.
     * @param servletRequest The request object.
     * @param servletResponse The response object.
     * @param filterChain The filter chain for further processing.
     * @throws IOException If an I/O exception occurs.
     * @throws ServletException If a servlet exception occurs.
     */
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        logger.info("Enter Filter {}", UpdateRequestFilter.class.getName());
        HttpServletRequest request=(HttpServletRequest)servletRequest;
        HttpServletResponse response=(HttpServletResponse) servletResponse;
        FilterHelper filterHelper = new FilterHelper();
        filterHelper.helperForEachFilterForPut(request, response, AllFieldValidation.fieldValidation(), InventoryRequestInputDto.class, filterChain);
    }
    /**
     * destroy method for filter
     */
    @Override
    public void destroy() {
        Filter.super.destroy();
    }
}
