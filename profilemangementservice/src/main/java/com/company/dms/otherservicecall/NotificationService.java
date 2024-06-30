package com.company.dms.otherservicecall;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import com.company.dms.dto.NotificationDto;
import com.company.dms.exception.ServiceDownException;
import com.company.dms.utils.ExcludeFromCodeCoverage;
import com.company.dms.utils.logger.LoggableClass;
import com.company.dms.utils.logger.LoggableMethod;

import reactor.core.publisher.Mono;

/**
 * Service class for sending notifications.
 */
@ExcludeFromCodeCoverage
@LoggableClass
@Component
public class NotificationService {
    private final WebClient.Builder webClient;
    @Autowired
    public NotificationService(WebClient.Builder webClient){
        this.webClient = webClient;
    }
    /**
     * Sends a notification via email.
     *
     * @param mail            the recipient's email address.
     * @param notificationDto the notification content.
     */
    @LoggableMethod
    public void sendNotification(String mail, NotificationDto notificationDto){
        try{
            webClient.build().post()
                    .uri("http://localhost:8085/mail/send/{mail}",mail)
                    .bodyValue(notificationDto)
                    .retrieve()
                    .onStatus(HttpStatusCode::isError, response -> Mono.error(new RuntimeException("Gets some error while sending notification calling to notification service"+response.statusCode())))
                    .bodyToMono(String.class)
                    .block();
        }catch (RuntimeException ex) {
            throw new ServiceDownException("Notification service is down : "+ex.getMessage());
        }
    }
}
