package com.example.inventoryservice.serviceImp;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.anyInt;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.example.inventoryservice.entities.Inventory;
import com.example.inventoryservice.entities.Stock;
import com.example.inventoryservice.exception.NoAuthorityException;
import com.example.inventoryservice.exception.NoSuchInventoryPresentException;
import com.example.inventoryservice.repository.InventoryRepo;
import com.example.inventoryservice.requestDto.InventoryRequestDto;
import com.example.inventoryservice.requestDto.RequestForInventoryDto;
import com.example.inventoryservice.responseDto.InventoryDto;
import com.example.inventoryservice.responseDto.InventoryResponseDto;
import com.example.inventoryservice.service.StockAvailableService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.aot.DisabledInAotMode;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ContextConfiguration(classes = {InventoryServiceImpl.class})
@ExtendWith(SpringExtension.class)
@DisabledInAotMode
class InventoryServiceImplTest {
    @MockBean
    private InventoryRepo inventoryRepo;

    @Autowired
    private InventoryServiceImpl inventoryServiceImpl;

    @MockBean
    private ModelMapper modelMapper;

    @MockBean
    private StockAvailableService stockAvailableService;

    /**
     * Method under test:
     * {@link InventoryServiceImpl#addInventory(InventoryRequestDto, String)}
     */
    @Test
    void testAddInventory() {
        // Arrange, Act and Assert
        assertThrows(NoAuthorityException.class,
                () -> inventoryServiceImpl.addInventory(new InventoryRequestDto("Name", 1, 10.0d), "Role"));
    }

    /**
     * Method under test:
     * {@link InventoryServiceImpl#addInventory(InventoryRequestDto, String)}
     */
    @Test
    void testAddInventory2() {
        // Arrange
        Inventory inventory = new Inventory();
        inventory.setInventoryId(1L);
        inventory.setName("Name");
        inventory.setPrice(10.0d);
        Optional<Inventory> ofResult = Optional.of(inventory);
        when(inventoryRepo.findByName(Mockito.<String>any())).thenReturn(ofResult);

        Inventory inventory2 = new Inventory();
        inventory2.setInventoryId(1L);
        inventory2.setName("admin");
        inventory2.setPrice(10.0d);
        when(modelMapper.map(Mockito.<Object>any(), Mockito.<Class<Object>>any())).thenReturn(inventory2);
        InventoryRequestDto inventory3 = mock(InventoryRequestDto.class);
        when(inventory3.getPrice()).thenThrow(new NoSuchInventoryPresentException("admin"));

        // Act and Assert
        assertThrows(NoSuchInventoryPresentException.class, () -> inventoryServiceImpl.addInventory(inventory3, "admin"));
        verify(inventoryRepo).findByName(eq("admin"));
        verify(inventory3).getPrice();
        verify(modelMapper).map(isA(Object.class), isA(Class.class));
    }

    /**
     * Method under test:
     * {@link InventoryServiceImpl#addInventory(InventoryRequestDto, String)}
     */
    @Test
    void testAddInventory3() {
        // Arrange
        Inventory inventory = new Inventory();
        inventory.setInventoryId(1L);
        inventory.setName("Name");
        inventory.setPrice(10.0d);

        Inventory inventory2 = new Inventory();
        inventory2.setInventoryId(1L);
        inventory2.setName("Name");
        inventory2.setPrice(10.0d);
        Optional<Inventory> ofResult = Optional.of(inventory2);
        when(inventoryRepo.save(Mockito.<Inventory>any())).thenReturn(inventory);
        when(inventoryRepo.findByName(Mockito.<String>any())).thenReturn(ofResult);

        Inventory inventory3 = new Inventory();
        inventory3.setInventoryId(1L);
        inventory3.setName("Name");
        inventory3.setPrice(10.0d);
        when(modelMapper.map(Mockito.<Object>any(), Mockito.<Class<Inventory>>any())).thenReturn(inventory3);
        when(stockAvailableService.addToStockAvailable(Mockito.<Inventory>any(), anyInt()))
                .thenThrow(new NoAuthorityException("admin"));
        InventoryRequestDto inventory4 = InventoryRequestDto.builder().name("Name").price(10.0d).quantity(1).build();

        // Act and Assert
        assertThrows(NoAuthorityException.class, () -> inventoryServiceImpl.addInventory(inventory4, "admin"));
        verify(inventoryRepo).findByName(eq("Name"));
        verify(stockAvailableService).addToStockAvailable(isA(Inventory.class), eq(1));
        verify(modelMapper).map(isA(Object.class), isA(Class.class));
        verify(inventoryRepo).save(isA(Inventory.class));
    }

    /**
     * Method under test:
     * {@link InventoryServiceImpl#removeInventory(RequestForInventoryDto, String)}
     */
    @Test
    void testRemoveInventory() {
        // Arrange, Act and Assert
        assertThrows(NoAuthorityException.class,
                () -> inventoryServiceImpl.removeInventory(new RequestForInventoryDto(), "Role"));
    }

    /**
     * Method under test:
     * {@link InventoryServiceImpl#removeInventory(RequestForInventoryDto, String)}
     */
    @Test
    void testRemoveInventory2() {
        // Arrange
        Inventory inventory = new Inventory();
        inventory.setInventoryId(1L);
        inventory.setName("Name");
        inventory.setPrice(10.0d);
        Optional<Inventory> ofResult = Optional.of(inventory);
        when(inventoryRepo.findByName(Mockito.<String>any())).thenReturn(ofResult);
        ArrayList<Stock> stockList = new ArrayList<>();
        when(stockAvailableService.deleteByInventory(Mockito.<Inventory>any())).thenReturn(stockList);
        RequestForInventoryDto inventoryName = mock(RequestForInventoryDto.class);
        when(inventoryName.getName()).thenReturn("Name");

        // Act
        List<Stock> actualRemoveInventoryResult = inventoryServiceImpl.removeInventory(inventoryName, "admin");

        // Assert
        verify(inventoryRepo).findByName(eq("Name"));
        verify(inventoryName).getName();
        verify(stockAvailableService).deleteByInventory(isA(Inventory.class));
        assertTrue(actualRemoveInventoryResult.isEmpty());
        assertSame(stockList, actualRemoveInventoryResult);
    }

    /**
     * Method under test:
     * {@link InventoryServiceImpl#removeInventory(RequestForInventoryDto, String)}
     */
    @Test
    void testRemoveInventory3() {
        // Arrange
        RequestForInventoryDto inventoryName = mock(RequestForInventoryDto.class);
        when(inventoryName.getName()).thenThrow(new NoAuthorityException("admin"));

        // Act and Assert
        assertThrows(NoAuthorityException.class, () -> inventoryServiceImpl.removeInventory(inventoryName, "admin"));
        verify(inventoryName).getName();
    }

    /**
     * Method under test:
     * {@link InventoryServiceImpl#removeInventory(RequestForInventoryDto, String)}
     */
    @Test
    void testRemoveInventory4() {
        // Arrange
        Optional<Inventory> emptyResult = Optional.empty();
        when(inventoryRepo.findByName(Mockito.<String>any())).thenReturn(emptyResult);
        RequestForInventoryDto inventoryName = mock(RequestForInventoryDto.class);
        when(inventoryName.getName()).thenReturn("Name");

        // Act and Assert
        assertThrows(NoSuchInventoryPresentException.class,
                () -> inventoryServiceImpl.removeInventory(inventoryName, "admin"));
        verify(inventoryRepo).findByName(eq("Name"));
        verify(inventoryName).getName();
    }

    /**
     * Method under test: {@link InventoryServiceImpl#findInventoryByName(String)}
     */
    @Test
    void testFindInventoryByName() {
        // Arrange
        Inventory inventory = new Inventory();
        inventory.setInventoryId(1L);
        inventory.setName("Name");
        inventory.setPrice(10.0d);
        Optional<Inventory> ofResult = Optional.of(inventory);
        when(inventoryRepo.findByName(Mockito.<String>any())).thenReturn(ofResult);

        // Act
        Inventory actualFindInventoryByNameResult = inventoryServiceImpl.findInventoryByName("Inventory Name");

        // Assert
        verify(inventoryRepo).findByName(eq("Inventory Name"));
        assertSame(inventory, actualFindInventoryByNameResult);
    }

    /**
     * Method under test: {@link InventoryServiceImpl#findInventoryByName(String)}
     */
    @Test
    void testFindInventoryByName2() {
        // Arrange
        Optional<Inventory> emptyResult = Optional.empty();
        when(inventoryRepo.findByName(Mockito.<String>any())).thenReturn(emptyResult);

        // Act and Assert
        assertThrows(NoSuchInventoryPresentException.class,
                () -> inventoryServiceImpl.findInventoryByName("Inventory Name"));
        verify(inventoryRepo).findByName(eq("Inventory Name"));
    }

    /**
     * Method under test: {@link InventoryServiceImpl#findInventoryByName(String)}
     */
    @Test
    void testFindInventoryByName3() {
        // Arrange
        when(inventoryRepo.findByName(Mockito.<String>any()))
                .thenThrow(new NoAuthorityException("No such Inventory is present"));

        // Act and Assert
        assertThrows(NoAuthorityException.class, () -> inventoryServiceImpl.findInventoryByName("Inventory Name"));
        verify(inventoryRepo).findByName(eq("Inventory Name"));
    }

    /**
     * Method under test:
     * {@link InventoryServiceImpl#findInventoryDtoByName(String)}
     */
    @Test
    void testFindInventoryDtoByName() {
        // Arrange
        Inventory inventory = new Inventory();
        inventory.setInventoryId(1L);
        inventory.setName("Name");
        inventory.setPrice(10.0d);
        Optional<Inventory> ofResult = Optional.of(inventory);
        when(inventoryRepo.findByName(Mockito.<String>any())).thenReturn(ofResult);
        InventoryDto buildResult = InventoryDto.builder().inventoryId(1L).name("Name").price(10.0d).build();
        when(modelMapper.map(Mockito.<Object>any(), Mockito.<Class<InventoryDto>>any())).thenReturn(buildResult);

        // Act
        inventoryServiceImpl.findInventoryDtoByName("Inventory Name");

        // Assert
        verify(inventoryRepo).findByName(eq("Inventory Name"));
        verify(modelMapper).map(isA(Object.class), isA(Class.class));
    }

    /**
     * Method under test:
     * {@link InventoryServiceImpl#findInventoryDtoByName(String)}
     */
    @Test
    void testFindInventoryDtoByName2() {
        // Arrange
        Inventory inventory = new Inventory();
        inventory.setInventoryId(1L);
        inventory.setName("Name");
        inventory.setPrice(10.0d);
        Optional<Inventory> ofResult = Optional.of(inventory);
        when(inventoryRepo.findByName(Mockito.<String>any())).thenReturn(ofResult);
        when(modelMapper.map(Mockito.<Object>any(), Mockito.<Class<InventoryDto>>any()))
                .thenThrow(new NoAuthorityException("foo"));

        // Act and Assert
        assertThrows(NoAuthorityException.class, () -> inventoryServiceImpl.findInventoryDtoByName("Inventory Name"));
        verify(inventoryRepo).findByName(eq("Inventory Name"));
        verify(modelMapper).map(isA(Object.class), isA(Class.class));
    }

    /**
     * Method under test:
     * {@link InventoryServiceImpl#findInventoryDtoByName(String)}
     */
    @Test
    void testFindInventoryDtoByName3() {
        // Arrange
        Optional<Inventory> emptyResult = Optional.empty();
        when(inventoryRepo.findByName("Inventory Name")).thenReturn(emptyResult);
        // Act and Assert
        assertThrows(NoSuchInventoryPresentException.class,
                () -> inventoryServiceImpl.findInventoryDtoByName("Inventory Name"));
        verify(inventoryRepo).findByName("Inventory Name");
    }

    /**
     * Method under test: {@link InventoryServiceImpl#inventoryToDto(Inventory)}
     */
    @Test
    void testInventoryToDto() {
        // Arrange
        InventoryDto buildResult = InventoryDto.builder().inventoryId(1L).name("Name").price(10.0d).build();
        when(modelMapper.map(Mockito.<Object>any(), Mockito.<Class<InventoryDto>>any())).thenReturn(buildResult);

        Inventory inventory = new Inventory();
        inventory.setInventoryId(1L);
        inventory.setName("Name");
        inventory.setPrice(10.0d);

        // Act
        inventoryServiceImpl.inventoryToDto(inventory);

        // Assert
        verify(modelMapper).map(isA(Object.class), isA(Class.class));
    }

    /**
     * Method under test: {@link InventoryServiceImpl#inventoryToDto(Inventory)}
     */
    @Test
    void testInventoryToDto2() {
        // Arrange
        when(modelMapper.map(Mockito.<Object>any(), Mockito.<Class<InventoryDto>>any()))
                .thenThrow(new NoAuthorityException("foo"));

        Inventory inventory = new Inventory();
        inventory.setInventoryId(1L);
        inventory.setName("Name");
        inventory.setPrice(10.0d);

        // Act and Assert
        assertThrows(NoAuthorityException.class, () -> inventoryServiceImpl.inventoryToDto(inventory));
        verify(modelMapper).map(isA(Object.class), isA(Class.class));
    }

    /**
     * Method under test:
     * {@link InventoryServiceImpl#inventoryToDto(Inventory, int)}
     */
    @Test
    void testInventoryToDto3() {
        // Arrange
        InventoryResponseDto buildResult = InventoryResponseDto.builder().name("Name").price(10.0d).quantity(1).build();
        when(modelMapper.map(Mockito.<Object>any(), Mockito.<Class<InventoryResponseDto>>any())).thenReturn(buildResult);

        Inventory inventory = new Inventory();
        inventory.setInventoryId(1L);
        inventory.setName("Name");
        inventory.setPrice(10.0d);

        // Act
        InventoryResponseDto actualInventoryToDtoResult = inventoryServiceImpl.inventoryToDto(inventory, 1);

        // Assert
        verify(modelMapper).map(isA(Object.class), isA(Class.class));
        assertEquals(1, actualInventoryToDtoResult.getQuantity());
    }

    /**
     * Method under test:
     * {@link InventoryServiceImpl#inventoryToDto(Inventory, int)}
     */
    @Test
    void testInventoryToDto4() {
        // Arrange
        when(modelMapper.map(Mockito.<Object>any(), Mockito.<Class<InventoryResponseDto>>any()))
                .thenThrow(new NoAuthorityException("foo"));

        Inventory inventory = new Inventory();
        inventory.setInventoryId(1L);
        inventory.setName("Name");
        inventory.setPrice(10.0d);

        // Act and Assert
        assertThrows(NoAuthorityException.class, () -> inventoryServiceImpl.inventoryToDto(inventory, 1));
        verify(modelMapper).map(isA(Object.class), isA(Class.class));
    }

    /**
     * Method under test:
     * {@link InventoryServiceImpl#dtoToInventory(InventoryRequestDto)}
     */
    @Test
    void testDtoToInventory() {
        // Arrange
        Inventory inventory = new Inventory();
        inventory.setInventoryId(1L);
        inventory.setName("Name");
        inventory.setPrice(10.0d);
        when(modelMapper.map(Mockito.<Object>any(), Mockito.<Class<Inventory>>any())).thenReturn(inventory);

        // Act
        Inventory actualDtoToInventoryResult = inventoryServiceImpl
                .dtoToInventory(new InventoryRequestDto("Name", 1, 10.0d));

        // Assert
        verify(modelMapper).map(isA(Object.class), isA(Class.class));
        assertSame(inventory, actualDtoToInventoryResult);
    }

    /**
     * Method under test:
     * {@link InventoryServiceImpl#dtoToInventory(InventoryRequestDto)}
     */
    @Test
    void testDtoToInventory2() {
        // Arrange
        when(modelMapper.map(Mockito.<Object>any(), Mockito.<Class<Inventory>>any()))
                .thenThrow(new NoAuthorityException("foo"));

        // Act and Assert
        assertThrows(NoAuthorityException.class,
                () -> inventoryServiceImpl.dtoToInventory(new InventoryRequestDto("Name", 1, 10.0d)));
        verify(modelMapper).map(isA(Object.class), isA(Class.class));
    }
}
