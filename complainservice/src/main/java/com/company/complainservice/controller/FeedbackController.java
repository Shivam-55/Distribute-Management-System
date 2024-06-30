package com.company.complainservice.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.company.complainservice.dto.FeedbackDto;
import com.company.complainservice.response.MessageResponse;
import com.company.complainservice.service.FeedbackService;
import com.company.complainservice.utils.DecodeText;
import com.company.complainservice.utils.logger.LoggableClass;
import com.company.complainservice.utils.logger.LoggableMethod;

import java.util.List;

/**
 * Controller class for handling feedback related operations.
 */
@LoggableClass
@RestController
@RequestMapping("connection")
public class FeedbackController {
    private final FeedbackService feedbackService;
    @Autowired
    public FeedbackController(FeedbackService feedbackService){
        this.feedbackService = feedbackService;
    }

    /**
     * Endpoint to give feedback on a connection.
     *
     * @param feedbackDto        The feedback DTO object.
     * @param httpServletRequest The HTTP servlet request.
     * @return ResponseEntity<MessageResponse<FeedbackDto>> representing the response.
     */
    @LoggableMethod
    @PostMapping("/feedback")
    public ResponseEntity<MessageResponse<FeedbackDto>> giveConnectionFeedback(@Valid @RequestBody FeedbackDto feedbackDto, HttpServletRequest httpServletRequest){
        Long userId = Long.valueOf(DecodeText.decryptText(httpServletRequest.getHeader("userId")));
        String role = DecodeText.decryptText(httpServletRequest.getHeader("userRole"));
        FeedbackDto filledFeedback = feedbackService.fillFeedbackForm(userId,role,feedbackDto);
        MessageResponse<FeedbackDto> response = MessageResponse.<FeedbackDto>builder()
                .data(filledFeedback)
                .message("Feedback filled successfully")
                .status(true)
                .build();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    /**
     * Endpoint to get all feedbacks for a user.
     *
     * @param httpServletRequest The HTTP servlet request.
     * @return ResponseEntity<MessageResponse<List<FeedbackDto>>> representing the response.
     */
    @LoggableMethod
    @GetMapping("/get/feedback")
    public ResponseEntity<MessageResponse<List<FeedbackDto>>> getAllFeedbacks(HttpServletRequest httpServletRequest){
        Long userId = Long.valueOf(DecodeText.decryptText(httpServletRequest.getHeader("userId")));
        List<FeedbackDto> feedbackDtoList = feedbackService.getAllFeedbacks(userId);
        MessageResponse<List<FeedbackDto>> response = MessageResponse.<List<FeedbackDto>>builder()
                .data(feedbackDtoList)
                .message("Feedback filled successfully")
                .status(true)
                .build();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
