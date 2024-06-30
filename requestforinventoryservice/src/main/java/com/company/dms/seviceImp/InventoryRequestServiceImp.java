package com.company.dms.seviceImp;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import com.company.dms.customenum.IsApproved;
import com.company.dms.customenum.Status;
import com.company.dms.dto.*;
import com.company.dms.dto.inventorydto.InventoryDto;
import com.company.dms.dto.inventorydto.InventoryRequestDto;
import com.company.dms.dto.inventorydto.InventoryRequestInputDto;
import com.company.dms.entity.InventoryRequest;
import com.company.dms.exception.AlreadyRequestPresentException;
import com.company.dms.exception.NoSuchRequestAvailableException;
import com.company.dms.exception.UserNotApprovedException;
import com.company.dms.otherservicecalls.InventoryServiceCall;
import com.company.dms.otherservicecalls.ProfileServiceCall;
import com.company.dms.repository.InventoryRequestRepo;
import com.company.dms.service.InventoryRequestService;
import com.company.dms.utils.logger.LoggableClass;
import com.company.dms.utils.logger.LoggableMethod;
import com.company.dms.utils.logger.NotLoggableMethod;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Service implementation class for managing inventory requests.
 */
@LoggableClass
@Service
public class InventoryRequestServiceImp implements InventoryRequestService {
    private final WebClient.Builder webClient;
    private final ModelMapper mapper;
    private final InventoryRequestRepo inventoryRequestRepo;
    private final InventoryServiceCall inventoryServiceCall;
    private final ProfileServiceCall profileServiceCall;
    @Autowired
    public InventoryRequestServiceImp(InventoryRequestRepo inventoryRequestRepo,
                                      InventoryServiceCall inventoryServiceCall,
                                      ProfileServiceCall profileServiceCall,
                                      WebClient.Builder webClient,
                                      ModelMapper mapper){
        this.inventoryRequestRepo = inventoryRequestRepo;
        this.inventoryServiceCall = inventoryServiceCall;
        this.profileServiceCall = profileServiceCall;
        this.webClient = webClient;
        this.mapper = mapper;
    }
    /**
     * Method to raise an inventory request.
     *
     * @param inventoryRequestInputDto The inventory request input data transfer object.
     * @param userId                   The user ID.
     * @return InventoryRequestDto The inventory request data transfer object.
     */
    @LoggableMethod
    @Override
    public InventoryRequestDto raiseRequestForInventory(InventoryRequestInputDto inventoryRequestInputDto, Long userId) {
        InventoryRequestDto inventoryRequestDto = new InventoryRequestDto();
        inventoryRequestDto.setName(inventoryRequestInputDto.getName());
        inventoryRequestDto.setQuantity(inventoryRequestInputDto.getQuantity());
        UserDto userDtoResponse = profileServiceCall.getUserWithId(userId);
        if(IsApproved.NOTAPPROVED.equals(userDtoResponse.getIsApproved()) || !userDtoResponse.isActive()) throw new UserNotApprovedException("You are not approved to raise request");
        InventoryRequest inventoryRequest = new InventoryRequest();
        InventoryDto inventoryDtoResponse = inventoryServiceCall.getInventoryByName(inventoryRequestDto.getName());
        boolean prevRequest = checkAlreadyRaisedRequest(userId, inventoryDtoResponse.getInventoryId());
        if(prevRequest){
            throw new AlreadyRequestPresentException("Your Request is Already present");
        }
        inventoryRequest.setInventoryId(inventoryDtoResponse.getInventoryId());
        inventoryRequest.setQuantity(inventoryRequestDto.getQuantity());
        inventoryRequest.setStatus(Status.PENDING);
        inventoryRequest.setUserId(userId);
        if(userDtoResponse.getIsApproved().equals(IsApproved.APPROVED) && userDtoResponse.isActive() && "distributor".equals(userDtoResponse.getRole().getName())){
            inventoryRequest.setUserId2(1L);
        } else if (userDtoResponse.getIsApproved().equals(IsApproved.APPROVED) && userDtoResponse.isActive() && "retailer".equals(userDtoResponse.getRole().getName())) {
            inventoryRequest.setUserId2(profileServiceCall.retailerConnection(userId).getDistributorId());
        }
        InventoryRequest newInventoryRequest=inventoryRequestRepo.save(inventoryRequest);
        return inventoryRequestToDto(newInventoryRequest,inventoryRequestDto.getName());
    }

//    think in terms of amount purchase
    /**
     * Method to check if a request is already raised.
     *
     * @param userId      The user ID.
     * @param inventoryId The inventory ID.
     * @return boolean indicating whether the request is already raised or not.
     */
    public boolean checkAlreadyRaisedRequest(Long userId, Long inventoryId){
        Optional<InventoryRequest> optionalInventoryRequest = inventoryRequestRepo.findByUserIdAndInventoryIdAndStatus(userId,inventoryId,Status.PENDING);
        return optionalInventoryRequest.isPresent() ;
    }

    /**
     * Method to get all requests made by a user.
     *
     * @param userId The user ID.
     * @return List<String> The list of requests.
     */
    @LoggableMethod
    @Override
    public List<String> getYourRequest(Long userId) {
        userExistence(userId);
        Optional<List<InventoryRequest>> optionalInventoryRequest = inventoryRequestRepo.findByUserId(userId);
        if(optionalInventoryRequest.isEmpty()) throw new NoSuchRequestAvailableException("Not any request is Raised by you");
        List<InventoryRequest> approvedRequest = new ArrayList<>();
        List<InventoryRequest> notApprovedRequest = new ArrayList<>();
        for(InventoryRequest request : optionalInventoryRequest.get()){
            if(request.getStatus()==Status.APPROVED){
                approvedRequest.add(request);
            }else{
                notApprovedRequest.add(request);
            }
        }
        List<String> allRequest = new ArrayList<>();
        allRequest.add(approvedRequest.toString());
        allRequest.add(notApprovedRequest.toString());
        return allRequest;
    }

    /**
     * Method to get all requests made by a user.
     *
     * @param userId The user ID.
     * @return List<InventoryRequest> The list of inventory requests.
     */
    @LoggableMethod
    @Override
    public List<InventoryRequest> getAllUserRequest(Long userId) {
        userExistence(userId);
        Optional<List<InventoryRequest>> requests = inventoryRequestRepo.findByUserId2(userId);
        if(requests.isEmpty()){
            throw new NoSuchRequestAvailableException("No current request");
        }
        return requests.get();
    }

    /**
     * Method to cancel a user's request.
     *
     * @param inventoryRequestInputDto The inventory request input data transfer object.
     * @param userId                   The user ID.
     * @return InventoryRequestDto The canceled inventory request data transfer object.
     */
    @LoggableMethod
    @Override
    public InventoryRequestDto cancelYourRequest(InventoryRequestInputDto inventoryRequestInputDto, Long userId) {
        userExistence(userId);
        InventoryRequestDto inventoryRequestDto = new InventoryRequestDto();
        inventoryRequestDto.setName(inventoryRequestInputDto.getName());
        inventoryRequestDto.setQuantity(inventoryRequestInputDto.getQuantity());
        InventoryDto inventoryDto = inventoryServiceCall.getInventoryByName(inventoryRequestDto.getName());
        Optional<InventoryRequest> inventoryRequest = inventoryRequestRepo.findByUserIdAndInventoryIdAndStatus(userId, inventoryDto.getInventoryId(),Status.PENDING);
        if(inventoryRequest.isEmpty()){
            throw new NoSuchRequestAvailableException("No such request is made by you");
        }
        inventoryRequestRepo.delete(inventoryRequest.get());
        return inventoryRequestToDto(inventoryRequest.get(),inventoryRequestDto.getName());
    }

    /**
     * Method to update a user's request.
     *
     * @param inventoryRequestInputDto The inventory request input data transfer object.
     * @param userId                   The user ID.
     * @return InventoryRequestDto The updated inventory request data transfer object.
     */
    @LoggableMethod
    @Override
    public InventoryRequestDto updateYourRequest(InventoryRequestInputDto inventoryRequestInputDto, Long userId) {
        userExistence(userId);
        InventoryRequestDto inventoryRequestDto = new InventoryRequestDto();
        inventoryRequestDto.setName(inventoryRequestInputDto.getName());
        inventoryRequestDto.setQuantity(inventoryRequestInputDto.getQuantity());
        InventoryDto inventoryDto = inventoryServiceCall.getInventoryByName(inventoryRequestDto.getName());
        Optional<InventoryRequest> inventoryRequest = inventoryRequestRepo.findByUserIdAndInventoryIdAndStatus(userId, inventoryDto.getInventoryId(),Status.PENDING);
        if(inventoryRequest.isEmpty()){
            throw new NoSuchRequestAvailableException("No such request is made by you");
        }
        inventoryRequestRepo.delete(inventoryRequest.get());
        inventoryRequest.get().setQuantity(inventoryRequestDto.getQuantity());
        inventoryRequestRepo.save(inventoryRequest.get());
        return inventoryRequestToDto(inventoryRequest.get(),inventoryRequestDto.getName());
    }

    /**
     * Method to update the status of a request.
     *
     * @param inventoryRequestDto The inventory request data transfer object.
     * @return InventoryRequestDto The updated inventory request data transfer object.
     */
    @LoggableMethod
    @Override
    public InventoryRequestDto updateRequestStatus(InventoryRequestDto inventoryRequestDto) {
        Optional<InventoryRequest> optionalRequest = inventoryRequestRepo.findByUserIdAndInventoryIdAndStatusAndQuantity(
                inventoryRequestDto.getRequesterUserId(),
                inventoryRequestDto.getInventoryId(),
                Status.PENDING,
                inventoryRequestDto.getQuantity()
        );
        // Check if the request exists
        if (optionalRequest.isEmpty()) {
            throw new NoSuchRequestAvailableException("No such request is made");
        }
        InventoryRequest request = optionalRequest.get();
        request.setStatus(Status.APPROVED);
        request.setUserId2(inventoryRequestDto.getApproverUserId());

        // Save the updated request
        InventoryRequest updatedRequest = inventoryRequestRepo.save(request);

        // Convert the updated request to DTO
        return inventoryRequestToDto(updatedRequest, inventoryRequestDto.getName());
    }

    @LoggableMethod
    @Override
    public InventoryRequestDto getYourPendingRequest(Long requesterId, Long inventoryId) {
        Optional<InventoryRequest> inventoryRequest = inventoryRequestRepo.findByUserIdAndInventoryIdAndStatus(requesterId,inventoryId,Status.PENDING);
        if(inventoryRequest.isEmpty()) throw  new NoSuchRequestAvailableException("No such request is raised");
        return inventoryRequestsToDto(inventoryRequest.get());
    }

    /**
     * Method to check user existence and approval status.
     *
     * @param userId The user ID.
     */
    @LoggableMethod
    private void userExistence(Long userId){
        UserDto userDto = profileServiceCall.getUserWithId(userId);
        if(!"admin".equalsIgnoreCase(userDto.getRole().getName()) || !userDto.isActive() || IsApproved.NOTAPPROVED.equals(userDto.getIsApproved())){
            throw new UserNotApprovedException("You are not active/approved user of this organisation");
        }
    }

    //    InventoryRequest to InventoryRequestDto
    /**
     * Method to convert InventoryRequest to InventoryRequestDto.
     *
     * @param inventoryRequest The inventory request entity.
     * @param inventoryName    The name of the inventory.
     * @return InventoryRequestDto The inventory request data transfer object.
     */
    @NotLoggableMethod
    private InventoryRequestDto inventoryRequestToDto(InventoryRequest inventoryRequest, String inventoryName) {
        InventoryRequestDto inventoryRequestDto =  this.mapper.map(inventoryRequest, InventoryRequestDto.class);
        inventoryRequestDto.setName(inventoryName);
        return inventoryRequestDto;
    }
    // InventoryRequestDto to InventoryRequest

    /**
     * Method to convert InventoryRequestDto to InventoryRequest.
     *
     * @param inventoryRequestDto
     * @return
     */
    @NotLoggableMethod
    private InventoryRequest dtoToinventoryRequest(InventoryRequestDto inventoryRequestDto) {
        return this.mapper.map(inventoryRequestDto, InventoryRequest.class);
    }

    @NotLoggableMethod
    private InventoryRequestDto inventoryRequestsToDto(InventoryRequest inventoryRequest) {
        return this.mapper.map(inventoryRequest, InventoryRequestDto.class);
    }
}