package com.company.dms.dto;

import com.company.dms.utils.ExcludeFromCodeCoverage;

import lombok.*;
/**
 * Data transfer object (DTO) representing both distributor and retailer information.
 */
@ExcludeFromCodeCoverage
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
    public class DistributorAndRetailerDto {
    private Long distributorId;
    private String distributorName;
    private Long retailerId;
    private String retailerName;
}
