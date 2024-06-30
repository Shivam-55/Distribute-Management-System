package com.company.dms.response;

import com.company.dms.utils.ExcludeFromCodeCoverage;

import lombok.Getter;
import lombok.Setter;

/**
 * Represents an exception response containing a message and a status indicating success or failure.
 */
@ExcludeFromCodeCoverage
@Getter
@Setter
public class ExceptionResponse {
    private String message;
    private Boolean status ;
    public ExceptionResponse() {
    }
    public ExceptionResponse(String message, Boolean status) {
        this.message = message;
        this.status = status;
    }
}
