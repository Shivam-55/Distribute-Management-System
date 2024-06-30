package com.company.dms.service;

import com.company.dms.dto.DistributorAndRetailerDto;
import com.company.dms.entity.DistributorToRetailer;
import com.company.dms.entity.User;


/**
 * Interface for RetailerConnectionService.
 */
public interface RetailerConnectionService {
    /**
     * Method to get the connection details of a retailer.
     *
     * @param retailerId The ID of the retailer.
     * @return DistributorAndRetailerDto object containing the connection details.
     */
    DistributorAndRetailerDto getRetailerConnection(Long retailerId);

    /**
     * Method to save a distributor connection.
     *
     * @param distributorToRetailer DistributorToRetailer object containing the distributor connection information.
     */
    void saveDistributorConnection(DistributorToRetailer distributorToRetailer);

    /**
     * Method to get the distributor connection of a retailer.
     *
     * @param distributor User object representing the distributor.
     * @return DistributorToRetailer object representing the connection.
     */
    DistributorToRetailer distributorToRetailerConnection(User distributor);

    /**
     * Method to remove the distributor-retailer connection.
     *
     * @param distributor User object representing the distributor.
     */
    void removeDistributorRetailerConnection(User distributor);
}
