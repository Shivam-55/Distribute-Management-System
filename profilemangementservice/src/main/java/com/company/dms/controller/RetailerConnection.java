package com.company.dms.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.company.dms.dto.DistributorAndRetailerDto;
import com.company.dms.service.RetailerConnectionService;
import com.company.dms.utils.logger.LoggableClass;
import com.company.dms.utils.logger.LoggableMethod;

/**
 * Controller class to handle retailer connection-related functionalities.
 */
@LoggableClass
@RestController
@RequestMapping("/user/connection")
public class RetailerConnection {
    private final RetailerConnectionService retailerConnectionService;
    @Autowired
    public RetailerConnection(RetailerConnectionService retailerConnectionService){
        this.retailerConnectionService = retailerConnectionService;
    }
    /**
     * Retrieves the connection information for the retailer with the given ID.
     *
     * @param retailerId The ID of the retailer for which the connection information needs to be retrieved.
     * @return ResponseEntity<DistributorAndRetailerDto> The response entity containing the distributor and retailer connection details.
     */
    @LoggableMethod
    @GetMapping("/{retailerId}")
    public ResponseEntity<DistributorAndRetailerDto> getRetailerConnection(@PathVariable Long retailerId){
        DistributorAndRetailerDto distributorAndRetailerDto = retailerConnectionService.getRetailerConnection(retailerId);
        return new ResponseEntity<>(distributorAndRetailerDto, HttpStatus.OK);
    }
}
