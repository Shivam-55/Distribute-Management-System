package com.example.inventoryservice.serviceImp;

import static org.junit.jupiter.api.Assertions.assertSame;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.example.inventoryservice.entities.Inventory;
import com.example.inventoryservice.entities.OrderHistory;
import com.example.inventoryservice.repository.OrderHistoryRepo;

import java.time.LocalDate;
import java.time.ZoneOffset;
import java.util.Date;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.aot.DisabledInAotMode;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ContextConfiguration(classes = {OrderHistoryServiceImp.class})
@ExtendWith(SpringExtension.class)
@DisabledInAotMode
class OrderHistoryServiceImpTest {
    @MockBean
    private OrderHistoryRepo orderHistoryRepo;

    @Autowired
    private OrderHistoryServiceImp orderHistoryServiceImp;

    /**
     * Method under test:
     * {@link OrderHistoryServiceImp#saveOrderHistory(OrderHistory)}
     */
    @Test
    void testSaveOrderHistory() {
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
        when(orderHistoryRepo.save(Mockito.<OrderHistory>any())).thenReturn(orderHistory);

        Inventory inventory2 = new Inventory();
        inventory2.setInventoryId(1L);
        inventory2.setName("Name");
        inventory2.setPrice(10.0d);

        OrderHistory orderHistory2 = new OrderHistory();
        orderHistory2.setApproverUserId(1L);
        orderHistory2.setCreateDate(Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()));
        orderHistory2.setInventory(inventory2);
        orderHistory2.setPurchaseId(1L);
        orderHistory2.setQuantity(1);
        orderHistory2.setRemark("Remark");
        orderHistory2.setRequesterUserId(1L);

        // Act
        OrderHistory actualSaveOrderHistoryResult = orderHistoryServiceImp.saveOrderHistory(orderHistory2);

        // Assert
        verify(orderHistoryRepo).save(isA(OrderHistory.class));
        assertSame(orderHistory, actualSaveOrderHistoryResult);
    }
}
