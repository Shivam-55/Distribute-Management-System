package com.company.dms.serviceImp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.company.dms.dto.DistributorAndRetailerDto;
import com.company.dms.dto.userdto.UserDto;
import com.company.dms.entity.DistributorToRetailer;
import com.company.dms.entity.User;
import com.company.dms.exception.ConnectionNotFoundException;
import com.company.dms.exception.NoSuchConnectionFoundException;
import com.company.dms.exception.NoSuchUserActiveException;
import com.company.dms.repository.DistributorToRetailerRepo;
import com.company.dms.service.RetailerConnectionService;
import com.company.dms.service.UserInfoService;
import com.company.dms.utils.logger.LoggableClass;
import com.company.dms.utils.logger.LoggableMethod;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

/**
 * Implementation of the RetailerConnectionService interface.
 */
@LoggableClass
@Service
public class RetailerConnectionImp implements RetailerConnectionService {
    private final DistributorToRetailerRepo distributorToRetailerRepo ;
    private final UserInfoService userInfoService;
    @Autowired
    public RetailerConnectionImp(DistributorToRetailerRepo distributorToRetailerRepo,
                                 UserInfoService userInfoService){
        this.distributorToRetailerRepo = distributorToRetailerRepo;
        this.userInfoService = userInfoService;
    }
    /**
     * Retrieves the connection information for a retailer.
     *
     * @param retailerId The ID of the retailer.
     * @return DistributorAndRetailerDto The DTO containing the distributor and retailer information.
     * @throws NoSuchUserActiveException If no active retailer is found with the given ID.
     */
    @LoggableMethod
    @Override
    public DistributorAndRetailerDto getRetailerConnection(Long retailerId) {
        List<DistributorToRetailer> retailerConnection = distributorToRetailerRepo.findAll();
        DistributorAndRetailerDto distributorAndRetailerDto = new DistributorAndRetailerDto();
        for (DistributorToRetailer row : retailerConnection) {
            for (User retailer : row.getRetailers()) {
                if (Objects.equals(retailer.getUserId(), retailerId)) {
                    UserDto retailerInfo = userInfoService.getUserInfo(retailerId);
                    distributorAndRetailerDto.setRetailerId(retailerId);
                    distributorAndRetailerDto.setRetailerName(retailerInfo.getUsername());
                    distributorAndRetailerDto.setDistributorName(row.getDistributor().getUsername());
                    distributorAndRetailerDto.setDistributorId(row.getDistributor().getUserId());
                    return distributorAndRetailerDto;
                }
            }
        }
        throw new NoSuchUserActiveException("No such retailer is active");
    }

    /**
     * Saves the connection information between a distributor and retailer.
     *
     * @param distributorToRetailer The connection information.
     */
    @LoggableMethod
    @Override
    public void saveDistributorConnection(DistributorToRetailer distributorToRetailer) {
        distributorToRetailerRepo.save(distributorToRetailer);
    }

    /**
     * Retrieves the connection information between a distributor and retailer.
     *
     * @param distributor The distributor.
     * @return DistributorToRetailer The connection information.
     * @throws ConnectionNotFoundException If no connection is found between the distributor and retailer.
     */
    @LoggableMethod
    @Override
    public DistributorToRetailer distributorToRetailerConnection(User distributor) {
        return distributorToRetailerRepo.findByDistributor(distributor)
                .orElseThrow(()->new ConnectionNotFoundException("No connection found between distributor and retailer"));
    }

    /**
     * Removes the connection between a distributor and retailer.
     *
     * @param distributor The distributor.
     * @throws NoSuchConnectionFoundException If no connection is found.
     */
    @LoggableMethod
    @Override
    public void removeDistributorRetailerConnection(User distributor) {
        Optional<DistributorToRetailer> distributorToRetailer =  distributorToRetailerRepo.findByDistributor(distributor);
        if (distributorToRetailer.isEmpty()) throw new NoSuchConnectionFoundException("No such connection found");
    }

}
