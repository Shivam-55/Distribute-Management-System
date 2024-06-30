package com.company.dms.validation.fieldvalidation;
/**
 * Validates quantities as positive integers.
 */
public class QuantityValidation implements FieldValidator{
    /**
     * Validates the provided value as a quantity.
     *
     * @param value The value to validate.
     * @return true if the value is a positive integer, false otherwise.
     */
    @Override
    public boolean validate(Object value) {
        if (value instanceof Integer) {
            int intValue = (int) value;
            return intValue > 0;
        }
        return false;
    }

}
