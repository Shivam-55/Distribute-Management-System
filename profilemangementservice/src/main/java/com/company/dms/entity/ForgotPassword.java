package com.company.dms.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import com.company.dms.utils.ExcludeFromCodeCoverage;

import java.util.Date;
/**
 * Entity forget password Represents a request for resetting a user's password.
 */

@ExcludeFromCodeCoverage
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Builder
public class ForgotPassword {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String email;
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "create_date", nullable = false, updatable = false)
    @CreationTimestamp
    private Date date;
    private int otp;
    private boolean verified=false;
}
