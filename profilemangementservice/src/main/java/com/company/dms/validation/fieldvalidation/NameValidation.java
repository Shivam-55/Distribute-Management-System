package com.company.dms.validation.fieldvalidation;

import java.util.regex.Pattern;

/**
 * A validator for checking if a value is a valid name.
 */
public class NameValidation implements FieldValidator{
    /**
     * Validates if the given value is a valid name.
     *
     * @param value The value to be validated.
     * @return {@code true} if the value is a valid name, {@code false} otherwise.
     */
    @Override
    public boolean validate(Object value) {
        if (value instanceof String) {
            String name = (String) value;
            Pattern pattern = Pattern.compile("^[a-zA-Z0-9_]+$");
            return pattern.matcher(name).matches();
        }
        return false;
    }
}
