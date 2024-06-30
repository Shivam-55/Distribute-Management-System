package com.company.dms.dto;

import lombok.*;

import java.util.List;

import com.company.dms.entity.User;
import com.company.dms.utils.ExcludeFromCodeCoverage;
/**
 * Data transfer object (DTO) representing information sent from a distributor to a retailer.
 */
@ExcludeFromCodeCoverage
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DistributorToRetailerDto {
    private User distributorId;
    private List<User> retailers;
}
