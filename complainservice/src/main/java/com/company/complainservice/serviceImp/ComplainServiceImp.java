package com.company.complainservice.serviceImp;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.company.complainservice.customeneum.IsApproved;
import com.company.complainservice.dto.ComplainDto;
import com.company.complainservice.dto.DistributorAndRetailerDto;
import com.company.complainservice.dto.UserDto;
import com.company.complainservice.entity.Complain;
import com.company.complainservice.exception.NoComplainReceivedException;
import com.company.complainservice.exception.UserNotApprovedException;
import com.company.complainservice.otherservicecall.ProfileServiceCall;
import com.company.complainservice.repository.ComplainRepo;
import com.company.complainservice.service.ComplainService;
import com.company.complainservice.utils.logger.LoggableClass;
import com.company.complainservice.utils.logger.LoggableMethod;

import java.util.ArrayList;
import java.util.List;

/**
 * Implementation of the {@link ComplainService} interface for managing complaints.
 */
@LoggableClass
@Service
public class ComplainServiceImp implements ComplainService {
    private final ModelMapper mapper;
    private final ComplainRepo complainRepo;
    private final ProfileServiceCall serviceCall;
    @Autowired
    public ComplainServiceImp(ComplainRepo complainRepo, ProfileServiceCall serviceCall,
                              ModelMapper mapper){
        this.complainRepo = complainRepo;
        this.serviceCall = serviceCall;
        this.mapper = mapper;
    }

    /**
     * Fills a complain form for the given retailer ID with the provided complain DTO.
     * @param retailerId The ID of the retailer filling the complain form.
     * @param complainDto The complain DTO containing complain details.
     * @return The filled complain DTO.
     * @throws UserNotApprovedException If the user is not approved or active in the organization.
     */
    @LoggableMethod
    @Override
    public ComplainDto fillComplainForm(Long retailerId, ComplainDto complainDto) {
        UserDto userDto = serviceCall.getUserWithId(retailerId);
        if(!userDto.isActive() && IsApproved.NOTAPPROVED.equals(userDto.getIsApproved())){
            throw new UserNotApprovedException("You are not active/approved user of this organisation");
        }
        Complain complain = dtoToComplain(complainDto);
        DistributorAndRetailerDto distributorAndRetailerDto = serviceCall.retailerConnection(retailerId);
        complain.setComplainerId(retailerId);
        complain.setComplainerName(distributorAndRetailerDto.getRetailerName());
        complain.setComplaintSubjectId(distributorAndRetailerDto.getDistributorId());
        complain.setComplaintSubjectName(distributorAndRetailerDto.getDistributorName());
        Complain filledComplain = complainRepo.save(complain);
        return complainToDto(filledComplain);
    }

    /**
     * Retrieves all complains.
     * @return A list of complain DTOs representing all complains.
     * @throws NoComplainReceivedException If no complains are received.
     */
    @LoggableMethod
    @Override
    public List<ComplainDto> getAllComplains() {
        List<Complain> complainList = complainRepo.findAll();
        if(complainList.isEmpty()) throw new NoComplainReceivedException("No complain received");
        List<ComplainDto> complainDtoList = new ArrayList<>();
        for(Complain complain : complainList){
            complainDtoList.add(complainToDto(complain));
        }
        return complainDtoList;
    }

    //    complain to complainDto
    public ComplainDto complainToDto(Complain complain) {
        return this.mapper.map(complain, ComplainDto.class);
    }

    // complainDto to complain
    public Complain dtoToComplain(ComplainDto complainDto) {
        return this.mapper.map(complainDto, Complain.class);
    }

}
