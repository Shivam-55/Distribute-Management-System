package com.company.complainservice.entity;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import com.company.complainservice.customeneum.Rating;

import java.util.Date;

/**
 * Entity class representing a feedback.
 */
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Builder
public class Feedback {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "feedback_id")
    private Long feedbackId;
    @Column(nullable = false)
    private Long feedbackProviderId;
    private String feedbackProviderName;
    private Long feedbackReceiverId;
    private String feedbackReceiverName;
    @Column(nullable = false)
    private Rating rating;
    private String remark;
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "create_date", nullable = false, updatable = false)
    @CreationTimestamp
    private Date createDate;
}

