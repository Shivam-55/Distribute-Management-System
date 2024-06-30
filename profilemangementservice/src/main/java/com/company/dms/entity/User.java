package com.company.dms.entity;

import com.company.dms.customenum.IsApproved;
import com.company.dms.utils.ExcludeFromCodeCoverage;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.util.Date;
/**
 * Represents a user in the system.
 */
@ExcludeFromCodeCoverage
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long userId;
    @Column(nullable = false)
    private String username;
    @Column(nullable = false)
    private String password;
    @ManyToOne(cascade = CascadeType.ALL)
    private Role role ;
    @Column(unique = true, nullable = false)
    private Long mobile;
    @Column(unique = true, nullable = false)
    private String email;
    @Column(nullable = false)
    private String area;
    private String address;
    private String pinCode;
    private IsApproved isApproved=IsApproved.NOTAPPROVED ;
    private boolean isActive=true;
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "create_date", nullable = false, updatable = false)
    @CreationTimestamp
    private Date createdAt;
    @JsonIgnore
    public String getPassword() {
        return password;
    }
    @JsonProperty
    public void setPassword(String password) {
        this.password = password;
    }
}
