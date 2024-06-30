package com.example.inventoryservice.Validation.fieldvalidation;
/**
 * Implementation of FieldValidator for validating quantity values.
 */
public class QuantityValidation implements FieldValidator{
    /**
     * Validates a quantity value.
     * @param value the value to validate
     * @return true if the value is a positive integer, false otherwise
     */
    @Override
    public boolean validate(Object value) {
        if (!(value instanceof Integer)) {
            return false;
        }
        int quantity = (Integer) value;
        return quantity > 0;
    }

}
