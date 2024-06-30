package com.company.dms.validation.fieldvalidation;

import java.util.regex.Pattern;

/**
 * A validator for checking if a value is a valid password.
 */
public class PasswordValidation implements FieldValidator{
    /**
     * Validates if the given value is a valid password.
     *
     * @param value The value to be validated.
     * @return {@code true} if the value is a valid password, {@code false} otherwise.
     */
    @Override
    public boolean validate(Object value) {
        if (value instanceof String) {
            String password = (String) value;
            Pattern pattern = Pattern.compile("^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=]).{8,}$");
            return pattern.matcher(password).matches();
        }
        return false;
    }
}
