package com.example.inventoryservice.controller;

import com.example.inventoryservice.entities.Stock;
import com.example.inventoryservice.requestDto.InventoryRequestDto;
import com.example.inventoryservice.requestDto.RequestForInventoryDto;
import com.example.inventoryservice.responseDto.InventoryDto;
import com.example.inventoryservice.responseDto.InventoryResponseDto;
import com.example.inventoryservice.service.InventoryService;
import com.example.inventoryservice.response.MessageResponse;
import com.example.inventoryservice.utility.DecodeText;
import com.example.inventoryservice.utility.logger.LoggableClass;
import com.example.inventoryservice.utility.logger.LoggableMethod;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controller class to handle inventory-related endpoints.
 */
@LoggableClass
@RestController
@RequestMapping("/inventory")
public class InventoryController {
    private final InventoryService inventoryService ;
    @Autowired
    public InventoryController(InventoryService inventoryService){
        this.inventoryService = inventoryService;
    }

    /**
     * Endpoint to add inventory.
     * @param inventory The inventory request DTO.
     * @param httpServletRequest The HttpServletRequest object.
     * @return ResponseEntity containing a message response.
     */
    @LoggableMethod
    @PostMapping("/add")
    public ResponseEntity<MessageResponse<InventoryResponseDto>> addInventory(@RequestBody InventoryRequestDto inventory, HttpServletRequest httpServletRequest){
        String role = DecodeText.decryptText(httpServletRequest.getHeader("userRole"));
        InventoryResponseDto inventoryResponseDto = inventoryService.addInventory(inventory,role);
        MessageResponse<InventoryResponseDto> response = MessageResponse.<InventoryResponseDto>builder()
                .data(inventoryResponseDto)
                .message("Inventory added successfully")
                .status(true)
                .build();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    /**
     * Endpoint to remove inventory.
     * @param inventoryName The request DTO containing inventory name.
     * @param httpServletRequest The HttpServletRequest object.
     * @return ResponseEntity containing a message response.
     */
    @LoggableMethod
    @DeleteMapping("/remove")
    public ResponseEntity<MessageResponse<List<Stock>>> removeInventory(@Valid @RequestBody RequestForInventoryDto inventoryName, HttpServletRequest httpServletRequest){
        String role = DecodeText.decryptText(httpServletRequest.getHeader("userRole"));
        List<Stock> inventoryRemovedList = inventoryService.removeInventory(inventoryName,role);
        MessageResponse<List<Stock>> response = MessageResponse.<List<Stock>>builder()
                .data(inventoryRemovedList)
                .message("Inventory removed successfully")
                .status(true)
                .build();
        return new ResponseEntity<>(response,HttpStatus.OK);
    }

    /**
     * Endpoint to get inventory by name.
     * @param inventoryName The name of the inventory.
     * @return ResponseEntity containing the inventory DTO.
     */
    @LoggableMethod
    @GetMapping("/{inventoryName}")
    public ResponseEntity<InventoryDto> getInventoryByName(@PathVariable String inventoryName){
        InventoryDto inventoryDto = inventoryService.findInventoryDtoByName(inventoryName);
        return new ResponseEntity<>(inventoryDto,HttpStatus.OK);
    }
}
