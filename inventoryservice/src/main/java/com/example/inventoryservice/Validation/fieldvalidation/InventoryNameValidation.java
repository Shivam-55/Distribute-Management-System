package com.example.inventoryservice.Validation.fieldvalidation;


/**
 * Implementation of FieldValidator for validating inventory names.
 */
public class InventoryNameValidation implements FieldValidator{

    /**
     * Validates an inventory name value.
     * @param value the value to validate
     * @return true if the value contains only alphabetic characters, false otherwise
     */
    @Override
    public boolean validate(Object value) {
        if (!(value instanceof String name)) {
            return false;
        }
        String regex = "^[a-zA-Z]+$";
        return name.matches(regex);
    }
}
