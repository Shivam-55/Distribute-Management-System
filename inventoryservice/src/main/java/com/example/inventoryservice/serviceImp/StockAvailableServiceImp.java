package com.example.inventoryservice.serviceImp;

import com.example.inventoryservice.entities.Inventory;
import com.example.inventoryservice.entities.OrderHistory;
import com.example.inventoryservice.entities.Stock;
import com.example.inventoryservice.exception.*;
import com.example.inventoryservice.repository.StockRepo;
import com.example.inventoryservice.requestDto.*;
import com.example.inventoryservice.responseDto.RequestForInventoryResDto;
import com.example.inventoryservice.service.InventoryService;
import com.example.inventoryservice.service.OrderHistoryService;
import com.example.inventoryservice.service.StockAvailableService;
import com.example.inventoryservice.customenum.IsApproved;
import com.example.inventoryservice.otherservicecall.ProfileManagementService;
import com.example.inventoryservice.otherservicecall.RequestForInventoryService;
import com.example.inventoryservice.utility.logger.LoggableClass;
import com.example.inventoryservice.utility.logger.LoggableMethod;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.List;
import java.util.Optional;

/**
 * Implementation class for the StockAvailableService interface.
 */
@LoggableClass
@Service
public class StockAvailableServiceImp implements StockAvailableService {
    private static final Logger LOGGER = LoggerFactory.getLogger(StockAvailableServiceImp.class.getName());
    private final ModelMapper mapper;
    private final StockRepo stockRepo;
    private final OrderHistoryService orderHistoryService;
    private final RequestForInventoryService requestForInventoryServiceCall;
    private final ProfileManagementService profileManagementService;
    @Autowired
    public StockAvailableServiceImp(StockRepo stockRepo, ModelMapper mapper,
                                    OrderHistoryService orderHistoryService,
                                    RequestForInventoryService requestForInventoryServiceCall,
                                    ProfileManagementService profileManagementService
                                    ){
        this.stockRepo = stockRepo;
        this.mapper = mapper;
        this.orderHistoryService = orderHistoryService;
        this.requestForInventoryServiceCall = requestForInventoryServiceCall;
        this.profileManagementService = profileManagementService;
    }

//    due to circular dependency used setter injection for StockAvailableServiceImp
//    to use InventoryService, constructor injection not working
    private InventoryService inventoryService;
    @Autowired
    public void setInventoryService(InventoryService inventoryService){
        this.inventoryService = inventoryService;
    }
    /**
     * Approves a request for inventory.
     *
     * @param approverUserId         The ID of the user approving the request.
     * @param requestForInventoryDto The request for inventory DTO containing necessary details.
     * @return The order history associated with the approved request.
     */
    @LoggableMethod
    @Override
    @Transactional
    public OrderHistory approveRequest(Long approverUserId, RequestForInventoryDto requestForInventoryDto) {
        UserDto userDto = profileManagementService.getUserWithId(approverUserId);
        if(!userDto.isActive() || IsApproved.NOTAPPROVED.equals(userDto.getIsApproved())){
            throw new UserNotApprovedException("You are not active/approved user of this organisation");
        }
        Inventory inventory = inventoryService.findInventoryByName(requestForInventoryDto.getName());
        InventoryRequestDto inventoryRequestDto = requestForInventoryServiceCall.getUserRaisedPendingRequest(requestForInventoryDto.getRequesterUserId(), inventory.getInventoryId());
        if(inventoryRequestDto.getQuantity() != requestForInventoryDto.getQuantity()){
            throw new InvalidRequestQuantityException("Entered Invalid Request Quantity");
        }
        Optional<Stock> approverAvailableStock = stockRepo.findByUserIdAndInventory(approverUserId, inventory);
        if (approverAvailableStock.isEmpty()) {
            throw new NoStockAvailableException("Stock is not available");
        }
        Stock availableStock = approverAvailableStock.get();
        if (availableStock.getQuantity() < requestForInventoryDto.getQuantity()) {
            throw new HigherQuantityDemandException("Available stock quantity is insufficient to fulfill this request");
        }

        // Distribute stock
        return distributeStock(approverUserId, requestForInventoryDto.getRequesterUserId(),
                availableStock, requestForInventoryDto.getQuantity(), inventory);
    }

    /**
     * Distributes stock to fulfill a request.
     *
     * @param approverUserId      The ID of the user approving the request.
     * @param requesterUserId     The ID of the user requesting the inventory.
     * @param approverAvailableStock The available stock for the approver.
     * @param quantity            The quantity of inventory to distribute.
     * @param inventory           The inventory being distributed.
     * @return The order history associated with the distributed stock.
     */
    @LoggableMethod
    public OrderHistory distributeStock(Long approverUserId, Long requesterUserId, Stock approverAvailableStock,
                                        int quantity, Inventory inventory) {
        // Requester stock adding
        Optional<Stock> requesterAvailableStock = stockRepo.findByUserIdAndInventory(requesterUserId, inventory);
        Stock stockToUpdate;
        if (requesterAvailableStock.isEmpty()) {
            // Create new stock entry for requester
            Stock newStock = new Stock();
            newStock.setUserId(requesterUserId);
            newStock.setQuantity(quantity);
            newStock.setInventory(inventory);
            stockToUpdate = newStock;
        } else {
            // Update existing stock entry for requester
            stockToUpdate = requesterAvailableStock.get();
            stockToUpdate.setQuantity(stockToUpdate.getQuantity() + quantity);
        }

        // Approver stock subtracting
        approverAvailableStock.setQuantity(approverAvailableStock.getQuantity() - quantity);

        // OrderHistory entity entry
        OrderHistory orderHistory = new OrderHistory();
        orderHistory.setInventory(inventory);
        orderHistory.setQuantity(quantity);
        orderHistory.setRemark("Order of "+ inventory.getName()+"with quantity "+quantity+" placed successfully");
        orderHistory.setRequesterUserId(requesterUserId);
        orderHistory.setApproverUserId(approverUserId);

//      update the inventory-request(status) table
        RequestForInventoryResDto requestStatus = new RequestForInventoryResDto();
        requestStatus.setApproverUserId(approverUserId);
        requestStatus.setRequesterUserId(requesterUserId);
        requestStatus.setInventoryId(inventory.getInventoryId());
        requestStatus.setQuantity(quantity);
        requestStatus.setName(inventory.getName());

//        updating status in inventory request
        requestForInventoryServiceCall.updateStatusInRequest(requestStatus)
                .subscribe(
                        response -> LOGGER.info("Response received:{} ", response),
                        error -> {
                            throw new NoSuchRequestException("No such request is made by you \n"+error.getMessage());
                        }
                );
        LOGGER.info("Status Update Successfully of request\n");
        stockRepo.save(stockToUpdate);
        stockRepo.save(approverAvailableStock);
        return orderHistoryService.saveOrderHistory(orderHistory);
    }

    /**
     * Saves a stock entry.
     *
     * @param stock The stock entry to be saved.
     */
    @LoggableMethod
    @Override
    public void saveStock(Stock stock) {
        stockRepo.save(stock);
    }

    /**
     * Finds a stock entry by user ID and inventory.
     *
     * @param userId    The ID of the user.
     * @param inventory The inventory for which to find the stock.
     * @return An optional containing the stock entry, if found.
     */
    @Override
    public Optional<Stock> findByUserIdAndInventory(Long userId, Inventory inventory) {
        return stockRepo.findByUserIdAndInventory(userId, inventory);
    }

//    removing all user's stock of type inventory

    /**
     * Removes all stock entries associated with a particular inventory.
     *
     * @param inventory The inventory for which to remove stock entries.
     * @return A list of removed stock entries.
     */
    @LoggableMethod
    @Override
    public List<Stock> deleteByInventory(Inventory inventory) {
        return stockRepo.deleteAllByInventory(inventory);
    }

//    admin adding self stock while adding inventory

    /**
     * Adds stock to the available stock for an inventory.
     *
     * @param inventory The inventory for which to add stock.
     * @param quantity  The quantity of stock to add.
     * @return The updated quantity of stock for the inventory.
     */
    @LoggableMethod
    @Override
    public int addToStockAvailable(Inventory inventory, int quantity) {
        Optional<Stock> stock = findByUserIdAndInventory(1L, inventory);
        if (stock.isEmpty()) {
            Stock newStock = new Stock();
            newStock.setInventory(inventory);
            newStock.setQuantity(quantity);
            newStock.setUserId(1L);
            saveStock(newStock);
            return quantity;
        } else {
            stock.get().setQuantity(stock.get().getQuantity() + quantity);
            saveStock(stock.get());
            return stock.get().getQuantity();
        }
    }

    /**
     * Retrieves all available stock entries for a user.
     *
     * @param userId The ID of the user.
     * @return A list of available stock entries.
     */
    @LoggableMethod
    @Override
    public List<Stock> viewAllStockAvailable(Long userId) {
        Optional<List<Stock>> optionalInventories = stockRepo.findByUserId(userId);
        if(optionalInventories.isEmpty())
            throw new NoStockAvailableException("Sorry!!! Your  stock list is empty");
        return optionalInventories.get();
    }

    /**
     * Retrieves the stock entry for a particular inventory.
     *
     * @param inventoryName The name of the inventory.
     * @param userId         The ID of the user.
     * @return The stock DTO representing the inventory.
     */
    @LoggableMethod
    @Override
    public StockDto viewXStock(String inventoryName, Long userId) {
        Inventory inventory = inventoryService.findInventoryByName(inventoryName);
        Optional<Stock> availStock = stockRepo.findByUserIdAndInventory(userId,inventory);
        if(availStock.isEmpty()){
            throw new NoStockAvailableException("No stock Available with such inventory name");
        }
        return stockToDto(availStock.get());
    }

    /**
     * Updates the stock quantity for a retailer.
     *
     * @param userId                        The ID of the retailer.
     * @param inventoryUpdateRetailerDto The DTO containing updated stock information.
     * @return The updated stock DTO.
     */
    @LoggableMethod
    @Override
    public StockDto stockUpdateForRetailer(Long userId, InventoryUpdateRetailerDto inventoryUpdateRetailerDto) {
        UserDto user = profileManagementService.getUserWithId(userId);
        if(!user.isActive() || IsApproved.NOTAPPROVED.equals(user.getIsApproved())){
            throw new NotActiveUserException("Not active/approved user of organisation");
        }
        Inventory inventory = inventoryService.findInventoryByName(inventoryUpdateRetailerDto.getName());
        Stock stock = stockRepo.findByUserIdAndInventory(userId,inventory).orElseThrow(()->new NoSuchInventoryPresentException("No such inventory stock available"));
        if(stock.getQuantity()<inventoryUpdateRetailerDto.getQuantity()){
            throw new InvalidQuantityEnteredException("quantity is higher than available");
        }
        stock.setQuantity(stock.getQuantity()-inventoryUpdateRetailerDto.getQuantity());
        return stockToDto(stockRepo.save(stock));
    }

    /**
     * Maps a stock entity to a stock DTO.
     *
     * @param stock The stock entity to be mapped.
     * @return The corresponding stock DTO.
     */
    public StockDto stockToDto(Stock stock) {
        return this.mapper.map(stock, StockDto.class);
    }

    /**
     * Maps a stock DTO to a stock entity.
     *
     * @param stockDto The stock DTO to be mapped.
     * @return The corresponding stock entity.
     */
    public Stock dtoToStock(StockDto stockDto) {
        return this.mapper.map(stockDto, Stock.class);
    }
}
