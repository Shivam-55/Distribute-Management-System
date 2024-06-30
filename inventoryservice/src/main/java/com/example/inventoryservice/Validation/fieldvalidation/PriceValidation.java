package com.example.inventoryservice.Validation.fieldvalidation;
/**
 * Implementation of FieldValidator for validating prices.
 */
public class PriceValidation implements FieldValidator{
    /**
     * Validates a price value.
     * @param value the value to validate
     * @return true if the value is a non-negative double, false otherwise
     */
    @Override
    public boolean validate(Object value) {
        if (!(value instanceof Double price)) {
            return false;
        }
        return price >= 0.0;
    }

}
