package com.company.dms.validation;

import java.util.Map;

import com.company.dms.validation.fieldvalidation.*;

/**
 * Utility class for providing field validation mappings.
 */
public class AllFieldValidation {
    /**
     * Provides a map of field names and their corresponding field validators.
     *
     * @return A map containing field names as keys and their respective validators as values.
     */
    public static Map<String, FieldValidator> fieldValidation() {
        return Map.of(
                "inventoryId", new IdValidation(),
                "quantity", new QuantityValidation(),
                "name", new NameValidation()
        );
    }
}