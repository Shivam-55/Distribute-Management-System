package com.company.dms.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.company.dms.entity.DistributorToRetailer;
import com.company.dms.entity.User;

import java.util.Optional;


/**
 * Repository interface for managing distributor to retailer relationships.
 */
@Repository
public interface DistributorToRetailerRepo extends JpaRepository<DistributorToRetailer, Long> {
    /**
     * Retrieves the distributor to retailer relationship by distributor.
     *
     * @param distributor the distributor entity
     * @return an optional of distributor to retailer relationship
     */
    Optional<DistributorToRetailer> findByDistributor(User distributor);

    /**
     * Deletes the distributor to retailer relationship by distributor.
     *
     * @param distributor the distributor entity
     * @return an optional of the deleted distributor to retailer relationship
     */
    Optional<DistributorToRetailer> deleteByDistributor(User distributor);
}