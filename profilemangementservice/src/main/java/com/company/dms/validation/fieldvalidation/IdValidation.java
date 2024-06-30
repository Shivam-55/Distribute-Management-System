package com.company.dms.validation.fieldvalidation;
/**
 * A validator for checking if a value is a positive integer (ID).
 */
public class IdValidation implements FieldValidator{
    /**
     * Validates if the given value is a positive integer.
     *
     * @param value The value to be validated.
     * @return {@code true} if the value is a positive integer, {@code false} otherwise.
     */
    @Override
    public boolean validate(Object value) {
        if (!(value instanceof Integer id)) {
            return false;
        }
        return id > 0;
    }
}
