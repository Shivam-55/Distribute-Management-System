package com.example.inventoryservice.requestDto;

import lombok.*;

/**
 * Data transfer object (DTO) representing a notification.
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@ToString
public class NotificationDto {
        private String subject;
        private String message;
}
