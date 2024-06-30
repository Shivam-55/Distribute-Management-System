package com.example.inventoryservice.Validation;

/**
 * Class containing exception messages for validation errors.
 */
public class ExceptionMessage {
    /** Error message for an invalid field value. */
    public static final String INVALID_FIELD="Invalid value";

    /** Error message for a field that does not exist. */
    public static final String NOT_EXISTS="Field not exists";

    /**
     * Generates an error message for an invalid field value with the specified field name.
     * @param fieldName the name of the invalid field
     * @return the error message indicating the invalid field value
     */
    public static String invalidFieldMethod (String fieldName){
        return INVALID_FIELD +" with Field Name =>"+fieldName;
    }
}
