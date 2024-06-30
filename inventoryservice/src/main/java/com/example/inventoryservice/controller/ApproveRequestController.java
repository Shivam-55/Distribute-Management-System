package com.example.inventoryservice.controller;

import com.example.inventoryservice.entities.OrderHistory;
import com.example.inventoryservice.requestDto.RequestForInventoryDto;
import com.example.inventoryservice.service.StockAvailableService;
import com.example.inventoryservice.response.MessageResponse;
import com.example.inventoryservice.utility.DecodeText;
import com.example.inventoryservice.utility.logger.LoggableClass;
import com.example.inventoryservice.utility.logger.LoggableMethod;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controller class to handle request approval endpoints.
 */
@LoggableClass
@RestController
public class ApproveRequestController {
    private final StockAvailableService stockAvailableService;
    @Autowired
    public ApproveRequestController(StockAvailableService stockAvailableService){
        this.stockAvailableService = stockAvailableService;
    }

    /**
     * Endpoint to approve a request for inventory.
     * @param inventoryRequestDto The request DTO containing inventory details.
     * @param httpServletRequest The HttpServletRequest object.
     * @return ResponseEntity containing a message response.
     */
    @LoggableMethod
    @PostMapping("/request/inProgress")
    public ResponseEntity<String> approveRequest(@RequestBody RequestForInventoryDto inventoryRequestDto, HttpServletRequest httpServletRequest){
        Long userId = Long.valueOf(DecodeText.decryptText(httpServletRequest.getHeader("userId")));
        OrderHistory orderHistory = stockAvailableService.approveRequest(userId,inventoryRequestDto);
        MessageResponse<OrderHistory> response = MessageResponse.<OrderHistory>builder()
                .data(orderHistory)
                .message("Request Approved Successfully")
                .status(true)
                .build();
        return new ResponseEntity<>("Successfully work done"+response.toString(), HttpStatus.OK);
    }
}
