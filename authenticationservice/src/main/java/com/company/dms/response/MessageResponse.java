package com.company.dms.response;

import lombok.*;

/**
 * Represents a generic message response with optional data.
 *
 * @param <T> the type of data included in the response
 */
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class MessageResponse <T>{
    private String message;
    private Boolean status ;
    private T data;

}
