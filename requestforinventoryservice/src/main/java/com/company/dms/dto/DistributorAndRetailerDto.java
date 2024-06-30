package com.company.dms.dto;

import lombok.*;
/**
 * Represents distributor and retailer dto
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
