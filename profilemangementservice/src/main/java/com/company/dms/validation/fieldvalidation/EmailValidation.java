package com.company.dms.validation.fieldvalidation;
/**
 * A validator for checking if a value is a valid email address.
 */
public class EmailValidation implements FieldValidator{

    /**
     * Validates if the given value is a valid email address.
     *
     * @param value The value to be validated.
     * @return {@code true} if the value is a valid email address, {@code false} otherwise.
     */
    @Override
    public boolean validate(Object value) {
        if (!(value instanceof String)) {
            return false;
        }
        String email = (String) value;
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,}$";
        return email.matches(emailRegex);
    }

}
