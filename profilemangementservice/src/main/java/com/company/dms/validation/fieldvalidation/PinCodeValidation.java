package com.company.dms.validation.fieldvalidation;
/**
 * A validator for checking if a value is a valid PIN code (integer).
 */
public class PinCodeValidation implements FieldValidator{
    /**
     * Validates if the given value is a valid PIN code (integer).
     *
     * @param value The value to be validated.
     * @return {@code true} if the value is a valid PIN code, {@code false} otherwise.
     */
    @Override
    public boolean validate(Object value) {
        if (value instanceof Integer integer) {
            return integer >= 0 && integer <= Integer.MAX_VALUE;
        }
        return false;
    }
}
