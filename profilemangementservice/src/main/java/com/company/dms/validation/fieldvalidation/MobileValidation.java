package com.company.dms.validation.fieldvalidation;
/**
 * A validator for checking if a value is a valid mobile number.
 */

public class MobileValidation implements FieldValidator{
    /**
     * Validates if the given value is a valid mobile number.
     *
     * @param value The value to be validated.
     * @return {@code true} if the value is a valid mobile number, {@code false} otherwise.
     */
    @Override
    public boolean validate(Object value) {
        if (!(value instanceof Long)) {
            return false;
        }
        String strValue = Long.toString((Long) value);
        String pattern = "^(\\+\\d{1,3}[- ]?)?\\d{10}$";
        return strValue.matches(pattern);
    }

}
