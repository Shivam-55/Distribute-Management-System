package com.company.dms.validation.fieldvalidation;

/**
 * A validator for checking if a value is a valid role (String).
 */
public class RoleValidation implements FieldValidator {
    /**
     * Validates if the given value is a valid role (String).
     *
     * @param value The value to be validated.
     * @return {@code true} if the value is a valid role, {@code false} otherwise.
     */
    @Override
    public boolean validate(Object value) {
        if (!(value instanceof String)) {
            return false;
        }
        return true;
    }
}

