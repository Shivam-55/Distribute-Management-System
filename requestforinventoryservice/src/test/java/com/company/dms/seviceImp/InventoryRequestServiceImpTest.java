package com.company.dms.seviceImp;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.reactive.function.client.WebClient;

import com.company.dms.customenum.IsApproved;
import com.company.dms.dto.UserDto;
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

import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

public class InventoryRequestServiceImpTest {

    @Mock
    private WebClient.Builder webClient;

    @Mock
    private ModelMapper mapper;

    @Mock
    private InventoryRequestRepo inventoryRequestRepo;

    @Mock
    private InventoryServiceCall inventoryServiceCall;

    @Mock
    private ProfileServiceCall profileServiceCall;

    @InjectMocks
    @Autowired
    private InventoryRequestService inventoryRequestService ;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testRaiseInventoryRequest_Success() {
        InventoryRequestInputDto inputDto = new InventoryRequestInputDto();
        inputDto.setName("InventoryName");
        inputDto.setQuantity(5);

        Long userId = 3L;
        UserDto userDto = new UserDto();
        userDto.setUserId(3L);
        userDto.setIsApproved(IsApproved.APPROVED);

        InventoryDto inventoryDto = new InventoryDto();
        inventoryDto.setInventoryId(1L);
        inventoryDto.setName("InventoryName");

        when(profileServiceCall.getUserWithId(anyLong())).thenReturn(new UserDto());
        when(inventoryServiceCall.getInventoryByName(anyString())).thenReturn(inventoryDto);
        when(inventoryRequestRepo.findByUserIdAndInventoryIdAndStatus(anyLong(), anyLong(), any())).thenReturn(Optional.empty());
        when(inventoryRequestRepo.save(any())).thenReturn(new InventoryRequest());

        InventoryRequestDto result = inventoryRequestService.raiseRequestForInventory(inputDto, userId);

        assertNotNull(result);
        assertEquals("InventoryName", result.getName());
        assertEquals(5, result.getQuantity());
    }

    @Test
    public void testRaiseInventoryRequest_UserNotApproved_ExceptionThrown() {
        InventoryRequestInputDto inputDto = new InventoryRequestInputDto();
        inputDto.setName("InventoryName");
        inputDto.setQuantity(5);


        Long userId = 3L;

        UserDto userDto = new UserDto();
        userDto.setUserId(3L);
        userDto.setIsApproved(IsApproved.NOTAPPROVED);

        when(profileServiceCall.getUserWithId(anyLong())).thenReturn(userDto);

        assertThrows(UserNotApprovedException.class, () -> {
            inventoryRequestService.raiseRequestForInventory(inputDto, userId);
        });
    }

    @Test
    public void testRaiseInventoryRequest_RequestAlreadyPresent_ExceptionThrown() {
        // Arrange
        InventoryRequestInputDto inputDto = new InventoryRequestInputDto();
        inputDto.setName("InventoryName");
        inputDto.setQuantity(5);

        Long userId = 3L;

        UserDto userDto = new UserDto();
        userDto.setIsApproved(IsApproved.APPROVED);
        userDto.setUserId(3L);

        InventoryDto inventoryDto = new InventoryDto();
        inventoryDto.setInventoryId(1L);
        inventoryDto.setName("InventoryName");

        // Mocking dependencies
        when(profileServiceCall.getUserWithId(anyLong())).thenReturn(new UserDto());
        when(inventoryServiceCall.getInventoryByName(anyString())).thenReturn(inventoryDto);
        when(inventoryRequestRepo.findByUserIdAndInventoryIdAndStatus(anyLong(), anyLong(), any())).thenReturn(Optional.of(new InventoryRequest()));

        // Act and Assert
        assertThrows(UserNotApprovedException.class, () -> {
            inventoryRequestService.raiseRequestForInventory(inputDto, userId);
        });
    }

    @Test
    public void testRaiseInventoryRequest_UserInactive_ExceptionThrown() {
        InventoryRequestInputDto inputDto = new InventoryRequestInputDto();
        inputDto.setName("InventoryName");
        inputDto.setQuantity(5);

        Long userId = 3L;
        UserDto userDto = new UserDto();
        userDto.setIsApproved(IsApproved.APPROVED);
        userDto.setUserId(3L);

        userDto.setActive(false);

        when(profileServiceCall.getUserWithId(anyLong())).thenReturn(userDto);

        assertThrows(UserNotApprovedException.class, () -> {
            inventoryRequestService.raiseRequestForInventory(inputDto, userId);
        });
    }

}
