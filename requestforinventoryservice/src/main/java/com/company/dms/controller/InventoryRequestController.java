package com.company.dms.controller;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.company.dms.dto.inventorydto.InventoryRequestDto;
import com.company.dms.dto.inventorydto.InventoryRequestInputDto;
import com.company.dms.entity.InventoryRequest;
import com.company.dms.response.MessageResponse;
import com.company.dms.service.InventoryRequestService;
import com.company.dms.utils.DecodeText;
import com.company.dms.utils.logger.LoggableClass;
import com.company.dms.utils.logger.LoggableMethod;

import java.util.List;

/**
 * Controller class for managing inventory requests.
 */
@LoggableClass
@RestController
@RequestMapping("/inventory/request")
public class InventoryRequestController {
    private final InventoryRequestService inventoryRequestService;
    @Autowired
    public InventoryRequestController(InventoryRequestService inventoryRequestService){
        this.inventoryRequestService = inventoryRequestService;
    }
    /**
     * Endpoint to raise an inventory request.
     *
     * @param inventoryRequestInputDto The inventory request input data transfer object.
     * @param httpServletRequest       The HTTP servlet request.
     * @return ResponseEntity<MessageResponse<InventoryRequestDto>> The response entity containing message response and HTTP status.
     */
    @LoggableMethod
    @PostMapping("/raise")
    public ResponseEntity<MessageResponse<InventoryRequestDto>> raiseInventoryRequest(@RequestBody InventoryRequestInputDto inventoryRequestInputDto, HttpServletRequest httpServletRequest){
        Long userId = Long.valueOf(DecodeText.decryptText(httpServletRequest.getHeader("userId")));
        InventoryRequestDto fetchInventoryRequestDto = inventoryRequestService.raiseRequestForInventory(inventoryRequestInputDto,userId);
        MessageResponse<InventoryRequestDto> response = MessageResponse.<InventoryRequestDto>builder()
                .data(fetchInventoryRequestDto)
                .message("Request for inventory "+fetchInventoryRequestDto.getName()+" raised successfully\n for quantity "+fetchInventoryRequestDto.getQuantity())
                .status(true)
                .build();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @LoggableMethod
    @GetMapping("/pending/{userId}/{inventoryId}")
    public ResponseEntity<MessageResponse<InventoryRequestDto>> raisePendingInventoryRequest(@PathVariable Long userId, @PathVariable Long inventoryId){
        InventoryRequestDto fetchInventoryRequestDto = inventoryRequestService.getYourPendingRequest(userId,inventoryId);
        MessageResponse<InventoryRequestDto> response = MessageResponse.<InventoryRequestDto>builder()
                .data(fetchInventoryRequestDto)
                .message("Request for inventory "+fetchInventoryRequestDto.getName()+" fetched successfully")
                .status(true)
                .build();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    /**
     * Endpoint to get all raised requests.
     *
     * @param httpServletRequest The HTTP servlet request.
     * @return ResponseEntity<MessageResponse<List<String>>> The response entity containing message response and HTTP status.
     */
    @LoggableMethod
    @GetMapping("/allRequests")
    public ResponseEntity<MessageResponse<List<String>>> getAllRaisedRequest(HttpServletRequest httpServletRequest){
        Long userId = Long.valueOf(DecodeText.decryptText(httpServletRequest.getHeader("userId")));
        List<String> requests = inventoryRequestService.getYourRequest(userId);
        MessageResponse<List<String>> response = MessageResponse.<List<String>>builder()
                .data(requests)
                .message("All Request fetched successfully")
                .status(true)
                .build();
        return new ResponseEntity<>(response,HttpStatus.OK);
    }


    /**
     * Endpoint to get all user requests.
     *
     * @param httpServletRequest The HTTP servlet request.
     * @return ResponseEntity<MessageResponse<List<InventoryRequest>>> The response entity containing message response and HTTP status.
     */
    @LoggableMethod
    @GetMapping("/showRequests")
    public ResponseEntity<MessageResponse<List<InventoryRequest>>> getAllUserRequests(HttpServletRequest httpServletRequest){
        Long userId = Long.valueOf(DecodeText.decryptText(httpServletRequest.getHeader("userId")));
        List<InventoryRequest> requests = inventoryRequestService.getAllUserRequest(userId);
        MessageResponse<List<InventoryRequest>> response = MessageResponse.<List<InventoryRequest>>builder()
                .data(requests)
                .message("All Request fetched successfully")
                .status(true)
                .build();
        return new ResponseEntity<>(response,HttpStatus.OK);
    }


    /**
     * Endpoint to cancel a request.
     *
     * @param inventoryRequestInputDto The inventory request input data transfer object.
     * @param httpServletRequest       The HTTP servlet request.
     * @return ResponseEntity<MessageResponse<InventoryRequestDto>> The response entity containing message response and HTTP status.
     */
    @LoggableMethod
    @DeleteMapping("/cancel")
    public ResponseEntity<MessageResponse<InventoryRequestDto>> cancelYourRequest(@RequestBody InventoryRequestInputDto inventoryRequestInputDto, HttpServletRequest httpServletRequest){
        Long userId = Long.valueOf(DecodeText.decryptText(httpServletRequest.getHeader("userId")));
        InventoryRequestDto inventoryRequestDto = inventoryRequestService.cancelYourRequest(inventoryRequestInputDto,userId);
        MessageResponse<InventoryRequestDto> response = MessageResponse.<InventoryRequestDto>builder()
                .data(inventoryRequestDto)
                .message("your request cancel successfully")
                .status(true)
                .build();
        return new ResponseEntity<>(response,HttpStatus.OK);
    }


    /**
     * Endpoint to update a request.
     *
     * @param inventoryRequestInputDto The inventory request input data transfer object.
     * @param httpServletRequest       The HTTP servlet request.
     * @return ResponseEntity<MessageResponse<InventoryRequestDto>> The response entity containing message response and HTTP status.
     */
    @LoggableMethod
    @PutMapping("/update")
    public ResponseEntity<MessageResponse<InventoryRequestDto>> updateYourRequest(@RequestBody InventoryRequestInputDto inventoryRequestInputDto, HttpServletRequest httpServletRequest){
        Long userId = Long.valueOf(DecodeText.decryptText(httpServletRequest.getHeader("userId")));
        InventoryRequestDto inventoryRequestDto = inventoryRequestService.updateYourRequest(inventoryRequestInputDto,userId);
        MessageResponse<InventoryRequestDto> response = MessageResponse.<InventoryRequestDto>builder()
                .data(inventoryRequestDto)
                .message("your request update successfully")
                .status(true)
                .build();
        return new ResponseEntity<>(response,HttpStatus.OK);
    }

    /**
     * Endpoint to update the status of a request.
     *
     * @param inventoryRequestDto The inventory request data transfer object.
     * @return ResponseEntity<InventoryRequestDto> The response entity containing updated inventory request data transfer object and HTTP status.
     */
    @LoggableMethod
    @PutMapping("/statusUpdate")
    public ResponseEntity<InventoryRequestDto> updateStatusForRequest(@RequestBody InventoryRequestDto inventoryRequestDto){
        InventoryRequestDto updatedRequest = inventoryRequestService.updateRequestStatus(inventoryRequestDto);
        return new ResponseEntity<>(updatedRequest,HttpStatus.OK);
    }

}
