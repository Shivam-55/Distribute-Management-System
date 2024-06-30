package com.company.dms.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.company.dms.customenum.Status;

import java.util.Date;

/**
 * Represents entity for inventory request
 */
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Builder
public class InventoryRequest {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "request_id")
    private Long requestId;
    @Column(name = "requester_user_id")

    private Long userId;   //requesterUserId

    private int quantity;
    @Column(name = "inventory_id")
    private Long inventoryId;
    private Status status;

    @Column(name = "approver_user_id")
    private Long userId2;  //approverUserId

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "create_date", nullable = false, updatable = false)
    @CreationTimestamp
    private Date createdDate;
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "update_date", nullable = false, updatable = false)
    @UpdateTimestamp
    private Date updatedAt;
}
