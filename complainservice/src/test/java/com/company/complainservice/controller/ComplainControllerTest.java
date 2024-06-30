package com.company.complainservice.controller;

import jakarta.servlet.http.HttpServletRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.company.complainservice.controller.ComplainController;
import com.company.complainservice.dto.ComplainDto;
import com.company.complainservice.response.MessageResponse;
import com.company.complainservice.service.ComplainService;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class ComplainControllerTest {

    @Mock
    private ComplainService complainService;

    @InjectMocks
    private ComplainController complainController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void testFillComplainForm_Success() {
        // Arrange
        ComplainDto complainDto = new ComplainDto();
        HttpServletRequest request = mock(HttpServletRequest.class);

        String encryptedUserId = "1HBb38hUVZefjFCL3OvR+Q==";
        request.setAttribute("userId", encryptedUserId);

        when(request.getHeader("userId")).thenReturn(encryptedUserId);

        // Mocking ComplainService fillComplainForm method
        when(complainService.fillComplainForm(anyLong(), any(ComplainDto.class))).thenReturn(complainDto);

        // Act
        ResponseEntity<MessageResponse<ComplainDto>> responseEntity = complainController.fillComplainForm(complainDto, request);

        // Assert
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals("complain filled successfully", responseEntity.getBody().getMessage());
        assertEquals(true, responseEntity.getBody().getStatus());
        assertEquals(complainDto, responseEntity.getBody().getData());
    }

    @Test
    void testGetAllComplains_Success() {
        // Arrange
        List<ComplainDto> complainDtoList = new ArrayList<>();
        HttpServletRequest request = mock(HttpServletRequest.class);

        // Mocking DecryptText method to return a valid userId
        String encryptedUserId = "1HBb38hUVZefjFCL3OvR+Q==";
        request.setAttribute("userId", encryptedUserId);
        when(request.getHeader("userId")).thenReturn(encryptedUserId);

        // Mocking ComplainService getAllComplains method
        when(complainService.getAllComplains()).thenReturn(complainDtoList);

        // Act
        ResponseEntity<MessageResponse<List<ComplainDto>>> responseEntity = complainController.getAllComplains();

        // Assert
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals("complain filled successfully", responseEntity.getBody().getMessage());
        assertEquals(true, responseEntity.getBody().getStatus());
        assertEquals(complainDtoList, responseEntity.getBody().getData());
    }
}
