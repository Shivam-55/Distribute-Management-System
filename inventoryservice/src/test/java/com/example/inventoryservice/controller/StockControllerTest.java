package com.example.inventoryservice.controller;

import com.example.inventoryservice.controller.StockController;
import com.example.inventoryservice.entities.Stock;
import com.example.inventoryservice.requestDto.InventoryUpdateRetailerDto;
import com.example.inventoryservice.requestDto.StockDto;
import com.example.inventoryservice.response.MessageResponse;
import com.example.inventoryservice.service.StockAvailableService;
import jakarta.servlet.http.HttpServletRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class StockControllerTest {

    @Mock
    private StockAvailableService stockService;

    @InjectMocks
    private StockController stockController;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testViewAllAvailableStock() {
        // Mock HttpServletRequest
        HttpServletRequest request = mock(HttpServletRequest.class);

        String encryptedUserId = "1HBb38hUVZefjFCL3OvR+Q==";
        request.setAttribute("userId", encryptedUserId);

        when(request.getHeader("userId")).thenReturn(encryptedUserId);

        // Mock Stock List
        List<Stock> mockStockList = new ArrayList<>();
        // Populate mockStockList with dummy data

        // Mock Stock Service
        when(stockService.viewAllStockAvailable(any(Long.class))).thenReturn(mockStockList);

        // Call controller method
        ResponseEntity<MessageResponse<List<Stock>>> responseEntity = stockController.viewAllAvailableStock(request);

        // Assert
        assertNotNull(responseEntity);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertNotNull(responseEntity.getBody());
        assertEquals("All available stocks fetched successfully", responseEntity.getBody().getMessage());
        assertTrue(responseEntity.getBody().getStatus());
        assertEquals(mockStockList, responseEntity.getBody().getData());
    }

    @Test
    public void testViewStock() {
        // Mock HttpServletRequest
        HttpServletRequest request = mock(HttpServletRequest.class);

        String encryptedUserId = "1HBb38hUVZefjFCL3OvR+Q==";
        request.setAttribute("userId", encryptedUserId);

        when(request.getHeader("userId")).thenReturn(encryptedUserId);


        // Mock Stock DTO
        StockDto mockStockDto = new StockDto();
        // Populate mockStockDto with dummy data

        // Mock Stock Service
        when(stockService.viewXStock(any(String.class), any(Long.class))).thenReturn(mockStockDto);

        // Call controller method
        ResponseEntity<MessageResponse<StockDto>> responseEntity = stockController.viewStock(request, "inventoryName");

        // Assert
        assertNotNull(responseEntity);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertNotNull(responseEntity.getBody());
        assertEquals("Stock of inventory name null fetched successfully", responseEntity.getBody().getMessage());
        assertTrue(responseEntity.getBody().getStatus());
        assertEquals(mockStockDto, responseEntity.getBody().getData());
    }

    @Test
    public void testUpdateRetailerStock() {
        // Mock HttpServletRequest
        HttpServletRequest request = mock(HttpServletRequest.class);

        String encryptedUserId = "1HBb38hUVZefjFCL3OvR+Q==";
        request.setAttribute("userId", encryptedUserId);

        when(request.getHeader("userId")).thenReturn(encryptedUserId);


        // Mock InventoryUpdateRetailerDto
        InventoryUpdateRetailerDto mockInventoryUpdateRetailerDto = new InventoryUpdateRetailerDto();
        // Populate mockInventoryUpdateRetailerDto with dummy data

        // Mock Stock DTO
        StockDto mockStockDto = new StockDto();
        // Populate mockStockDto with dummy data

        // Mock Stock Service
        when(stockService.stockUpdateForRetailer(any(Long.class), any(InventoryUpdateRetailerDto.class))).thenReturn(mockStockDto);

        // Call controller method
        ResponseEntity<MessageResponse<StockDto>> responseEntity = stockController.updateRetailerStock(mockInventoryUpdateRetailerDto, request);

        // Assert
        assertNotNull(responseEntity);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertNotNull(responseEntity.getBody());
        assertEquals("Stock of inventory name null updated successfully", responseEntity.getBody().getMessage());
        assertTrue(responseEntity.getBody().getStatus());
        assertEquals(mockStockDto, responseEntity.getBody().getData());
    }
}
