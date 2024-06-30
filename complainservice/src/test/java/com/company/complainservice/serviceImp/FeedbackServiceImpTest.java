package com.company.complainservice.serviceImp;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;

import com.company.complainservice.customeneum.IsApproved;
import com.company.complainservice.dto.DistributorAndRetailerDto;
import com.company.complainservice.dto.FeedbackDto;
import com.company.complainservice.dto.UserDto;
import com.company.complainservice.entity.Feedback;
import com.company.complainservice.exception.NoFeedbackReceivedException;
import com.company.complainservice.exception.UserNotApprovedException;
import com.company.complainservice.otherservicecall.ProfileServiceCall;
import com.company.complainservice.repository.FeedbackRepo;
import com.company.complainservice.serviceImp.FeedbackServiceImp;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

class FeedbackServiceImpTest {

    @Mock
    private ModelMapper mockMapper;

    @Mock
    private FeedbackRepo mockFeedbackRepo;

    @Mock
    private ProfileServiceCall mockServiceCall;

    @InjectMocks
    private FeedbackServiceImp feedbackServiceImpUnderTest;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testFillFeedbackForm_WithUserRoleAsDistributor() {
        // Setup
        final Long userId = 2L;
        final String userRole = "distributor";
        final FeedbackDto feedbackDto = FeedbackDto.builder()
                .feedbackProviderId(2L)
                .feedbackProviderName("distributorName")
                .feedbackReceiverId(1L)
                .feedbackReceiverName("admin")
                .build();

        final UserDto userDto = UserDto.builder()
                .userId(userId)
                .username("distributorName")
                .isApproved(IsApproved.APPROVED)
                .isActive(true)
                .build();
        when(mockServiceCall.getUserWithId(userId)).thenReturn(userDto);

        final UserDto admin = UserDto.builder()
                .userId(1L)
                .username("admin")
                .build();
        when(mockServiceCall.getUserWithId(1L)).thenReturn(admin);
//        feedbackDto.setFeedbackReceiverId(admin.getUserId());
//        feedbackDto.setFeedbackReceiverName(admin.getUsername());
//        feedbackDto.setFeedbackProviderName(userDto.getUsername());
        final DistributorAndRetailerDto connectionDetail = DistributorAndRetailerDto.builder()
                .distributorId(1L)
                .distributorName("distributorName")
                .retailerName("retailerName")
                .build();
        when(mockServiceCall.retailerConnection(userId)).thenReturn(connectionDetail);

        final Feedback feedback = Feedback.builder()
                .feedbackProviderId(userId)
                .feedbackReceiverId(admin.getUserId())
                .feedbackReceiverName(admin.getUsername())
                .feedbackProviderName(userDto.getUsername())
                .build();
        when(mockFeedbackRepo.save(any(Feedback.class))).thenReturn(feedback);
        when(feedbackServiceImpUnderTest.feedbackToDto(feedback)).thenReturn(feedbackDto);

        // Run the test
        final FeedbackDto result = feedbackServiceImpUnderTest.fillFeedbackForm(userId, userRole, feedbackDto);

        // Verify the results
        assertNotNull(result);
        assertEquals(userId, result.getFeedbackProviderId());
        assertEquals(admin.getUserId(), result.getFeedbackReceiverId());
        assertEquals(admin.getUsername(), result.getFeedbackReceiverName());
        assertEquals(userDto.getUsername(), result.getFeedbackProviderName());
    }

    @Test
    void testFillFeedbackForm_WithUserRoleAsRetailer() {
        // Setup
        final Long userId = 3L;
        final String userRole = "retailer";
        final FeedbackDto feedbackDto = FeedbackDto.builder()
                .feedbackProviderId(userId)
                .build();

        final UserDto userDto = UserDto.builder()
                .userId(userId)
                .username("retailerName")
                .isApproved(IsApproved.APPROVED)
                .isActive(true)
                .build();
        when(mockServiceCall.getUserWithId(userId)).thenReturn(userDto);

        final DistributorAndRetailerDto connectionDetail = DistributorAndRetailerDto.builder()
                .distributorId(2L)
                .distributorName("distributorName")
                .retailerName("retailerName")
                .build();
        when(mockServiceCall.retailerConnection(userId)).thenReturn(connectionDetail);

        final Feedback feedback = Feedback.builder()
                .feedbackProviderId(userId)
                .feedbackReceiverId(connectionDetail.getDistributorId())
                .feedbackReceiverName(connectionDetail.getDistributorName())
                .feedbackProviderName(connectionDetail.getRetailerName())
                .build();
        when(mockFeedbackRepo.save(any(Feedback.class))).thenReturn(feedback);
        when(feedbackServiceImpUnderTest.feedbackToDto(feedback)).thenReturn(feedbackDto);


        // Run the test
        final FeedbackDto result = feedbackServiceImpUnderTest.fillFeedbackForm(userId, userRole, feedbackDto);

//        // Verify the results
        assertNotNull(result);
        assertEquals(userId, result.getFeedbackProviderId());
        assertEquals(connectionDetail.getDistributorId(), result.getFeedbackReceiverId());
        assertEquals(connectionDetail.getDistributorName(), result.getFeedbackReceiverName());
        assertEquals(connectionDetail.getRetailerName(), result.getFeedbackProviderName());
    }

    @Test
    void testFillFeedbackForm_WhenUserNotApproved() {
        // Setup
        final Long userId = 0L;
        final String userRole = "distributor";
        final FeedbackDto feedbackDto = FeedbackDto.builder()
                .feedbackProviderId(userId)
                .build();

        final UserDto userDto = UserDto.builder()
                .userId(userId)
                .username("distributorName")
                .isApproved(IsApproved.NOTAPPROVED)
                .isActive(true)
                .build();
        when(mockServiceCall.getUserWithId(userId)).thenReturn(userDto);

        // Run the test and verify that UserNotApprovedException is thrown
        assertThrows(UserNotApprovedException.class, () -> {
            feedbackServiceImpUnderTest.fillFeedbackForm(userId, userRole, feedbackDto);
        });
    }

    @Test
    void testFillFeedbackForm_WhenUserNotActive() {
        // Setup
        final Long userId = 0L;
        final String userRole = "distributor";
        final FeedbackDto feedbackDto = FeedbackDto.builder()
                .feedbackProviderId(userId)
                .build();

        final UserDto userDto = UserDto.builder()
                .userId(userId)
                .username("distributorName")
                .isApproved(IsApproved.APPROVED)
                .isActive(false)
                .build();
        when(mockServiceCall.getUserWithId(userId)).thenReturn(userDto);

        // Run the test and verify that UserNotApprovedException is thrown
        assertThrows(UserNotApprovedException.class, () -> {
            feedbackServiceImpUnderTest.fillFeedbackForm(userId, userRole, feedbackDto);
        });
    }

    @Test
    void testGetAllFeedbacks_WhenNoFeedbacksReceived() {
        // Setup
        final Long userId = 0L;

        final UserDto userDto = UserDto.builder()
                .userId(userId)
                .username("distributorName")
                .isApproved(IsApproved.APPROVED)
                .isActive(true)
                .build();
        when(mockServiceCall.getUserWithId(userId)).thenReturn(userDto);

        when(mockFeedbackRepo.findByFeedbackReceiverId(userId)).thenReturn(Optional.empty());

        // Run the test and verify that NoFeedbackReceivedException is thrown
        assertThrows(NoFeedbackReceivedException.class, () -> {
            feedbackServiceImpUnderTest.getAllFeedbacks(userId);
        });
    }

    @Test
    void testGetAllFeedbacks_WhenFeedbacksReceived() {
        // Setup
        final Long userId = 0L;

        final UserDto userDto = UserDto.builder()
                .userId(userId)
                .username("distributorName")
                .isApproved(IsApproved.APPROVED)
                .isActive(true)
                .build();
        when(mockServiceCall.getUserWithId(userId)).thenReturn(userDto);

        final List<Feedback> feedbackList = new ArrayList<>();
        feedbackList.add(Feedback.builder().build());
        when(mockFeedbackRepo.findByFeedbackReceiverId(userId)).thenReturn(Optional.of(feedbackList));

        // Run the test
        final List<FeedbackDto> result = feedbackServiceImpUnderTest.getAllFeedbacks(userId);

        // Verify the results
        assertNotNull(result);
        assertEquals(feedbackList.size(), result.size());
    }

    @Test
    void testUserExistence_WhenUserIsNotApproved() {
        // Setup
        final Long userId = 0L;

        final UserDto userDto = UserDto.builder()
                .userId(userId)
                .isApproved(IsApproved.NOTAPPROVED)
                .isActive(true)
                .build();
        when(mockServiceCall.getUserWithId(userId)).thenReturn(userDto);

        // Run the test and verify that UserNotApprovedException is thrown
        assertThrows(UserNotApprovedException.class, () -> {
            feedbackServiceImpUnderTest.userExistence(userId);
        });
    }

    @Test
    void testUserExistence_WhenUserIsNotActive() {
        // Setup
        final Long userId = 0L;

        final UserDto userDto = UserDto.builder()
                .userId(userId)
                .isApproved(IsApproved.APPROVED)
                .isActive(false)
                .build();
        when(mockServiceCall.getUserWithId(userId)).thenReturn(userDto);

        // Run the test and verify that UserNotApprovedException is thrown
        assertThrows(UserNotApprovedException.class, () -> {
            feedbackServiceImpUnderTest.userExistence(userId);
        });
    }

    @Test
    void testUserExistence_WhenUserIsApprovedAndActive() {
        // Setup
        final Long userId = 0L;

        final UserDto userDto = UserDto.builder()
                .userId(userId)
                .isApproved(IsApproved.APPROVED)
                .isActive(true)
                .build();
        when(mockServiceCall.getUserWithId(userId)).thenReturn(userDto);

        // Run the test and verify that no exception is thrown
        assertDoesNotThrow(() -> {
            feedbackServiceImpUnderTest.userExistence(userId);
        });
    }
}
