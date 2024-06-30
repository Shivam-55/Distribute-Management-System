package com.company.complainservice.serviceImp;

import org.modelmapper.Conditions;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.company.complainservice.customeneum.IsApproved;
import com.company.complainservice.dto.DistributorAndRetailerDto;
import com.company.complainservice.dto.FeedbackDto;
import com.company.complainservice.dto.UserDto;
import com.company.complainservice.entity.Feedback;
import com.company.complainservice.exception.NoFeedbackReceivedException;
import com.company.complainservice.exception.UserNotApprovedException;
import com.company.complainservice.otherservicecall.ProfileServiceCall;
import com.company.complainservice.repository.FeedbackRepo;
import com.company.complainservice.service.FeedbackService;
import com.company.complainservice.utils.logger.LoggableClass;
import com.company.complainservice.utils.logger.LoggableMethod;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Implementation of the {@link FeedbackService} interface for managing feedback.
 */
@LoggableClass
@Service
public class FeedbackServiceImp implements FeedbackService {
    private final ModelMapper mapper;
    private final FeedbackRepo feedbackRepo;
    private final ProfileServiceCall serviceCall;
    @Autowired
    public FeedbackServiceImp(FeedbackRepo feedbackRepo, ProfileServiceCall serviceCall,
                              ModelMapper mapper){
        this.feedbackRepo = feedbackRepo;
        this.serviceCall = serviceCall;
        this.mapper = mapper;
    }

    /**
     * Fills a feedback form for the given user ID, user role, and feedback DTO.
     * @param userId The ID of the user filling the feedback form.
     * @param userRole The role of the user filling the feedback form.
     * @param feedbackDto The feedback DTO containing feedback details.
     * @return The filled feedback DTO.
     * @throws UserNotApprovedException If the user is not approved or active in the organization.
     */
    @LoggableMethod
    @Override
    public FeedbackDto fillFeedbackForm(Long userId, String userRole, FeedbackDto feedbackDto) {
        userExistence(userId);
        feedbackDto.setFeedbackProviderId(userId);
        if("distributor".equalsIgnoreCase(userRole)) {
            UserDto userDto = serviceCall.getUserWithId(userId);
            UserDto admin = serviceCall.getUserWithId(1L);
            feedbackDto.setFeedbackReceiverId(admin.getUserId());
            feedbackDto.setFeedbackReceiverName(admin.getUsername());
            feedbackDto.setFeedbackProviderName(userDto.getUsername());
        } else if ("retailer".equalsIgnoreCase(userRole)) {
            DistributorAndRetailerDto connectionDetail = serviceCall.retailerConnection(userId);
            feedbackDto.setFeedbackReceiverId(connectionDetail.getDistributorId());
            feedbackDto.setFeedbackProviderName(connectionDetail.getRetailerName());
            feedbackDto.setFeedbackReceiverName(connectionDetail.getDistributorName());
        }
        return feedbackToDto(feedbackRepo.save(dtoToFeedback(feedbackDto)));
    }

    /**
     * Retrieves all feedbacks for the given user ID.
     * @param userId The ID of the user for whom feedbacks are retrieved.
     * @return A list of feedback DTOs representing all feedbacks for the user.
     * @throws NoFeedbackReceivedException If no feedbacks are received for the user.
     */
    @LoggableMethod
    @Override
    public List<FeedbackDto> getAllFeedbacks(Long userId) {
        userExistence(userId);
        Optional<List<Feedback>> optionalFeedbackList = feedbackRepo.findByFeedbackReceiverId(userId);
        if(optionalFeedbackList.isEmpty()) throw new NoFeedbackReceivedException("No FeedBack Received");
        List<Feedback> feedbackList = optionalFeedbackList.get();
        List<FeedbackDto> feedbackDtoList = new ArrayList<>();
        for(Feedback feedback : feedbackList){
            feedbackDtoList.add(feedbackToDto(feedback));
        }
        return feedbackDtoList;
    }


    /**
     * Checks the existence and approval status of the user.
     * @param userId The ID of the user.
     * @throws UserNotApprovedException If the user is not approved or active in the organization.
     */
    @LoggableMethod
    void userExistence(Long userId){
        UserDto userDto = serviceCall.getUserWithId(userId);
        if(!userDto.isActive() || IsApproved.NOTAPPROVED.equals(userDto.getIsApproved())){
            throw new UserNotApprovedException("You are not active/approved user of this organisation");
        }
    }

    //    feedback to feedbackDto
    public FeedbackDto feedbackToDto(Feedback feedback) {
        return this.mapper.map(feedback, FeedbackDto.class);
    }

    // feedbackDto to feedback
    public Feedback dtoToFeedback(FeedbackDto feedbackDto) {
        // Create a new instance of ModelMapper
        ModelMapper modelMapper = new ModelMapper();

        // Define the mapping configuration
        modelMapper.getConfiguration()
                .setMatchingStrategy(MatchingStrategies.STRICT)
                .setSkipNullEnabled(true)
                .setPropertyCondition(Conditions.isNotNull());

        TypeMap<FeedbackDto, Feedback> typeMap = modelMapper.createTypeMap(FeedbackDto.class, Feedback.class);
        typeMap.addMapping(FeedbackDto::getFeedbackProviderId, Feedback::setFeedbackProviderId);
        typeMap.addMapping(FeedbackDto::getFeedbackReceiverId, Feedback::setFeedbackReceiverId);

        // Perform the mapping
        return modelMapper.map(feedbackDto, Feedback.class);
    }
}
