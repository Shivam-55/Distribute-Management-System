package com.example.inventoryservice.controller;

import com.example.inventoryservice.entities.Stock;
import com.example.inventoryservice.requestDto.InventoryUpdateRetailerDto;
import com.example.inventoryservice.requestDto.StockDto;
import com.example.inventoryservice.service.StockAvailableService;
import com.example.inventoryservice.response.MessageResponse;
import com.example.inventoryservice.utility.DecodeText;
import com.example.inventoryservice.utility.logger.LoggableClass;
import com.example.inventoryservice.utility.logger.LoggableMethod;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controller class to handle stock-related endpoints.
 */
@LoggableClass
@RestController
@RequestMapping("/stock")
public class StockController {
    private static final String USER_ID = "userId" ;
    private final StockAvailableService stockService;
    @Autowired
    public StockController(StockAvailableService stockService){
        this.stockService = stockService;
    }

    /**
     * Endpoint to view all available stocks.
     * @param httpServletRequest The HttpServletRequest object.
     * @return ResponseEntity containing a message response.
     */
    @LoggableMethod
    @GetMapping("/all")
    public ResponseEntity<MessageResponse<List<Stock>>> viewAllAvailableStock(HttpServletRequest httpServletRequest){
        Long userId = Long.valueOf(DecodeText.decryptText(httpServletRequest.getHeader(USER_ID)));
        List<Stock> stocks = stockService.viewAllStockAvailable(userId);
        MessageResponse<List<Stock>> response = MessageResponse.<List<Stock>>builder()
                .data(stocks)
                .message("All available stocks fetched successfully")
                .status(true)
                .build();
        return new ResponseEntity<>(response,HttpStatus.OK);
    }

    /**
     * Endpoint to view stock by inventory name.
     * @param httpServletRequest The HttpServletRequest object.
     * @param inventoryName The name of the inventory.
     * @return ResponseEntity containing a message response.
     */
    @LoggableMethod
    @GetMapping("/all/{inventoryName}")
    public ResponseEntity<MessageResponse<StockDto>> viewStock(HttpServletRequest httpServletRequest, @PathVariable String inventoryName){
        Long userId = Long.valueOf(DecodeText.decryptText(httpServletRequest.getHeader(USER_ID)));
        StockDto stockDto = stockService.viewXStock(inventoryName,userId);
        MessageResponse<StockDto> response = MessageResponse.<StockDto>builder()
                .data(stockDto)
                .message("Stock of inventory name "+ stockDto.getInventoryName()+" fetched successfully")
                .status(true)
                .build();
        return new ResponseEntity<>(response,HttpStatus.OK);
    }


    /**
     * Endpoint to update retailer stock.
     * @param inventoryUpdateRetailerDto The request DTO for updating retailer stock.
     * @param httpServletRequest The HttpServletRequest object.
     * @return ResponseEntity containing a message response.
     */
    @LoggableMethod
    @PostMapping("/retailer/update")
    public ResponseEntity<MessageResponse<StockDto>> updateRetailerStock(InventoryUpdateRetailerDto inventoryUpdateRetailerDto,HttpServletRequest httpServletRequest){
        Long userId = Long.valueOf(DecodeText.decryptText(httpServletRequest.getHeader(USER_ID)));
        StockDto stockDto = stockService.stockUpdateForRetailer(userId,inventoryUpdateRetailerDto);
        MessageResponse<StockDto> response = MessageResponse.<StockDto>builder()
                .data(stockDto)
                .message("Stock of inventory name "+ stockDto.getInventoryName()+" updated successfully")
                .status(true)
                .build();
        return new ResponseEntity<>(response,HttpStatus.OK);
    }
}
