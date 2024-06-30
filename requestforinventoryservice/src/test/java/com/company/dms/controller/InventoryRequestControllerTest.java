package com.company.dms.controller;

import jakarta.servlet.http.HttpServletRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.company.dms.controller.InventoryRequestController;
import com.company.dms.dto.inventorydto.InventoryRequestDto;
import com.company.dms.dto.inventorydto.InventoryRequestInputDto;
import com.company.dms.entity.InventoryRequest;
import com.company.dms.response.MessageResponse;
import com.company.dms.service.InventoryRequestService;
import com.company.dms.utils.DecodeText;

import java.util.ArrayList;
import java.util.List;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class InventoryRequestControllerTest {

    @Mock
    private InventoryRequestService inventoryRequestService;

    @InjectMocks
    private InventoryRequestController inventoryRequestController;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testRaiseInventoryRequest() {
        HttpServletRequest request = mock(HttpServletRequest.class);

        String encryptedUserId = "1HBb38hUVZefjFCL3OvR+Q==";
        request.setAttribute("userId", encryptedUserId);

        when(request.getHeader("userId")).thenReturn(encryptedUserId);


        InventoryRequestInputDto inputDto = new InventoryRequestInputDto();

        InventoryRequestDto requestDto = new InventoryRequestDto();

        when(inventoryRequestService.raiseRequestForInventory(any(InventoryRequestInputDto.class), any(Long.class))).thenReturn(requestDto);

        ResponseEntity<MessageResponse<InventoryRequestDto>> responseEntity = inventoryRequestController.raiseInventoryRequest(inputDto, request);

        assertNotNull(responseEntity);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertNotNull(responseEntity.getBody());
        assertEquals("Request for inventory " + requestDto.getName() + " raised successfully\n for quantity " + requestDto.getQuantity(), responseEntity.getBody().getMessage());
        assertTrue(responseEntity.getBody().getStatus());
        assertEquals(requestDto, responseEntity.getBody().getData());
    }

    @Test
    public void testRaisePendingInventoryRequest() {
        Long userId = 1L;
        Long inventoryId = 1L;
        HttpServletRequest request = mock(HttpServletRequest.class);

        String encryptedUserId = "1HBb38hUVZefjFCL3OvR+Q==";
        request.setAttribute("userId", encryptedUserId);

        when(request.getHeader("userId")).thenReturn(encryptedUserId);

        InventoryRequestDto requestDto = new InventoryRequestDto();

        when(inventoryRequestService.getYourPendingRequest(userId, inventoryId)).thenReturn(requestDto);

        ResponseEntity<MessageResponse<InventoryRequestDto>> responseEntity = inventoryRequestController.raisePendingInventoryRequest(userId, inventoryId);

        assertNotNull(responseEntity);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertNotNull(responseEntity.getBody());
        assertEquals("Request for inventory " + requestDto.getName() + " fetched successfully", responseEntity.getBody().getMessage());
        assertTrue(responseEntity.getBody().getStatus());
        assertEquals(requestDto, responseEntity.getBody().getData());
    }

    @Test
    public void testGetAllRaisedRequest() {
        HttpServletRequest request = mock(HttpServletRequest.class);

        String encryptedUserId = "1HBb38hUVZefjFCL3OvR+Q==";
        request.setAttribute("userId", encryptedUserId);

        when(request.getHeader("userId")).thenReturn(encryptedUserId);


        List<String> requests = new ArrayList<>();
        requests.add("Request 1");
        requests.add("Request 2");

        when(inventoryRequestService.getYourRequest(any(Long.class))).thenReturn(requests);

        ResponseEntity<MessageResponse<List<String>>> responseEntity = inventoryRequestController.getAllRaisedRequest(request);

        assertNotNull(responseEntity);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertNotNull(responseEntity.getBody());
        assertEquals("All Request fetched successfully", responseEntity.getBody().getMessage());
        assertTrue(responseEntity.getBody().getStatus());
        assertEquals(requests, responseEntity.getBody().getData());
    }

    @Test
    public void testGetAllUserRequests() {
        HttpServletRequest request = mock(HttpServletRequest.class);

        String encryptedUserId = "1HBb38hUVZefjFCL3OvR+Q==";
        request.setAttribute("userId", encryptedUserId);

        when(request.getHeader("userId")).thenReturn(encryptedUserId);


        List<InventoryRequest> requests = new ArrayList<>();
        InventoryRequest request1 = new InventoryRequest();
        InventoryRequest request2 = new InventoryRequest();
        requests.add(request1);
        requests.add(request2);

        when(inventoryRequestService.getAllUserRequest(any(Long.class))).thenReturn(requests);

        ResponseEntity<MessageResponse<List<InventoryRequest>>> responseEntity = inventoryRequestController.getAllUserRequests(request);

        assertNotNull(responseEntity);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertNotNull(responseEntity.getBody());
        assertEquals("All Request fetched successfully", responseEntity.getBody().getMessage());
        assertTrue(responseEntity.getBody().getStatus());
        assertEquals(requests, responseEntity.getBody().getData());
    }

    @Test
    public void testCancelYourRequest() {
        HttpServletRequest request = mock(HttpServletRequest.class);

        String encryptedUserId = "1HBb38hUVZefjFCL3OvR+Q==";
        request.setAttribute("userId", encryptedUserId);

        when(request.getHeader("userId")).thenReturn(encryptedUserId);


        InventoryRequestInputDto inputDto = new InventoryRequestInputDto();

        InventoryRequestDto requestDto = new InventoryRequestDto();

        when(inventoryRequestService.cancelYourRequest(any(InventoryRequestInputDto.class), any(Long.class))).thenReturn(requestDto);

        ResponseEntity<MessageResponse<InventoryRequestDto>> responseEntity = inventoryRequestController.cancelYourRequest(inputDto, request);

        assertNotNull(responseEntity);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertNotNull(responseEntity.getBody());
        assertEquals("your request cancel successfully", responseEntity.getBody().getMessage());
        assertTrue(responseEntity.getBody().getStatus());
        assertEquals(requestDto, responseEntity.getBody().getData());
    }

    @Test
    public void testUpdateYourRequest() {
        HttpServletRequest request = mock(HttpServletRequest.class);

        String encryptedUserId = "1HBb38hUVZefjFCL3OvR+Q==";
        request.setAttribute("userId", encryptedUserId);

        when(request.getHeader("userId")).thenReturn(encryptedUserId);

        InventoryRequestInputDto inputDto = new InventoryRequestInputDto();

        InventoryRequestDto requestDto = new InventoryRequestDto();

        when(inventoryRequestService.updateYourRequest(any(InventoryRequestInputDto.class), any(Long.class))).thenReturn(requestDto);

        ResponseEntity<MessageResponse<InventoryRequestDto>> responseEntity = inventoryRequestController.updateYourRequest(inputDto, request);

        assertNotNull(responseEntity);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertNotNull(responseEntity.getBody());
        assertEquals("your request update successfully", responseEntity.getBody().getMessage());
        assertTrue(responseEntity.getBody().getStatus());
        assertEquals(requestDto, responseEntity.getBody().getData());
    }

    @Test
    public void testUpdateStatusForRequest() {
        InventoryRequestDto requestDto = new InventoryRequestDto();
        HttpServletRequest request = mock(HttpServletRequest.class);

        String encryptedUserId = "1HBb38hUVZefjFCL3OvR+Q==";
        request.setAttribute("userId", encryptedUserId);

        when(request.getHeader("userId")).thenReturn(encryptedUserId);

        when(inventoryRequestService.updateRequestStatus(any(InventoryRequestDto.class))).thenReturn(requestDto);

        ResponseEntity<InventoryRequestDto> responseEntity = inventoryRequestController.updateStatusForRequest(requestDto);

        assertNotNull(responseEntity);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(requestDto, responseEntity.getBody());
    }
}
