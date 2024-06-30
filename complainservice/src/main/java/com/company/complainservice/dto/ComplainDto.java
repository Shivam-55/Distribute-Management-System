package com.company.complainservice.dto;

import com.company.complainservice.customeneum.FeedbackFlag;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.validation.constraints.Size;
import lombok.*;

/**
 * Data transfer object (DTO) for complaints.
 */
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Builder
public class ComplainDto {
    private Long complainerId;
    private String complainerName;
    private Long complaintSubjectId;
    private String complaintSubjectName;
    private FeedbackFlag feedbackFlag;

    @Size(min = 50, max = 250, message = "Remark must not be Empty")
    private String remark;

    @JsonIgnore
    public Long getComplainerId() {
        return complainerId;
    }
    @JsonProperty
    public void setComplainerId(Long complainerId) {
        this.complainerId = complainerId;
    }
    @JsonIgnore
    public Long getComplaintSubjectId() {
        return complaintSubjectId;
    }
    @JsonProperty
    public void setComplaintSubjectId(Long complaintSubjectId) {
        this.complaintSubjectId = complaintSubjectId;
    }

}
