package com.company.dms.serviceImp;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.dao.OptimisticLockingFailureException;

import com.company.dms.dto.DistributorAndRetailerDto;
import com.company.dms.dto.userdto.UserDto;
import com.company.dms.entity.DistributorToRetailer;
import com.company.dms.entity.User;
import com.company.dms.exception.ConnectionNotFoundException;
import com.company.dms.exception.NoSuchConnectionFoundException;
import com.company.dms.exception.NoSuchUserActiveException;
import com.company.dms.repository.DistributorToRetailerRepo;
import com.company.dms.service.UserInfoService;
import com.company.dms.serviceImp.RetailerConnectionImp;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class RetailerConnectionImpTest {

    @Mock
    private DistributorToRetailerRepo mockDistributorToRetailerRepo;
    @Mock
    private UserInfoService mockUserInfoService;

    @InjectMocks
    private RetailerConnectionImp retailerConnectionImpUnderTest;

    @Test
    void testGetRetailerConnection() {
        // Setup
        // Configure DistributorToRetailerRepo.findAll(...).
        final List<DistributorToRetailer> distributorToRetailers = List.of(DistributorToRetailer.builder()
                .distributor(User.builder()
                        .userId(0L)
                        .username("DistributorName")
                        .build())
                .retailers(List.of(User.builder()
                        .userId(0L)
                        .username("DistributorName")
                        .build()))
                .build());
        when(mockDistributorToRetailerRepo.findAll()).thenReturn(distributorToRetailers);

        // Configure UserInfoService.getUserInfo(...).
        final UserDto userDto = UserDto.builder()
                .username("RetailerName")
                .build();
        when(mockUserInfoService.getUserInfo(0L)).thenReturn(userDto);

        // Run the test
        final DistributorAndRetailerDto result = retailerConnectionImpUnderTest.getRetailerConnection(0L);

        // Verify the results
    }

    @Test
    void testGetRetailerConnection_DistributorToRetailerRepoReturnsNoItems() {
        // Setup
        when(mockDistributorToRetailerRepo.findAll()).thenReturn(Collections.emptyList());

        // Run the test
        assertThatThrownBy(() -> retailerConnectionImpUnderTest.getRetailerConnection(0L))
                .isInstanceOf(NoSuchUserActiveException.class);
    }

    @Test
    void testSaveDistributorConnection() {
        // Setup
        final DistributorToRetailer distributorToRetailer = DistributorToRetailer.builder()
                .distributor(User.builder()
                        .userId(0L)
                        .username("DistributorName")
                        .build())
                .retailers(List.of(User.builder()
                        .userId(0L)
                        .username("DistributorName")
                        .build()))
                .build();

        // Run the test
        retailerConnectionImpUnderTest.saveDistributorConnection(distributorToRetailer);

        // Verify the results
        verify(mockDistributorToRetailerRepo).save(any(DistributorToRetailer.class));
    }

    @Test
    void testSaveDistributorConnection_DistributorToRetailerRepoThrowsOptimisticLockingFailureException() {
        // Setup
        final DistributorToRetailer distributorToRetailer = DistributorToRetailer.builder()
                .distributor(User.builder()
                        .userId(0L)
                        .username("DistributorName")
                        .build())
                .retailers(List.of(User.builder()
                        .userId(0L)
                        .username("DistributorName")
                        .build()))
                .build();
        when(mockDistributorToRetailerRepo.save(any(DistributorToRetailer.class)))
                .thenThrow(OptimisticLockingFailureException.class);

        // Run the test
        assertThatThrownBy(
                () -> retailerConnectionImpUnderTest.saveDistributorConnection(distributorToRetailer))
                .isInstanceOf(OptimisticLockingFailureException.class);
    }

    @Test
    void testDistributorToRetailerConnection() {
        // Setup
        final User distributor = User.builder()
                .userId(0L)
                .username("DistributorName")
                .build();

        // Configure DistributorToRetailerRepo.findByDistributor(...).
        final Optional<DistributorToRetailer> distributorToRetailer = Optional.of(DistributorToRetailer.builder()
                .distributor(User.builder()
                        .userId(0L)
                        .username("DistributorName")
                        .build())
                .retailers(List.of(User.builder()
                        .userId(0L)
                        .username("DistributorName")
                        .build()))
                .build());
        when(mockDistributorToRetailerRepo.findByDistributor(any(User.class))).thenReturn(distributorToRetailer);

        // Run the test
        final DistributorToRetailer result = retailerConnectionImpUnderTest.distributorToRetailerConnection(
                distributor);

        // Verify the results
    }

    @Test
    void testDistributorToRetailerConnection_DistributorToRetailerRepoReturnsAbsent() {
        // Setup
        final User distributor = User.builder()
                .userId(0L)
                .username("DistributorName")
                .build();
        when(mockDistributorToRetailerRepo.findByDistributor(any(User.class))).thenReturn(Optional.empty());

        // Run the test
        assertThatThrownBy(
                () -> retailerConnectionImpUnderTest.distributorToRetailerConnection(distributor))
                .isInstanceOf(ConnectionNotFoundException.class);
    }

    @Test
    void testRemoveDistributorRetailerConnection() {
        // Setup
        final User distributor = User.builder()
                .userId(0L)
                .username("DistributorName")
                .build();

        // Configure DistributorToRetailerRepo.findByDistributor(...).
        final Optional<DistributorToRetailer> distributorToRetailer = Optional.of(DistributorToRetailer.builder()
                .distributor(User.builder()
                        .userId(0L)
                        .username("DistributorName")
                        .build())
                .retailers(List.of(User.builder()
                        .userId(0L)
                        .username("DistributorName")
                        .build()))
                .build());
        when(mockDistributorToRetailerRepo.findByDistributor(any(User.class))).thenReturn(distributorToRetailer);

        // Run the test
        retailerConnectionImpUnderTest.removeDistributorRetailerConnection(distributor);

        // Verify the results
    }

    @Test
    void testRemoveDistributorRetailerConnection_DistributorToRetailerRepoReturnsAbsent() {
        // Setup
        final User distributor = User.builder()
                .userId(0L)
                .username("DistributorName")
                .build();
        when(mockDistributorToRetailerRepo.findByDistributor(any(User.class))).thenReturn(Optional.empty());

        // Run the test
        assertThatThrownBy(
                () -> retailerConnectionImpUnderTest.removeDistributorRetailerConnection(distributor))
                .isInstanceOf(NoSuchConnectionFoundException.class);
    }
}
