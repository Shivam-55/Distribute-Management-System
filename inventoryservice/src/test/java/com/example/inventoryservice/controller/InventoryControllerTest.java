package com.example.inventoryservice.controller;

import com.example.inventoryservice.controller.InventoryController;
import com.example.inventoryservice.entities.Stock;
import com.example.inventoryservice.requestDto.InventoryRequestDto;
import com.example.inventoryservice.requestDto.RequestForInventoryDto;
import com.example.inventoryservice.responseDto.InventoryDto;
import com.example.inventoryservice.responseDto.InventoryResponseDto;
import com.example.inventoryservice.service.InventoryService;
import com.example.inventoryservice.response.MessageResponse;
import com.example.inventoryservice.utility.DecodeText;
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
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class InventoryControllerTest {

    @Mock
    private InventoryService inventoryService;

    @InjectMocks
    private InventoryController inventoryController;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testAddInventory() {
        // Mock HttpServletRequest
        HttpServletRequest request = mock(HttpServletRequest.class);

        String encryptedUserId = "1HBb38hUVZefjFCL3OvR+Q==";
        String encryptedUserRole ="1HBb38hUVZefjFCL3OvR+Q==";
        request.setAttribute("userId", encryptedUserId);
        request.setAttribute("userRole","admin");


        when(request.getHeader("userRole")).thenReturn(encryptedUserRole);


        // Mock Inventory Request DTO
        InventoryRequestDto inventoryRequestDto = new InventoryRequestDto();
        inventoryRequestDto.setName("Name");
        inventoryRequestDto.setPrice(30.0);
        inventoryRequestDto.setQuantity(20);

        // Populate inventoryRequestDto with dummy data

        // Mock Inventory Response DTO
        InventoryResponseDto inventoryResponseDto = new InventoryResponseDto();
        inventoryResponseDto.setName(inventoryRequestDto.getName());
        inventoryResponseDto.setPrice(inventoryRequestDto.getPrice());
        inventoryResponseDto.setQuantity(inventoryRequestDto.getQuantity());
        // Populate inventoryResponseDto with dummy data

        // Mock Inventory Service
        when(inventoryService.addInventory(any(InventoryRequestDto.class), any(String.class))).thenReturn(inventoryResponseDto);

        // Call controller method
        ResponseEntity<MessageResponse<InventoryResponseDto>> responseEntity = inventoryController.addInventory(inventoryRequestDto, request);

        // Assert
        assertNotNull(responseEntity);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertNotNull(responseEntity.getBody());
        assertEquals("Inventory added successfully", responseEntity.getBody().getMessage());
        assertTrue(responseEntity.getBody().getStatus());
        assertEquals(inventoryResponseDto, responseEntity.getBody().getData());
    }

    @Test
    public void testRemoveInventory() {
        // Mock HttpServletRequest
        HttpServletRequest request = mock(HttpServletRequest.class);
        String encryptedUserId = "1HBb38hUVZefjFCL3OvR+Q==";
        String encryptedUserRole ="1HBb38hUVZefjFCL3OvR+Q==";
        request.setAttribute("userId", encryptedUserId);
        request.setAttribute("userRole","admin");

        when(request.getHeader("userId")).thenReturn(encryptedUserId);
        when(request.getHeader("userRole")).thenReturn(encryptedUserRole);


        // Mock RequestForInventoryDto
        RequestForInventoryDto requestForInventoryDto = new RequestForInventoryDto();
        // Populate requestForInventoryDto with dummy data

        // Mock List of Stock
        List<Stock> inventoryRemovedList = new ArrayList<>();
        // Populate inventoryRemovedList with dummy data

        // Mock Inventory Service
        when(inventoryService.removeInventory(any(RequestForInventoryDto.class), any(String.class))).thenReturn(inventoryRemovedList);

        // Call controller method
        ResponseEntity<MessageResponse<List<Stock>>> responseEntity = inventoryController.removeInventory(requestForInventoryDto, request);

        // Assert
        assertNotNull(responseEntity);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertNotNull(responseEntity.getBody());
        assertEquals("Inventory removed successfully", responseEntity.getBody().getMessage());
        assertTrue(responseEntity.getBody().getStatus());
        assertEquals(inventoryRemovedList, responseEntity.getBody().getData());
    }

    @Test
    public void testGetInventoryByName() {
        // Mock Inventory DTO
        InventoryDto inventoryDto = new InventoryDto();
        // Populate inventoryDto with dummy data

        // Mock Inventory Service
        when(inventoryService.findInventoryDtoByName(any(String.class))).thenReturn(inventoryDto);

        // Call controller method
        ResponseEntity<InventoryDto> responseEntity = inventoryController.getInventoryByName("inventoryName");

        // Assert
        assertNotNull(responseEntity);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertNotNull(responseEntity.getBody());
        assertEquals(inventoryDto, responseEntity.getBody());
    }
}
