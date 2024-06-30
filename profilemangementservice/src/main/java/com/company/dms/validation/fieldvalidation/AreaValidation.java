package com.company.dms.validation.fieldvalidation;

/**
 * A validator for checking if a value is an instance of String.
 */
public class AreaValidation implements FieldValidator {

    /**
     * Validates if the given value is an instance of String.
     *
     * @param value The value to be validated.
     * @return {@code true} if the value is an instance of String, {@code false} otherwise.
     */
    @Override
    public boolean validate(Object value) {
        return value instanceof String;
    }
}