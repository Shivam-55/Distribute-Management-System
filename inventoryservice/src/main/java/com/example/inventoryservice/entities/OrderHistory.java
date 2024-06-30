package com.example.inventoryservice.entities;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.util.Date;

/**
 * Entity class representing the order history.
 */
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@ToString
public class OrderHistory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "purchase_id")
    private Long purchaseId ;
    @Column(name = "user_id")
    private Long requesterUserId;
    @Column(name = "distributor_user_id")
    private Long approverUserId;
    @ManyToOne
    @JoinColumn(name = "inventory_id")
    private Inventory inventory;
    private int quantity;
    private String remark;
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "create_date", nullable = false, updatable = false)
    @CreationTimestamp
    private Date createDate;
}
