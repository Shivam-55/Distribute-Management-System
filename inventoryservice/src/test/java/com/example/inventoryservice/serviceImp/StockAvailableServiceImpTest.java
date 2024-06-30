package com.example.inventoryservice.serviceImp;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.example.inventoryservice.entities.Inventory;
import com.example.inventoryservice.entities.OrderHistory;
import com.example.inventoryservice.entities.Stock;
import com.example.inventoryservice.exception.NoStockAvailableException;
import com.example.inventoryservice.exception.NoSuchRequestException;
import com.example.inventoryservice.exception.NotActiveUserException;
import com.example.inventoryservice.exception.UserNotApprovedException;
import com.example.inventoryservice.repository.StockRepo;
import com.example.inventoryservice.requestDto.InventoryUpdateRetailerDto;
import com.example.inventoryservice.requestDto.RequestForInventoryDto;
import com.example.inventoryservice.requestDto.StockDto;
import com.example.inventoryservice.requestDto.UserDto;
import com.example.inventoryservice.responseDto.RequestForInventoryResDto;
import com.example.inventoryservice.service.InventoryService;
import com.example.inventoryservice.service.OrderHistoryService;
import com.example.inventoryservice.otherservicecall.ProfileManagementService;
import com.example.inventoryservice.otherservicecall.RequestForInventoryService;

import java.time.LocalDate;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.aot.DisabledInAotMode;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import reactor.core.Disposable;
import reactor.core.publisher.Mono;

@ContextConfiguration(classes = {StockAvailableServiceImp.class})
@ExtendWith(SpringExtension.class)
@DisabledInAotMode
class StockAvailableServiceImpTest {
    @MockBean
    private InventoryService inventoryService;

    @MockBean
    private ModelMapper modelMapper;

    @MockBean
    private OrderHistoryService orderHistoryService;

    @MockBean
    private ProfileManagementService profileManagementService;

    @MockBean
    private RequestForInventoryService requestForInventoryService;

    @Autowired
    private StockAvailableServiceImp stockAvailableServiceImp;

    @MockBean
    private StockRepo stockRepo;

    /**
     * Method under test:
     * {@link StockAvailableServiceImp#approveRequest(Long, RequestForInventoryDto)}
     */
    @Test
    void testApproveRequest() {
        // Arrange
        UserDto buildResult = UserDto.builder()
                .area("Area")
                .email("jane.doe@example.org")
                .mobile(1L)
                .pinCode("Pin Code")
                .userId(1L)
                .username("janedoe")
                .build();
        when(profileManagementService.getUserWithId(Mockito.<Long>any())).thenReturn(buildResult);

        // Act and Assert
        assertThrows(UserNotApprovedException.class,
                () -> stockAvailableServiceImp.approveRequest(1L, new RequestForInventoryDto()));
        verify(profileManagementService).getUserWithId(isA(Long.class));
    }

    /**
     * Method under test:
     * {@link StockAvailableServiceImp#approveRequest(Long, RequestForInventoryDto)}
     */
    @Test
    void testApproveRequest2() {
        // Arrange
        when(profileManagementService.getUserWithId(Mockito.<Long>any())).thenThrow(new UserNotApprovedException("foo"));

        // Act and Assert
        assertThrows(UserNotApprovedException.class,
                () -> stockAvailableServiceImp.approveRequest(1L, new RequestForInventoryDto()));
        verify(profileManagementService).getUserWithId(isA(Long.class));
    }

    /**
     * Method under test:
     * {@link StockAvailableServiceImp#distributeStock(Long, Long, Stock, int, Inventory)}
     */
    @Test
    void testDistributeStock() {
        // Arrange
        Inventory inventory = new Inventory();
        inventory.setInventoryId(1L);
        inventory.setName("Name");
        inventory.setPrice(10.0d);

        OrderHistory orderHistory = new OrderHistory();
        orderHistory.setApproverUserId(1L);
        orderHistory.setCreateDate(Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()));
        orderHistory.setInventory(inventory);
        orderHistory.setPurchaseId(1L);
        orderHistory.setQuantity(1);
        orderHistory.setRemark("Remark");
        orderHistory.setRequesterUserId(1L);
        when(orderHistoryService.saveOrderHistory(Mockito.<OrderHistory>any())).thenReturn(orderHistory);
        RequestForInventoryResDto buildResult = RequestForInventoryResDto.builder()
                .approverUserId(1L)
                .inventoryId(1L)
                .name("Name")
                .quantity(1)
                .requesterUserId(1L)
                .build();
        Mono<RequestForInventoryResDto> justResult = Mono.just(buildResult);
        when(requestForInventoryService.updateStatusInRequest(Mockito.<RequestForInventoryResDto>any()))
                .thenReturn(justResult);

        Inventory inventory2 = new Inventory();
        inventory2.setInventoryId(1L);
        inventory2.setName("Name");
        inventory2.setPrice(10.0d);

        Stock stock = new Stock();
        stock.setCreatedAt(Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()));
        stock.setInventory(inventory2);
        stock.setQuantity(1);
        stock.setStockId(1L);
        stock.setUpdatedAt(Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()));
        stock.setUserId(1L);

        Inventory inventory3 = new Inventory();
        inventory3.setInventoryId(1L);
        inventory3.setName("Name");
        inventory3.setPrice(10.0d);

        Stock stock2 = new Stock();
        stock2.setCreatedAt(Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()));
        stock2.setInventory(inventory3);
        stock2.setQuantity(1);
        stock2.setStockId(1L);
        stock2.setUpdatedAt(Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()));
        stock2.setUserId(1L);
        Optional<Stock> ofResult = Optional.of(stock2);
        when(stockRepo.save(Mockito.<Stock>any())).thenReturn(stock);
        when(stockRepo.findByUserIdAndInventory(Mockito.<Long>any(), Mockito.<Inventory>any())).thenReturn(ofResult);

        Inventory inventory4 = new Inventory();
        inventory4.setInventoryId(1L);
        inventory4.setName("Name");
        inventory4.setPrice(10.0d);

        Stock approverAvailableStock = new Stock();
        approverAvailableStock
                .setCreatedAt(Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()));
        approverAvailableStock.setInventory(inventory4);
        approverAvailableStock.setQuantity(1);
        approverAvailableStock.setStockId(1L);
        approverAvailableStock
                .setUpdatedAt(Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()));
        approverAvailableStock.setUserId(1L);

        Inventory inventory5 = new Inventory();
        inventory5.setInventoryId(1L);
        inventory5.setName("Name");
        inventory5.setPrice(10.0d);

        // Act
        OrderHistory actualDistributeStockResult = stockAvailableServiceImp.distributeStock(1L, 1L, approverAvailableStock,
                1, inventory5);

        // Assert
        verify(stockRepo).findByUserIdAndInventory(isA(Long.class), isA(Inventory.class));
        verify(orderHistoryService).saveOrderHistory(isA(OrderHistory.class));
        verify(requestForInventoryService).updateStatusInRequest(isA(RequestForInventoryResDto.class));
        verify(stockRepo, atLeast(1)).save(Mockito.<Stock>any());
        assertEquals(0, approverAvailableStock.getQuantity());
        assertSame(orderHistory, actualDistributeStockResult);
    }

    /**
     * Method under test:
     * {@link StockAvailableServiceImp#distributeStock(Long, Long, Stock, int, Inventory)}
     */
    @Test
    void testDistributeStock2() {
        // Arrange
        when(stockRepo.findByUserIdAndInventory(Mockito.<Long>any(), Mockito.<Inventory>any()))
                .thenThrow(new NoSuchRequestException("This is the first order purchase"));

        Inventory inventory = new Inventory();
        inventory.setInventoryId(1L);
        inventory.setName("Name");
        inventory.setPrice(10.0d);

        Stock approverAvailableStock = new Stock();
        approverAvailableStock
                .setCreatedAt(Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()));
        approverAvailableStock.setInventory(inventory);
        approverAvailableStock.setQuantity(1);
        approverAvailableStock.setStockId(1L);
        approverAvailableStock
                .setUpdatedAt(Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()));
        approverAvailableStock.setUserId(1L);

        Inventory inventory2 = new Inventory();
        inventory2.setInventoryId(1L);
        inventory2.setName("Name");
        inventory2.setPrice(10.0d);

        // Act and Assert
        assertThrows(NoSuchRequestException.class,
                () -> stockAvailableServiceImp.distributeStock(1L, 1L, approverAvailableStock, 1, inventory2));
        verify(stockRepo).findByUserIdAndInventory(isA(Long.class), isA(Inventory.class));
    }

    /**
     * Method under test:
     * {@link StockAvailableServiceImp#distributeStock(Long, Long, Stock, int, Inventory)}
     */
    @Test
    void testDistributeStock3() {
        // Arrange
        Inventory inventory = new Inventory();
        inventory.setInventoryId(1L);
        inventory.setName("Name");
        inventory.setPrice(10.0d);

        OrderHistory orderHistory = new OrderHistory();
        orderHistory.setApproverUserId(1L);
        orderHistory.setCreateDate(Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()));
        orderHistory.setInventory(inventory);
        orderHistory.setPurchaseId(1L);
        orderHistory.setQuantity(1);
        orderHistory.setRemark("Remark");
        orderHistory.setRequesterUserId(1L);
        when(orderHistoryService.saveOrderHistory(Mockito.<OrderHistory>any())).thenReturn(orderHistory);
        Mono<RequestForInventoryResDto> mono = mock(Mono.class);
        when(mono.subscribe(Mockito.<Consumer<RequestForInventoryResDto>>any(), Mockito.<Consumer<Throwable>>any()))
                .thenReturn(mock(Disposable.class));
        when(requestForInventoryService.updateStatusInRequest(Mockito.<RequestForInventoryResDto>any())).thenReturn(mono);

        Inventory inventory2 = new Inventory();
        inventory2.setInventoryId(1L);
        inventory2.setName("Name");
        inventory2.setPrice(10.0d);

        Stock stock = new Stock();
        stock.setCreatedAt(Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()));
        stock.setInventory(inventory2);
        stock.setQuantity(1);
        stock.setStockId(1L);
        stock.setUpdatedAt(Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()));
        stock.setUserId(1L);

        Inventory inventory3 = new Inventory();
        inventory3.setInventoryId(1L);
        inventory3.setName("Name");
        inventory3.setPrice(10.0d);

        Stock stock2 = new Stock();
        stock2.setCreatedAt(Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()));
        stock2.setInventory(inventory3);
        stock2.setQuantity(1);
        stock2.setStockId(1L);
        stock2.setUpdatedAt(Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()));
        stock2.setUserId(1L);
        Optional<Stock> ofResult = Optional.of(stock2);
        when(stockRepo.save(Mockito.<Stock>any())).thenReturn(stock);
        when(stockRepo.findByUserIdAndInventory(Mockito.<Long>any(), Mockito.<Inventory>any())).thenReturn(ofResult);

        Inventory inventory4 = new Inventory();
        inventory4.setInventoryId(1L);
        inventory4.setName("Name");
        inventory4.setPrice(10.0d);

        Stock approverAvailableStock = new Stock();
        approverAvailableStock
                .setCreatedAt(Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()));
        approverAvailableStock.setInventory(inventory4);
        approverAvailableStock.setQuantity(1);
        approverAvailableStock.setStockId(1L);
        approverAvailableStock
                .setUpdatedAt(Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()));
        approverAvailableStock.setUserId(1L);

        Inventory inventory5 = new Inventory();
        inventory5.setInventoryId(1L);
        inventory5.setName("Name");
        inventory5.setPrice(10.0d);

        // Act
        OrderHistory actualDistributeStockResult = stockAvailableServiceImp.distributeStock(1L, 1L, approverAvailableStock,
                1, inventory5);

        // Assert
        verify(stockRepo).findByUserIdAndInventory(isA(Long.class), isA(Inventory.class));
        verify(orderHistoryService).saveOrderHistory(isA(OrderHistory.class));
        verify(requestForInventoryService).updateStatusInRequest(isA(RequestForInventoryResDto.class));
        verify(stockRepo, atLeast(1)).save(Mockito.<Stock>any());
        verify(mono).subscribe(isA(Consumer.class), isA(Consumer.class));
        assertEquals(0, approverAvailableStock.getQuantity());
        assertSame(orderHistory, actualDistributeStockResult);
    }

    /**
     * Method under test: {@link StockAvailableServiceImp#saveStock(Stock)}
     */
    @Test
    void testSaveStock() {
        // Arrange
        Inventory inventory = new Inventory();
        inventory.setInventoryId(1L);
        inventory.setName("Name");
        inventory.setPrice(10.0d);

        Stock stock = new Stock();
        stock.setCreatedAt(Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()));
        stock.setInventory(inventory);
        stock.setQuantity(1);
        stock.setStockId(1L);
        stock.setUpdatedAt(Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()));
        stock.setUserId(1L);
        when(stockRepo.save(Mockito.<Stock>any())).thenReturn(stock);

        Inventory inventory2 = new Inventory();
        inventory2.setInventoryId(1L);
        inventory2.setName("Name");
        inventory2.setPrice(10.0d);

        Stock stock2 = new Stock();
        Date createdAt = Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant());
        stock2.setCreatedAt(createdAt);
        stock2.setInventory(inventory2);
        stock2.setQuantity(1);
        stock2.setStockId(1L);
        Date updatedAt = Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant());
        stock2.setUpdatedAt(updatedAt);
        stock2.setUserId(1L);

        // Act
        stockAvailableServiceImp.saveStock(stock2);

        // Assert that nothing has changed
        verify(stockRepo).save(isA(Stock.class));
        assertEquals(1, stock2.getQuantity());
        assertEquals(1L, stock2.getInventory().getInventoryId().longValue());
        assertEquals(1L, stock2.getStockId().longValue());
        assertEquals(1L, stock2.getUserId().longValue());
        assertSame(createdAt, stock2.getCreatedAt());
        assertSame(updatedAt, stock2.getUpdatedAt());
    }

    /**
     * Method under test: {@link StockAvailableServiceImp#saveStock(Stock)}
     */
    @Test
    void testSaveStock2() {
        // Arrange
        when(stockRepo.save(Mockito.<Stock>any())).thenThrow(new UserNotApprovedException("foo"));

        Inventory inventory = new Inventory();
        inventory.setInventoryId(1L);
        inventory.setName("Name");
        inventory.setPrice(10.0d);

        Stock stock = new Stock();
        stock.setCreatedAt(Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()));
        stock.setInventory(inventory);
        stock.setQuantity(1);
        stock.setStockId(1L);
        stock.setUpdatedAt(Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()));
        stock.setUserId(1L);

        // Act and Assert
        assertThrows(UserNotApprovedException.class, () -> stockAvailableServiceImp.saveStock(stock));
        verify(stockRepo).save(isA(Stock.class));
    }

    /**
     * Method under test:
     * {@link StockAvailableServiceImp#findByUserIdAndInventory(Long, Inventory)}
     */
    @Test
    void testFindByUserIdAndInventory() {
        // Arrange
        Inventory inventory = new Inventory();
        inventory.setInventoryId(1L);
        inventory.setName("Name");
        inventory.setPrice(10.0d);

        Stock stock = new Stock();
        stock.setCreatedAt(Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()));
        stock.setInventory(inventory);
        stock.setQuantity(1);
        stock.setStockId(1L);
        stock.setUpdatedAt(Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()));
        stock.setUserId(1L);
        Optional<Stock> ofResult = Optional.of(stock);
        when(stockRepo.findByUserIdAndInventory(Mockito.<Long>any(), Mockito.<Inventory>any())).thenReturn(ofResult);

        Inventory inventory2 = new Inventory();
        inventory2.setInventoryId(1L);
        inventory2.setName("Name");
        inventory2.setPrice(10.0d);

        // Act
        Optional<Stock> actualFindByUserIdAndInventoryResult = stockAvailableServiceImp.findByUserIdAndInventory(1L,
                inventory2);

        // Assert
        verify(stockRepo).findByUserIdAndInventory(isA(Long.class), isA(Inventory.class));
        assertSame(ofResult, actualFindByUserIdAndInventoryResult);
    }

    /**
     * Method under test:
     * {@link StockAvailableServiceImp#findByUserIdAndInventory(Long, Inventory)}
     */
    @Test
    void testFindByUserIdAndInventory2() {
        // Arrange
        when(stockRepo.findByUserIdAndInventory(Mockito.<Long>any(), Mockito.<Inventory>any()))
                .thenThrow(new UserNotApprovedException("foo"));

        Inventory inventory = new Inventory();
        inventory.setInventoryId(1L);
        inventory.setName("Name");
        inventory.setPrice(10.0d);

        // Act and Assert
        assertThrows(UserNotApprovedException.class,
                () -> stockAvailableServiceImp.findByUserIdAndInventory(1L, inventory));
        verify(stockRepo).findByUserIdAndInventory(isA(Long.class), isA(Inventory.class));
    }

    /**
     * Method under test:
     * {@link StockAvailableServiceImp#deleteByInventory(Inventory)}
     */
    @Test
    void testDeleteByInventory() {
        // Arrange
        ArrayList<Stock> stockList = new ArrayList<>();
        when(stockRepo.deleteAllByInventory(Mockito.<Inventory>any())).thenReturn(stockList);

        Inventory inventory = new Inventory();
        inventory.setInventoryId(1L);
        inventory.setName("Name");
        inventory.setPrice(10.0d);

        // Act
        List<Stock> actualDeleteByInventoryResult = stockAvailableServiceImp.deleteByInventory(inventory);

        // Assert
        verify(stockRepo).deleteAllByInventory(isA(Inventory.class));
        assertTrue(actualDeleteByInventoryResult.isEmpty());
        assertSame(stockList, actualDeleteByInventoryResult);
    }

    /**
     * Method under test:
     * {@link StockAvailableServiceImp#deleteByInventory(Inventory)}
     */
    @Test
    void testDeleteByInventory2() {
        // Arrange
        when(stockRepo.deleteAllByInventory(Mockito.<Inventory>any())).thenThrow(new UserNotApprovedException("foo"));

        Inventory inventory = new Inventory();
        inventory.setInventoryId(1L);
        inventory.setName("Name");
        inventory.setPrice(10.0d);

        // Act and Assert
        assertThrows(UserNotApprovedException.class, () -> stockAvailableServiceImp.deleteByInventory(inventory));
        verify(stockRepo).deleteAllByInventory(isA(Inventory.class));
    }

    /**
     * Method under test:
     * {@link StockAvailableServiceImp#addToStockAvailable(Inventory, int)}
     */
    @Test
    void testAddToStockAvailable() {
        // Arrange
        Inventory inventory = new Inventory();
        inventory.setInventoryId(1L);
        inventory.setName("Name");
        inventory.setPrice(10.0d);

        Stock stock = new Stock();
        stock.setCreatedAt(Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()));
        stock.setInventory(inventory);
        stock.setQuantity(1);
        stock.setStockId(1L);
        stock.setUpdatedAt(Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()));
        stock.setUserId(1L);

        Inventory inventory2 = new Inventory();
        inventory2.setInventoryId(1L);
        inventory2.setName("Name");
        inventory2.setPrice(10.0d);

        Stock stock2 = new Stock();
        stock2.setCreatedAt(Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()));
        stock2.setInventory(inventory2);
        stock2.setQuantity(1);
        stock2.setStockId(1L);
        stock2.setUpdatedAt(Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()));
        stock2.setUserId(1L);
        Optional<Stock> ofResult = Optional.of(stock2);
        when(stockRepo.save(Mockito.<Stock>any())).thenReturn(stock);
        when(stockRepo.findByUserIdAndInventory(Mockito.<Long>any(), Mockito.<Inventory>any())).thenReturn(ofResult);

        Inventory inventory3 = new Inventory();
        inventory3.setInventoryId(1L);
        inventory3.setName("Name");
        inventory3.setPrice(10.0d);

        // Act
        int actualAddToStockAvailableResult = stockAvailableServiceImp.addToStockAvailable(inventory3, 2);

        // Assert
        verify(stockRepo).findByUserIdAndInventory(isA(Long.class), isA(Inventory.class));
        verify(stockRepo).save(isA(Stock.class));
        assertEquals(3, actualAddToStockAvailableResult);
    }

    /**
     * Method under test:
     * {@link StockAvailableServiceImp#addToStockAvailable(Inventory, int)}
     */
    @Test
    void testAddToStockAvailable2() {
        // Arrange
        Inventory inventory = new Inventory();
        inventory.setInventoryId(1L);
        inventory.setName("Name");
        inventory.setPrice(10.0d);

        Stock stock = new Stock();
        stock.setCreatedAt(Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()));
        stock.setInventory(inventory);
        stock.setQuantity(1);
        stock.setStockId(1L);
        stock.setUpdatedAt(Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()));
        stock.setUserId(1L);
        when(stockRepo.save(Mockito.<Stock>any())).thenReturn(stock);
        Optional<Stock> emptyResult = Optional.empty();
        when(stockRepo.findByUserIdAndInventory(Mockito.<Long>any(), Mockito.<Inventory>any())).thenReturn(emptyResult);

        Inventory inventory2 = new Inventory();
        inventory2.setInventoryId(1L);
        inventory2.setName("Name");
        inventory2.setPrice(10.0d);

        // Act
        int actualAddToStockAvailableResult = stockAvailableServiceImp.addToStockAvailable(inventory2, 2);

        // Assert
        verify(stockRepo).findByUserIdAndInventory(isA(Long.class), isA(Inventory.class));
        verify(stockRepo).save(isA(Stock.class));
        assertEquals(2, actualAddToStockAvailableResult);
    }

    /**
     * Method under test:
     * {@link StockAvailableServiceImp#addToStockAvailable(Inventory, int)}
     */
    @Test
    void testAddToStockAvailable3() {
        // Arrange
        when(stockRepo.findByUserIdAndInventory(Mockito.<Long>any(), Mockito.<Inventory>any()))
                .thenThrow(new NoSuchRequestException("foo"));

        Inventory inventory = new Inventory();
        inventory.setInventoryId(1L);
        inventory.setName("Name");
        inventory.setPrice(10.0d);

        // Act and Assert
        assertThrows(NoSuchRequestException.class, () -> stockAvailableServiceImp.addToStockAvailable(inventory, 2));
        verify(stockRepo).findByUserIdAndInventory(isA(Long.class), isA(Inventory.class));
    }

    /**
     * Method under test:
     * {@link StockAvailableServiceImp#viewAllStockAvailable(Long)}
     */
    @Test
    void testViewAllStockAvailable() {
        // Arrange
        ArrayList<Stock> stockList = new ArrayList<>();
        Optional<List<Stock>> ofResult = Optional.of(stockList);
        when(stockRepo.findByUserId(Mockito.<Long>any())).thenReturn(ofResult);

        // Act
        List<Stock> actualViewAllStockAvailableResult = stockAvailableServiceImp.viewAllStockAvailable(1L);

        // Assert
        verify(stockRepo).findByUserId(isA(Long.class));
        assertTrue(actualViewAllStockAvailableResult.isEmpty());
        assertSame(stockList, actualViewAllStockAvailableResult);
    }

    /**
     * Method under test:
     * {@link StockAvailableServiceImp#viewAllStockAvailable(Long)}
     */
    @Test
    void testViewAllStockAvailable2() {
        // Arrange
        Optional<List<Stock>> emptyResult = Optional.empty();
        when(stockRepo.findByUserId(Mockito.<Long>any())).thenReturn(emptyResult);

        // Act and Assert
        assertThrows(NoStockAvailableException.class, () -> stockAvailableServiceImp.viewAllStockAvailable(1L));
        verify(stockRepo).findByUserId(isA(Long.class));
    }

    /**
     * Method under test:
     * {@link StockAvailableServiceImp#viewAllStockAvailable(Long)}
     */
    @Test
    void testViewAllStockAvailable3() {
        // Arrange
        when(stockRepo.findByUserId(Mockito.<Long>any()))
                .thenThrow(new UserNotApprovedException("Sorry!!! Your  stock list is empty"));

        // Act and Assert
        assertThrows(UserNotApprovedException.class, () -> stockAvailableServiceImp.viewAllStockAvailable(1L));
        verify(stockRepo).findByUserId(isA(Long.class));
    }

    /**
     * Method under test: {@link StockAvailableServiceImp#viewXStock(String, Long)}
     */
    @Test
    void testViewXStock() {
        // Arrange
        Inventory inventory = new Inventory();
        inventory.setInventoryId(1L);
        inventory.setName("Name");
        inventory.setPrice(10.0d);
        when(inventoryService.findInventoryByName(Mockito.<String>any())).thenReturn(inventory);
        StockDto buildResult = StockDto.builder().inventoryName("Inventory Name").quantity(1).build();
        when(modelMapper.map(Mockito.<Object>any(), Mockito.<Class<StockDto>>any())).thenReturn(buildResult);

        Inventory inventory2 = new Inventory();
        inventory2.setInventoryId(1L);
        inventory2.setName("Name");
        inventory2.setPrice(10.0d);

        Stock stock = new Stock();
        stock.setCreatedAt(Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()));
        stock.setInventory(inventory2);
        stock.setQuantity(1);
        stock.setStockId(1L);
        stock.setUpdatedAt(Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()));
        stock.setUserId(1L);
        Optional<Stock> ofResult = Optional.of(stock);
        when(stockRepo.findByUserIdAndInventory(Mockito.<Long>any(), Mockito.<Inventory>any())).thenReturn(ofResult);

        // Act
        stockAvailableServiceImp.viewXStock("Inventory Name", 1L);

        // Assert
        verify(stockRepo).findByUserIdAndInventory(isA(Long.class), isA(Inventory.class));
        verify(inventoryService).findInventoryByName(eq("Inventory Name"));
        verify(modelMapper).map(isA(Object.class), isA(Class.class));
    }

    /**
     * Method under test: {@link StockAvailableServiceImp#viewXStock(String, Long)}
     */
    @Test
    void testViewXStock2() {
        // Arrange
        Inventory inventory = new Inventory();
        inventory.setInventoryId(1L);
        inventory.setName("Name");
        inventory.setPrice(10.0d);
        when(inventoryService.findInventoryByName(Mockito.<String>any())).thenReturn(inventory);
        when(modelMapper.map(Mockito.<Object>any(), Mockito.<Class<StockDto>>any()))
                .thenThrow(new UserNotApprovedException("foo"));

        Inventory inventory2 = new Inventory();
        inventory2.setInventoryId(1L);
        inventory2.setName("Name");
        inventory2.setPrice(10.0d);

        Stock stock = new Stock();
        stock.setCreatedAt(Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()));
        stock.setInventory(inventory2);
        stock.setQuantity(1);
        stock.setStockId(1L);
        stock.setUpdatedAt(Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()));
        stock.setUserId(1L);
        Optional<Stock> ofResult = Optional.of(stock);
        when(stockRepo.findByUserIdAndInventory(Mockito.<Long>any(), Mockito.<Inventory>any())).thenReturn(ofResult);

        // Act and Assert
        assertThrows(UserNotApprovedException.class, () -> stockAvailableServiceImp.viewXStock("Inventory Name", 1L));
        verify(stockRepo).findByUserIdAndInventory(isA(Long.class), isA(Inventory.class));
        verify(inventoryService).findInventoryByName(eq("Inventory Name"));
        verify(modelMapper).map(isA(Object.class), isA(Class.class));
    }

    /**
     * Method under test: {@link StockAvailableServiceImp#viewXStock(String, Long)}
     */
    @Test
    void testViewXStock3() {
        // Arrange
        Inventory inventory = new Inventory();
        Long userId=2L;
        inventory.setInventoryId(1L);
        inventory.setName("Name");
        inventory.setPrice(10.0d);
        when(inventoryService.findInventoryByName("Name")).thenReturn(inventory);
        Optional<Stock> availStock=Optional.empty();
        when(stockRepo.findByUserIdAndInventory(userId,inventory)).thenReturn(availStock);
        when(inventoryService.findInventoryByName(Mockito.<String>any())).thenReturn(inventory);
        when(modelMapper.map(Mockito.<Object>any(), Mockito.<Class<Object>>any())).thenReturn("Map");
//        StockDto buildResult = StockDto.builder().inventoryName("Inventory Name").quantity(1).build();
//        when(modelMapper.map(Mockito.<Object>any(), Mockito.<Class<StockDto>>any())).thenReturn(buildResult);
        Optional<Stock> emptyResult = Optional.empty();
        when(stockRepo.findByUserIdAndInventory(Mockito.<Long>any(), Mockito.<Inventory>any())).thenReturn(emptyResult);

        // Act and Assert
        assertThrows(NoStockAvailableException.class, () -> stockAvailableServiceImp.viewXStock("Inventory Name", 1L));
//        verify(stockRepo).findByUserIdAndInventory(isA(Long.class), isA(Inventory.class));
//        verify(inventoryService).findInventoryByName(eq("Inventory Name"));
//        verify(modelMapper).map(Mockito.<Object>any(), Mockito.<Class<Object>>any());
    }

    /**
     * Method under test:
     * {@link StockAvailableServiceImp#stockUpdateForRetailer(Long, InventoryUpdateRetailerDto)}
     */
    @Test
    void testStockUpdateForRetailer() {
        // Arrange
        UserDto buildResult = UserDto.builder()
                .area("Area")
                .email("jane.doe@example.org")
                .mobile(1L)
                .pinCode("Pin Code")
                .userId(1L)
                .username("janedoe")
                .build();
        when(profileManagementService.getUserWithId(Mockito.<Long>any())).thenReturn(buildResult);

        // Act and Assert
        assertThrows(NotActiveUserException.class,
                () -> stockAvailableServiceImp.stockUpdateForRetailer(1L, new InventoryUpdateRetailerDto("Name", 1)));
        verify(profileManagementService).getUserWithId(isA(Long.class));
    }

    /**
     * Method under test:
     * {@link StockAvailableServiceImp#stockUpdateForRetailer(Long, InventoryUpdateRetailerDto)}
     */
    @Test
    void testStockUpdateForRetailer2() {
        // Arrange
        when(profileManagementService.getUserWithId(Mockito.<Long>any())).thenThrow(new UserNotApprovedException("foo"));

        // Act and Assert
        assertThrows(UserNotApprovedException.class,
                () -> stockAvailableServiceImp.stockUpdateForRetailer(1L, new InventoryUpdateRetailerDto("Name", 1)));
        verify(profileManagementService).getUserWithId(isA(Long.class));
    }

    /**
     * Method under test: {@link StockAvailableServiceImp#stockToDto(Stock)}
     */
    @Test
    void testStockToDto() {
        // Arrange
        StockDto buildResult = StockDto.builder().inventoryName("Inventory Name").quantity(1).build();
        when(modelMapper.map(Mockito.<Object>any(), Mockito.<Class<StockDto>>any())).thenReturn(buildResult);

        Inventory inventory = new Inventory();
        inventory.setInventoryId(1L);
        inventory.setName("Name");
        inventory.setPrice(10.0d);

        Stock stock = new Stock();
        stock.setCreatedAt(Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()));
        stock.setInventory(inventory);
        stock.setQuantity(1);
        stock.setStockId(1L);
        stock.setUpdatedAt(Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()));
        stock.setUserId(1L);

        // Act
        stockAvailableServiceImp.stockToDto(stock);

        // Assert
        verify(modelMapper).map(isA(Object.class), isA(Class.class));
    }

    /**
     * Method under test: {@link StockAvailableServiceImp#stockToDto(Stock)}
     */
    @Test
    void testStockToDto2() {
        // Arrange
        when(modelMapper.map(Mockito.<Object>any(), Mockito.<Class<StockDto>>any()))
                .thenThrow(new UserNotApprovedException("foo"));

        Inventory inventory = new Inventory();
        inventory.setInventoryId(1L);
        inventory.setName("Name");
        inventory.setPrice(10.0d);

        Stock stock = new Stock();
        stock.setCreatedAt(Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()));
        stock.setInventory(inventory);
        stock.setQuantity(1);
        stock.setStockId(1L);
        stock.setUpdatedAt(Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()));
        stock.setUserId(1L);

        // Act and Assert
        assertThrows(UserNotApprovedException.class, () -> stockAvailableServiceImp.stockToDto(stock));
        verify(modelMapper).map(isA(Object.class), isA(Class.class));
    }

    /**
     * Method under test: {@link StockAvailableServiceImp#dtoToStock(StockDto)}
     */
    @Test
    void testDtoToStock() {
        // Arrange
        Inventory inventory = new Inventory();
        inventory.setInventoryId(1L);
        inventory.setName("Name");
        inventory.setPrice(10.0d);

        Stock stock = new Stock();
        stock.setCreatedAt(Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()));
        stock.setInventory(inventory);
        stock.setQuantity(1);
        stock.setStockId(1L);
        stock.setUpdatedAt(Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()));
        stock.setUserId(1L);
        when(modelMapper.map(Mockito.<Object>any(), Mockito.<Class<Stock>>any())).thenReturn(stock);

        // Act
        Stock actualDtoToStockResult = stockAvailableServiceImp.dtoToStock(new StockDto(1, "Inventory Name"));

        // Assert
        verify(modelMapper).map(isA(Object.class), isA(Class.class));
        assertSame(stock, actualDtoToStockResult);
    }

    /**
     * Method under test: {@link StockAvailableServiceImp#dtoToStock(StockDto)}
     */
    @Test
    void testDtoToStock2() {
        // Arrange
        when(modelMapper.map(Mockito.<Object>any(), Mockito.<Class<Stock>>any()))
                .thenThrow(new UserNotApprovedException("foo"));

        // Act and Assert
        assertThrows(UserNotApprovedException.class,
                () -> stockAvailableServiceImp.dtoToStock(new StockDto(1, "Inventory Name")));
        verify(modelMapper).map(isA(Object.class), isA(Class.class));
    }
}
