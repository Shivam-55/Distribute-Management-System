package com.example.inventoryservice.schedular;

import com.example.inventoryservice.entities.Stock;
import com.example.inventoryservice.requestDto.NotificationDto;
import com.example.inventoryservice.requestDto.UserDto;
import com.example.inventoryservice.service.StockAvailableService;
import com.example.inventoryservice.otherservicecall.NotificationService;
import com.example.inventoryservice.otherservicecall.ProfileManagementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.logging.Logger;

/**
 * Scheduler class for checking stock availability and sending notifications.
 */
@Component
public class StockSchedular {
    private final Logger logger = Logger.getLogger(StockSchedular.class.getName());
    private final ProfileManagementService profileManagementService;
    private final StockAvailableService stockAvailableService;
    private final NotificationService notificationService;
    @Autowired
    public StockSchedular(ProfileManagementService profileManagementService,
                          StockAvailableService stockAvailableService,
                          NotificationService notificationService){
        this.profileManagementService = profileManagementService;
        this.stockAvailableService = stockAvailableService;
        this.notificationService = notificationService;
    }

    /**
     * Scheduled method to check stock availability and send notifications.
     */
//    @Scheduled(fixedRate = 1800000)
    public void checkStockAvailability(){
        List<UserDto> userDtoList = profileManagementService.getAllActiveApprovedUser();
        int notificationThreshold = 100;
        for(UserDto user : userDtoList){
            NotificationDto notificationDto = new NotificationDto();
            notificationDto.setSubject("Notification For Inventory Availability");
            try{
                List<Stock> stockList = stockAvailableService.viewAllStockAvailable(user.getUserId());
                StringBuilder messageBuilder = new StringBuilder();
                for (Stock stock : stockList) {
                    int quantity = stock.getQuantity();
                    if (quantity <= notificationThreshold) {
                        messageBuilder.append("\nYour Stock of Inventory ").append(stock.getInventory().getName())
                                .append(" is about to exhaust.\nCurrent quantity is ").append(quantity).append("\n");
                    }
                }
                String message = messageBuilder.toString().trim();
                if (!message.isEmpty()) {
                    notificationDto.setMessage(message);
                    notificationService.sendNotification(user.getEmail(), notificationDto);
                }
            }catch (Exception e){
                logger.warning(e.getMessage());
                notificationDto.setMessage("Your Stock Is Empty. Raise Request For Inventory");
            }
            notificationService.sendNotification(user.getEmail(), notificationDto);
        }
    }
}
