package com.company.dms.response;

import lombok.*;
/**
 * Represents a message response containing a message, status, and optional data.
 *
 * @param <T> The type of data included in the response.
 */
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class MessageResponse <T>{
    private String message;
    private Boolean status;
    private T data;
}
