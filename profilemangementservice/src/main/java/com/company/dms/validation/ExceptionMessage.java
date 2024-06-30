package com.company.dms.validation;


/**
 * Utility class for defining exception messages.
 */
public class ExceptionMessage {

    /**
     * Default message for an invalid field value.
     */
    public static final String INVALID_FIELD = "Invalid value";

    /**
     * Default message for a field that does not exist.
     */
    public static final String NOT_EXISTS = "Field not exists";

    /**
     * Constructs a custom invalid field message with the specified field name.
     *
     * @param fieldName The name of the invalid field.
     * @return A custom invalid field message including the field name.
     */
    public static String invalidField (String fieldName){
        return INVALID_FIELD + " with Field Name => " + fieldName;
    }
}