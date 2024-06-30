package com.example.inventoryservice.Validation;

import com.example.inventoryservice.Validation.fieldvalidation.*;

import java.util.Map;

/**
 * Class containing field validators for various fields.
 */
public class AllFieldValidation {
    /**
     * Returns a map of field validators for different fields.
     * @return a map containing field names as keys and corresponding field validators as values
     */
    public static Map<String, FieldValidator> fieldValidation() {
        return Map.of(
                "name", new InventoryNameValidation(),
                "quantity", new QuantityValidation(),
                "requesterUserId", new IdValidation(),
                "price", new PriceValidation()
        );
    }
}
