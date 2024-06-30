package com.company.complainservice.dto;

import lombok.*;

/**
 * Data transfer object (DTO) for distributor and retailer information.
 */
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Builder
public class DistributorAndRetailerDto {
    private Long distributorId;
    private String distributorName;
    private Long retailerId;
    private String retailerName;
}
