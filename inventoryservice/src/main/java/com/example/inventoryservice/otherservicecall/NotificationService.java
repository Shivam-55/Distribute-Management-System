package com.example.inventoryservice.otherservicecall;

import com.example.inventoryservice.exception.ServiceDownException;
import com.example.inventoryservice.requestDto.NotificationDto;
import com.example.inventoryservice.utility.logger.LoggableClass;
import com.example.inventoryservice.utility.logger.LoggableMethod;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

/**
 * Service class responsible for sending notifications.
 */
@LoggableClass
@Component
public class NotificationService {
    private final WebClient.Builder webClient;
    @Autowired
    public NotificationService(WebClient.Builder webClient){
        this.webClient = webClient;
    }

    /**
     * Sends a notification.
     * @param mail the email address to send the notification to.
     * @param notificationDto the notification data.
     * @throws ServiceDownException if the notification service is down.
     */
    @LoggableMethod
    public void sendNotification(String mail, NotificationDto notificationDto){
        try{
            webClient.build().post()
                    .uri("http://localhost:8085/mail/send/{mail}",mail)
                    .bodyValue(notificationDto)
                    .retrieve()
                    .onStatus(HttpStatusCode::isError, response -> Mono.error(new RuntimeException("Gets error while sending notification calling to notification service"+response.statusCode())))
                    .bodyToMono(String.class)
                    .block();
        }catch (RuntimeException ex) {
            throw new ServiceDownException("Notification service down exception :"+ex.getLocalizedMessage());
        }
    }
}
