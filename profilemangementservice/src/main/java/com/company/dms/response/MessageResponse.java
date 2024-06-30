package com.company.dms.response;

import com.company.dms.utils.ExcludeFromCodeCoverage;

import lombok.*;
/**
 * Represents a message response containing a message, status, and optional data.
 * @param <T> The type of data contained in the message response.
 */
@ExcludeFromCodeCoverage
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class MessageResponse<T> {
    private String message;
    private Boolean status;
    private T data;
}
