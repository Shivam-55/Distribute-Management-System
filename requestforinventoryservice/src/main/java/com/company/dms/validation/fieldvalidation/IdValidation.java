package com.company.dms.validation.fieldvalidation;
/**
 * Validates ID fields as long values greater than or equal to 0 and less than or equal to Long.MAX_VALUE.
 */
public class IdValidation implements FieldValidator{
    /**
     * Validates the provided value as a long.
     *
     * @param value The value to validate.
     * @return true if the value is a valid long ID, false otherwise.
     */
    @Override
    public boolean validate(Object value) {
        if (value instanceof Long longValue) {
            return longValue >= 0 && longValue <= Long.MAX_VALUE;
        }
        return false;
    }
}
