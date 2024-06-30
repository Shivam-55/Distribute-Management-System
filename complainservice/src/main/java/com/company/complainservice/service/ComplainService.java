package com.company.complainservice.service;

import java.util.List;

import com.company.complainservice.dto.ComplainDto;

/**
 * Interface representing the service for managing complaints.
 */
public interface ComplainService {

    /**
     * Fills a complaint form for the given retailer ID with the provided complain DTO.
     * @param retailerId The ID of the retailer.
     * @param complainDto The complain DTO containing complain details.
     * @return The filled complain DTO.
     */
    ComplainDto fillComplainForm(Long retailerId, ComplainDto complainDto);

    /**
     * Retrieves all complaints.
     * @return A list of complain DTOs representing all complaints.
     */
    List<ComplainDto> getAllComplains();
}
