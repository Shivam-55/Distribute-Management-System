package com.company.complainservice.service;

import java.util.List;

import com.company.complainservice.dto.FeedbackDto;


/**
 * Interface representing the service for managing feedback.
 */
public interface FeedbackService {

    /**
     * Fills a feedback form for the given user ID and role with the provided feedback DTO.
     * @param userId The ID of the user providing the feedback.
     * @param userRole The role of the user providing the feedback.
     * @param feedbackDto The feedback DTO containing feedback details.
     * @return The filled feedback DTO.
     */
    FeedbackDto fillFeedbackForm(Long userId, String userRole, FeedbackDto feedbackDto);

    /**
     * Retrieves all feedbacks associated with the given user ID.
     * @param userId The ID of the user for whom feedbacks are to be retrieved.
     * @return A list of feedback DTOs representing all feedbacks.
     */
    List<FeedbackDto> getAllFeedbacks(Long userId);
}
