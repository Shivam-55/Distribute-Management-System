package com.company.dms.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

import com.company.dms.utils.ExcludeFromCodeCoverage;
/**
 * Represents a distribution from a distributor to a retailer.
 */
@ExcludeFromCodeCoverage
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DistributorToRetailer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "distributor_id")
    private User distributor;
    @OneToMany(cascade = CascadeType.ALL)
    @Column(name = "retailer_id")
    private List<User> retailers;
}
