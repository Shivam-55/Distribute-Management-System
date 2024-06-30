package com.company.dms.serviceImp;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ConfigurationException;
import org.modelmapper.MappingException;
import org.modelmapper.ModelMapper;

import com.company.dms.dto.RoleDto;
import com.company.dms.entity.Role;
import com.company.dms.exception.NoSuchRolePresentException;
import com.company.dms.repository.RoleInfoRepo;
import com.company.dms.serviceImp.RoleInfoServiceImp;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class RoleInfoServiceImpTest {

    @Mock
    private RoleInfoRepo mockRoleInfoRepo;
    @Mock
    private ModelMapper mockMapper;

    @InjectMocks
    private RoleInfoServiceImp roleInfoServiceImpUnderTest;

    @Test
    void testFindByRoleName() {
        // Setup
        when(mockRoleInfoRepo.findByName("roleName")).thenReturn(Optional.of(Role.builder().build()));

        // Run the test
        final Role result = roleInfoServiceImpUnderTest.findByRoleName("roleName");

        // Verify the results
    }

    @Test
    void testFindByRoleName_RoleInfoRepoReturnsAbsent() {
        // Setup
        when(mockRoleInfoRepo.findByName("roleName")).thenReturn(Optional.empty());

        // Run the test
        assertThatThrownBy(() -> roleInfoServiceImpUnderTest.findByRoleName("roleName"))
                .isInstanceOf(NoSuchRolePresentException.class);
    }

    @Test
    void testRoleToDto() {
        // Setup
        final Role role = Role.builder().build();
        when(mockMapper.map(any(Object.class), eq(RoleDto.class))).thenReturn(RoleDto.builder().build());

        // Run the test
        final RoleDto result = roleInfoServiceImpUnderTest.roleToDto(role);

        // Verify the results
    }

    @Test
    void testRoleToDto_ModelMapperThrowsConfigurationException() {
        // Setup
        final Role role = Role.builder().build();
        when(mockMapper.map(any(Object.class), eq(RoleDto.class))).thenThrow(ConfigurationException.class);

        // Run the test
        assertThatThrownBy(() -> roleInfoServiceImpUnderTest.roleToDto(role))
                .isInstanceOf(ConfigurationException.class);
    }

    @Test
    void testRoleToDto_ModelMapperThrowsMappingException() {
        // Setup
        final Role role = Role.builder().build();
        when(mockMapper.map(any(Object.class), eq(RoleDto.class))).thenThrow(MappingException.class);

        // Run the test
        assertThatThrownBy(() -> roleInfoServiceImpUnderTest.roleToDto(role)).isInstanceOf(MappingException.class);
    }
}
