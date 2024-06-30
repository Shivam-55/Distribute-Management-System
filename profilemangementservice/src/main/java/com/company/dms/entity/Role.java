package com.company.dms.entity;

import com.company.dms.utils.ExcludeFromCodeCoverage;

import jakarta.persistence.*;
import lombok.*;
/**
 * Represents a user role in the system.
 */
@ExcludeFromCodeCoverage
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "role_id")
    private Long roleId;
    @Column(nullable = false)
    private String name ;
    private String description;
}