package com.example.inventoryservice.serviceImp;

import com.example.inventoryservice.entities.Inventory;
import com.example.inventoryservice.entities.Stock;
import com.example.inventoryservice.exception.NoAuthorityException;
import com.example.inventoryservice.exception.NoSuchInventoryPresentException;
import com.example.inventoryservice.repository.InventoryRepo;
import com.example.inventoryservice.requestDto.InventoryRequestDto;
import com.example.inventoryservice.requestDto.RequestForInventoryDto;
import com.example.inventoryservice.responseDto.InventoryDto;
import com.example.inventoryservice.responseDto.InventoryResponseDto;
import com.example.inventoryservice.service.InventoryService;
import com.example.inventoryservice.service.StockAvailableService;
import com.example.inventoryservice.utility.logger.LoggableClass;
import com.example.inventoryservice.utility.logger.LoggableMethod;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Implementation of the InventoryService interface.
 */
@LoggableClass
@Service
public class InventoryServiceImpl implements InventoryService {
    private final ModelMapper mapper;
    private final InventoryRepo inventoryRepo;
    private final StockAvailableService stockAvailableService;
    @Autowired
    public InventoryServiceImpl(InventoryRepo inventoryRepo, StockAvailableService stockAvailableService,
                                ModelMapper mapper){
        this.inventoryRepo = inventoryRepo;
        this.stockAvailableService = stockAvailableService;
        this.mapper = mapper;
    }

    /**
     * Adds inventory.
     * @param inventory The inventory request DTO.
     * @param role The role of the user.
     * @return InventoryResponseDto containing the response.
     */
    @LoggableMethod
    @Override
    public InventoryResponseDto addInventory(InventoryRequestDto inventory, String role) {
        if("admin".equalsIgnoreCase(role)){
            Inventory newInventory = dtoToInventory(inventory);
            Optional<Inventory> existInventory = inventoryRepo.findByName(newInventory.getName());
            int quantity;
            if(existInventory.isEmpty()){
                quantity=stockAvailableService.addToStockAvailable(inventoryRepo.save(newInventory),inventory.getQuantity());
            }else{
                existInventory.get().setPrice(inventory.getPrice());
                quantity=stockAvailableService.addToStockAvailable(inventoryRepo.save(existInventory.get()),inventory.getQuantity());
            }
            return inventoryToDto(newInventory,quantity);
        }else{
            throw new NoAuthorityException("You have not authority to access this functionality");
        }
    }

    /**
     * Removes inventory.
     * @param inventoryName The request DTO containing the name of the inventory to be removed.
     * @param role The role of the user.
     * @return List of Stock objects.
     */
    @LoggableMethod
    @Override
    public List<Stock> removeInventory(RequestForInventoryDto inventoryName, String role) {
        if("admin".equalsIgnoreCase(role)){
            Inventory inventory = findInventoryByName(inventoryName.getName());
            return stockAvailableService.deleteByInventory(inventory);
        }else{
            throw new NoAuthorityException("You are not authorized for this");
        }
    }

    /**
     * Finds inventory by name.
     * @param inventoryName The name of the inventory.
     * @return Inventory object.
     */
    @LoggableMethod
    @Override
    public Inventory findInventoryByName(String inventoryName) {
        Optional<Inventory> inventory = inventoryRepo.findByName(inventoryName);
        if(inventory.isEmpty()) throw new NoSuchInventoryPresentException("No such Inventory is present");
        return inventory.get();
    }

    /**
     * Finds inventory DTO by name.
     * @param inventoryName The name of the inventory.
     * @return InventoryDto containing the inventory details.
     */
    @LoggableMethod
    @Override
    public InventoryDto findInventoryDtoByName(String inventoryName) {
        return inventoryToDto(findInventoryByName(inventoryName));
    }

    /**
     * Converts inventory to inventory DTO.
     * @param inventory The inventory object.
     * @param quantity The quantity of the inventory.
     * @return InventoryResponseDto containing the inventory response.
     */
    public InventoryResponseDto inventoryToDto(Inventory inventory,int quantity) {
        InventoryResponseDto responseDto = this.mapper.map(inventory, InventoryResponseDto.class);
        responseDto.setQuantity(quantity);
        return responseDto;
    }

    /**
     * Converts inventory to inventory DTO.
     * @param inventory The inventory object.
     * @return InventoryDto containing the inventory details.
     */
    public InventoryDto inventoryToDto(Inventory inventory){
        return this.mapper.map(inventory,InventoryDto.class);
    }

    /**
     * Maps an InventoryRequestDto object to an Inventory object.
     *
     * @param inventory The inventory request DTO object to map.
     * @return Inventory representing the mapped object.
     */
    public Inventory dtoToInventory(InventoryRequestDto inventory) {
        return this.mapper.map(inventory, Inventory.class);
    }
}

