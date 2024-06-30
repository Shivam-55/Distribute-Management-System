package com.company.complainservice.serviceImp;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ConfigurationException;
import org.modelmapper.MappingException;
import org.modelmapper.ModelMapper;
import org.springframework.dao.OptimisticLockingFailureException;

import com.company.complainservice.customeneum.IsApproved;
import com.company.complainservice.dto.ComplainDto;
import com.company.complainservice.dto.DistributorAndRetailerDto;
import com.company.complainservice.dto.UserDto;
import com.company.complainservice.entity.Complain;
import com.company.complainservice.exception.NoComplainReceivedException;
import com.company.complainservice.otherservicecall.ProfileServiceCall;
import com.company.complainservice.repository.ComplainRepo;
import com.company.complainservice.serviceImp.ComplainServiceImp;

import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ComplainServiceImpTest {

    @Mock
    private ComplainRepo mockComplainRepo;
    @Mock
    private ProfileServiceCall mockServiceCall;
    @Mock
    private ModelMapper mockMapper;

    private ComplainServiceImp complainServiceImpUnderTest;

    @BeforeEach
    void setUp() {
        complainServiceImpUnderTest = new ComplainServiceImp(mockComplainRepo, mockServiceCall, mockMapper);
    }

    @Test
    void testFillComplainForm() {
        Long retailerId = 3L;
        // Setup
        final ComplainDto complainDto = ComplainDto.builder()
                .complainerId(4L)
                .complainerId(retailerId)
                .remark("Remark").build();

        // Configure ProfileServiceCall.getUserWithId(...).
        final UserDto userDto = UserDto.builder().userId(3L)
                .isApproved(IsApproved.APPROVED)
                .isActive(true)
                .build();

        when(mockServiceCall.getUserWithId(3L)).thenReturn(userDto);
        when(complainServiceImpUnderTest.dtoToComplain(complainDto)).thenReturn(new Complain());

        // Configure ModelMapper.map(...).
        final Complain complain = Complain.builder()
                .complainerId(3L)
                .complainerName("complainerName")
                .complaintSubjectId(3L)
                .complaintSubjectName("complaintSubjectName")
                .build();

        // Configure ProfileServiceCall.retailerConnection(...).
        final DistributorAndRetailerDto distributorAndRetailerDto = DistributorAndRetailerDto.builder()
                .distributorId(3L)
                .distributorName("complaintSubjectName")
                .retailerName("complainerName")
                .build();
        when(mockServiceCall.retailerConnection(3L)).thenReturn(distributorAndRetailerDto);

        // Configure ComplainRepo.save(...).
        final Complain complain1 = Complain.builder()
                .complainerId(3L)
                .complainerName("complainerName")
                .complaintSubjectId(3L)
                .complaintSubjectName("complaintSubjectName")
                .build();
        when(mockComplainRepo.save(any(Complain.class))).thenReturn(complain1);
        when(complainServiceImpUnderTest.complainToDto(complain1)).thenReturn(complainDto);
        // Run the test
        final ComplainDto result = complainServiceImpUnderTest.fillComplainForm(3L, complainDto);

        // Verify the results
        assertEquals(complainDto.getComplainerId(), result.getComplainerId());
        assertEquals(complainDto.getComplainerName(), result.getComplainerName());
        assertEquals(complainDto.getComplaintSubjectId(), result.getComplaintSubjectId());
        assertEquals(complainDto.getComplaintSubjectName(), result.getComplaintSubjectName());
    }

//    @Test
//    void testFillComplainForm_ModelMapperThrowsConfigurationException() {
//        // Setup
//        final ComplainDto complainDto = ComplainDto.builder().build();
//
//        // Configure ProfileServiceCall.getUserWithId(...).
//        final UserDto userDto = UserDto.builder()
//                .isApproved(IsApproved.NOTAPPROVED)
//                .isActive(false)
//                .build();
//        when(mockServiceCall.getUserWithId(3L)).thenReturn(userDto);
//
//        when(mockMapper.map(any(Object.class), eq(Complain.class))).thenThrow(ConfigurationException.class);
//
//        // Run the test
//        assertThatThrownBy(() -> complainServiceImpUnderTest.fillComplainForm(3L, complainDto))
//                .isInstanceOf(ConfigurationException.class);
//    }
//
//    @Test
//    void testFillComplainForm_ModelMapperThrowsMappingException() {
//        // Setup
//        final ComplainDto complainDto = ComplainDto.builder().build();
//
//        // Configure ProfileServiceCall.getUserWithId(...).
//        final UserDto userDto = UserDto.builder()
//                .isApproved(IsApproved.NOTAPPROVED)
//                .isActive(false)
//                .build();
//        when(mockServiceCall.getUserWithId(3L)).thenReturn(userDto);
//
//        when(mockMapper.map(any(Object.class), eq(Complain.class))).thenThrow(MappingException.class);
//
//        // Run the test
//        assertThatThrownBy(() -> complainServiceImpUnderTest.fillComplainForm(3L, complainDto))
//                .isInstanceOf(MappingException.class);
//    }
//
//    @Test
//    void testFillComplainForm_ComplainRepoThrowsOptimisticLockingFailureException() {
//        // Setup
//        final ComplainDto complainDto = ComplainDto.builder().build();
//
//        // Configure ProfileServiceCall.getUserWithId(...).
//        final UserDto userDto = UserDto.builder()
//                .isApproved(IsApproved.NOTAPPROVED)
//                .isActive(false)
//                .build();
//        when(mockServiceCall.getUserWithId(3L)).thenReturn(userDto);
//
//        // Configure ModelMapper.map(...).
//        final Complain complain = Complain.builder()
//                .complainerId(3L)
//                .complainerName("complainerName")
//                .complaintSubjectId(3L)
//                .complaintSubjectName("complaintSubjectName")
//                .build();
//        when(mockMapper.map(any(Object.class), eq(Complain.class))).thenReturn(complain);
//
//        // Configure ProfileServiceCall.retailerConnection(...).
//        final DistributorAndRetailerDto distributorAndRetailerDto = DistributorAndRetailerDto.builder()
//                .distributorId(3L)
//                .distributorName("complaintSubjectName")
//                .retailerName("complainerName")
//                .build();
//        when(mockServiceCall.retailerConnection(3L)).thenReturn(distributorAndRetailerDto);
//
//        when(mockComplainRepo.save(any(Complain.class))).thenThrow(OptimisticLockingFailureException.class);
//
//        // Run the test
//        assertThatThrownBy(() -> complainServiceImpUnderTest.fillComplainForm(3L, complainDto))
//                .isInstanceOf(OptimisticLockingFailureException.class);
//    }

    @Test
    void testGetAllComplains() {
        // Setup
        // Configure ComplainRepo.findAll(...).
        final List<Complain> complains = List.of(Complain.builder()
                .complainerId(3L)
                .complainerName("complainerName")
                .complaintSubjectId(3L)
                .complaintSubjectName("complaintSubjectName")
                .build());
        when(mockComplainRepo.findAll()).thenReturn(complains);

        when(mockMapper.map(any(Object.class), eq(ComplainDto.class))).thenReturn(ComplainDto.builder().build());

        // Run the test
        final List<ComplainDto> result = complainServiceImpUnderTest.getAllComplains();

        // Verify the results
    }

    @Test
    void testGetAllComplains_ComplainRepoReturnsNoItems() {
        // Setup
        when(mockComplainRepo.findAll()).thenReturn(Collections.emptyList());

        // Run the test
        assertThatThrownBy(() -> complainServiceImpUnderTest.getAllComplains())
                .isInstanceOf(NoComplainReceivedException.class);
    }

    @Test
    void testGetAllComplains_ModelMapperThrowsConfigurationException() {
        // Setup
        // Configure ComplainRepo.findAll(...).
        final List<Complain> complains = List.of(Complain.builder()
                .complainerId(3L)
                .complainerName("complainerName")
                .complaintSubjectId(3L)
                .complaintSubjectName("complaintSubjectName")
                .build());
        when(mockComplainRepo.findAll()).thenReturn(complains);

        when(mockMapper.map(any(Object.class), eq(ComplainDto.class))).thenThrow(ConfigurationException.class);

        // Run the test
        assertThatThrownBy(() -> complainServiceImpUnderTest.getAllComplains())
                .isInstanceOf(ConfigurationException.class);
    }

    @Test
    void testGetAllComplains_ModelMapperThrowsMappingException() {
        // Setup
        // Configure ComplainRepo.findAll(...).
        final List<Complain> complains = List.of(Complain.builder()
                .complainerId(3L)
                .complainerName("complainerName")
                .complaintSubjectId(3L)
                .complaintSubjectName("complaintSubjectName")
                .build());
        when(mockComplainRepo.findAll()).thenReturn(complains);

        when(mockMapper.map(any(Object.class), eq(ComplainDto.class))).thenThrow(MappingException.class);

        // Run the test
        assertThatThrownBy(() -> complainServiceImpUnderTest.getAllComplains()).isInstanceOf(MappingException.class);
    }

    @Test
    void testComplainToDto() {
        // Setup
        final Complain complain = Complain.builder()
                .complainerId(3L)
                .complainerName("complainerName")
                .complaintSubjectId(3L)
                .complaintSubjectName("complaintSubjectName")
                .build();
        when(mockMapper.map(any(Object.class), eq(ComplainDto.class))).thenReturn(ComplainDto.builder().build());

        // Run the test
        final ComplainDto result = complainServiceImpUnderTest.complainToDto(complain);

        // Verify the results
    }

    @Test
    void testComplainToDto_ModelMapperThrowsConfigurationException() {
        // Setup
        final Complain complain = Complain.builder()
                .complainerId(3L)
                .complainerName("complainerName")
                .complaintSubjectId(3L)
                .complaintSubjectName("complaintSubjectName")
                .build();
        when(mockMapper.map(any(Object.class), eq(ComplainDto.class))).thenThrow(ConfigurationException.class);

        // Run the test
        assertThatThrownBy(() -> complainServiceImpUnderTest.complainToDto(complain))
                .isInstanceOf(ConfigurationException.class);
    }

    @Test
    void testComplainToDto_ModelMapperThrowsMappingException() {
        // Setup
        final Complain complain = Complain.builder()
                .complainerId(3L)
                .complainerName("complainerName")
                .complaintSubjectId(3L)
                .complaintSubjectName("complaintSubjectName")
                .build();
        when(mockMapper.map(any(Object.class), eq(ComplainDto.class))).thenThrow(MappingException.class);

        // Run the test
        assertThatThrownBy(() -> complainServiceImpUnderTest.complainToDto(complain))
                .isInstanceOf(MappingException.class);
    }

    @Test
    void testDtoToComplain() {
        // Setup
        final ComplainDto complainDto = ComplainDto.builder().build();

        // Configure ModelMapper.map(...).
        final Complain complain = Complain.builder()
                .complainerId(3L)
                .complainerName("complainerName")
                .complaintSubjectId(3L)
                .complaintSubjectName("complaintSubjectName")
                .build();
        when(mockMapper.map(any(Object.class), eq(Complain.class))).thenReturn(complain);

        // Run the test
        final Complain result = complainServiceImpUnderTest.dtoToComplain(complainDto);

        // Verify the results
    }

    @Test
    void testDtoToComplain_ModelMapperThrowsConfigurationException() {
        // Setup
        final ComplainDto complainDto = ComplainDto.builder().build();
        when(mockMapper.map(any(Object.class), eq(Complain.class))).thenThrow(ConfigurationException.class);

        // Run the test
        assertThatThrownBy(() -> complainServiceImpUnderTest.dtoToComplain(complainDto))
                .isInstanceOf(ConfigurationException.class);
    }

    @Test
    void testDtoToComplain_ModelMapperThrowsMappingException() {
        // Setup
        final ComplainDto complainDto = ComplainDto.builder().build();
        when(mockMapper.map(any(Object.class), eq(Complain.class))).thenThrow(MappingException.class);

        // Run the test
        assertThatThrownBy(() -> complainServiceImpUnderTest.dtoToComplain(complainDto))
                .isInstanceOf(MappingException.class);
    }
}
