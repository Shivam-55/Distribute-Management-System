package com.company.complainservice.dto;

import com.company.complainservice.customeneum.Rating;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

/**
 * Data transfer object (DTO) for feedback information.
 */
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Builder
public class FeedbackDto {
    private Long feedbackProviderId;
    private String feedbackProviderName;
    private Long feedbackReceiverId;
    private String feedbackReceiverName;
    @NotNull
    private Rating rating;
    @NotNull
    @Size(min = 50, max = 255, message = "Remark length must be of length 50-255 characters")
    private String remark;
    @JsonIgnore
    public Long getFeedbackProviderId() {
        return feedbackProviderId;
    }
    @JsonProperty
    public void setFeedbackProviderId(Long feedbackProviderId) {
        this.feedbackProviderId = feedbackProviderId;
    }
    @JsonIgnore
    public Long getFeedbackReceiverId() {
        return feedbackReceiverId;
    }
    @JsonProperty
    public void setFeedbackReceiverId(Long feedbackReceiverId) {
        this.feedbackReceiverId = feedbackReceiverId;
    }
    public void setRating(String rating) {
        this.rating = Rating.fromString(rating);
    }
}
