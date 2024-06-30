package com.example.inventoryservice.response;

import lombok.*;
/**
 * Represents a message response containing a message, status, and optional data.
 * @param <T> The type of data contained in the message response.
 */
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Getter
@Setter
public class MessageResponse<T> {
    private String message;
    private Boolean status;
    private T data;
}
