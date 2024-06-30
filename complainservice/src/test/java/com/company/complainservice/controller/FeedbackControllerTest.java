package com.company.complainservice.controller;

import jakarta.servlet.http.HttpServletRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.company.complainservice.controller.FeedbackController;
import com.company.complainservice.dto.FeedbackDto;
import com.company.complainservice.response.MessageResponse;
import com.company.complainservice.service.FeedbackService;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class FeedbackControllerTest {

    @Mock
    private FeedbackService feedbackService;

    @InjectMocks
    private FeedbackController feedbackController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void testGiveConnectionFeedback_Success() {
        // Arrange
        FeedbackDto feedbackDto = new FeedbackDto();
        // Set up feedbackDto object as needed
        HttpServletRequest request = mock(HttpServletRequest.class);

        String encryptedUserId = "1HBb38hUVZefjFCL3OvR+Q==";
        request.setAttribute("userId",encryptedUserId);

        // Mocking DecryptText method to return a valid userId and userRole
        when(request.getHeader("userId")).thenReturn(encryptedUserId);
        when(request.getHeader("userRole")).thenReturn(encryptedUserId);

        // Mocking FeedbackService fillFeedbackForm method
        when(feedbackService.fillFeedbackForm(anyLong(), anyString(), any(FeedbackDto.class))).thenReturn(feedbackDto);

        // Act
        ResponseEntity<MessageResponse<FeedbackDto>> responseEntity = feedbackController.giveConnectionFeedback(feedbackDto, request);

        // Assert
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals("Feedback filled successfully", responseEntity.getBody().getMessage());
        assertEquals(true, responseEntity.getBody().getStatus());
        assertEquals(feedbackDto, responseEntity.getBody().getData());
    }

    @Test
    void testGetAllFeedbacks_Success() {
        // Arrange
        List<FeedbackDto> feedbackDtoList = new ArrayList<>();
        // Mocking DecryptText method to return a valid userId
        HttpServletRequest request = mock(HttpServletRequest.class);

        String encryptedUserId = "1HBb38hUVZefjFCL3OvR+Q==";
        request.setAttribute("userId",encryptedUserId);

        when(request.getHeader("userId")).thenReturn(encryptedUserId);

        // Mocking FeedbackService getAllFeedbacks method
        when(feedbackService.getAllFeedbacks(anyLong())).thenReturn(feedbackDtoList);

        // Act
        ResponseEntity<MessageResponse<List<FeedbackDto>>> responseEntity = feedbackController.getAllFeedbacks(request);

        // Assert
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals("Feedback filled successfully", responseEntity.getBody().getMessage());
        assertEquals(true, responseEntity.getBody().getStatus());
        assertEquals(feedbackDtoList, responseEntity.getBody().getData());
    }
}
