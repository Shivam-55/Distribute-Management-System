package com.company.dms.validation.fieldvalidation;

import java.util.regex.Pattern;
/**
 * Validates names as strings containing only alphabetic characters.
 */
public class NameValidation implements FieldValidator{
    /**
     * Validates the provided value as a name.
     *
     * @param value The value to validate.
     * @return true if the value is a valid name, false otherwise.
     */
    @Override
    public boolean validate(Object value) {
        if (value instanceof String) {
            String strValue = (String) value;
            String regex = "^[a-zA-Z]*$";
            return Pattern.matches(regex, strValue);
        }
        return false;
    }
}
