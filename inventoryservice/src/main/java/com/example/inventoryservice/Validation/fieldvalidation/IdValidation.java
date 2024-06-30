package com.example.inventoryservice.Validation.fieldvalidation;


/**
 * Implementation of FieldValidator for validating ID fields.
 */
public class IdValidation implements FieldValidator{
    /**
     * Validates an ID value.
     * @param value the value to validate
     * @return true if the value is a positive integer, false otherwise
     */
    @Override
    public boolean validate(Object value) {
        if (!(value instanceof Integer id)) {
            return false;
        }
        return id > 0;
    }

}
