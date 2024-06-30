package com.company.complainservice.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import com.company.complainservice.customeneum.FeedbackFlag;

import java.util.Date;

/**
 * Entity class representing a complaint.
 */
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Builder
public class Complain {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long complainId;
    @Column(nullable = false)
    private Long complainerId;
    private String complainerName;
    @Column(nullable = false)
    private Long complaintSubjectId;
    private String complaintSubjectName;
    @Column(nullable = false)
    private FeedbackFlag feedbackFlag;
    @Column(nullable = false)
    private String remark;
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "create_date", nullable = false, updatable = false)
    @CreationTimestamp
    private Date createDate;
}
