package com.example.inventoryservice.controller;

import com.example.inventoryservice.entities.OrderHistory;
import com.example.inventoryservice.requestDto.RequestForInventoryDto;
import com.example.inventoryservice.service.StockAvailableService;
import com.example.inventoryservice.utility.DecodeText;
import jakarta.servlet.http.HttpServletRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class ApproveRequestControllerTest {

    @Mock
    private StockAvailableService stockAvailableService;

    @InjectMocks
    private ApproveRequestController approveRequestController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void testApproveRequest_Success() {
        // Arrange
        RequestForInventoryDto inventoryRequestDto = new RequestForInventoryDto();

        HttpServletRequest request = mock(HttpServletRequest.class);

        String encryptedUserId = "1HBb38hUVZefjFCL3OvR+Q==";
        request.setAttribute("userId", encryptedUserId);

        when(request.getHeader("userId")).thenReturn(encryptedUserId);
        // Assuming decryptText returns the same value
        OrderHistory orderHistory = new OrderHistory(); // Create a sample OrderHistory object
        when(stockAvailableService.approveRequest(any(Long.class), any(RequestForInventoryDto.class))).thenReturn(orderHistory);

        // Act
        ResponseEntity<String> responseEntity = approveRequestController.approveRequest(inventoryRequestDto, request);

        // Assert
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertNotNull(responseEntity.getBody());
        assertTrue(responseEntity.getBody().contains("Successfully work done"));
        assertTrue(responseEntity.getBody().contains("Request Approved Successfully"));
    }

    // Add more test cases for different scenarios like invalid user ID, null DTO, etc.
}
