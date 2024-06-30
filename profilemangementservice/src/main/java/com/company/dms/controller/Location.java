package com.company.dms.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.company.dms.otherservicecall.LocationService;
import com.company.dms.utils.logger.LoggableClass;
import com.company.dms.utils.logger.LoggableMethod;

import reactor.core.publisher.Mono;

import java.util.List;
import java.util.logging.Logger;

/**
 * Controller class to handle location-related functionalities.
 */
@LoggableClass
@RestController
@RequestMapping("/location")
public class Location {
    private final Logger logger = Logger.getLogger(Location.class.getName());
    private final LocationService locationService;
    @Autowired
    public Location(LocationService locationService){
        this.locationService = locationService;
    }
    /**
     * Retrieves the city based on the provided pin code.
     *
     * @param pinCode The pin code for which the city needs to be retrieved.
     * @return ResponseEntity<String> The response entity containing the city corresponding to the pin code.
     */
    @LoggableMethod
    @GetMapping("/pincode/{pinCode}")
    public ResponseEntity<String> getLocation(@PathVariable String pinCode) {
        try {
            return new ResponseEntity<>(locationService.getCityByPincode(pinCode).block(), HttpStatus.OK);
        }catch (RuntimeException ex){
            return new ResponseEntity<>("Enter valid PinCode",HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * Retrieves the pin codes associated with the provided city.
     *
     * @param city The city for which the pin codes need to be retrieved.
     * @return Mono<ResponseEntity<List<String>>> The response entity containing the list of pin codes corresponding to the city.
     */
    @LoggableMethod
    @GetMapping("/city/{city}")
    public Mono<ResponseEntity<List<String>>> getPinCodesByCity(@PathVariable String city) {
        return locationService.getPincodesByCity(city)
                .map(pinCodes -> new ResponseEntity<>(pinCodes, HttpStatus.OK))
                .defaultIfEmpty(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
}

