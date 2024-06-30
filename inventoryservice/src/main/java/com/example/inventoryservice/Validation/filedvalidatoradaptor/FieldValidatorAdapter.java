package com.example.inventoryservice.Validation.filedvalidatoradaptor;

import com.example.inventoryservice.Validation.ExceptionMessage;
import com.example.inventoryservice.Validation.fieldvalidation.FieldValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Field;
import java.util.HashSet;
import java.util.Map;

/**
 * Adapter class for validating fields using FieldValidator implementations.
 */
public class FieldValidatorAdapter {
    private Map<String, FieldValidator> fieldValidators;
    private Class<?> aClass;
    /**
     * Constructor for FieldValidatorAdapter.
     * @param fieldValidators a map of field names to FieldValidator implementations
     * @param aClass the class to which the fields belong
     */
    public FieldValidatorAdapter(Map<String, FieldValidator> fieldValidators,Class<?>aClass) {
        this.fieldValidators = fieldValidators;
        this.aClass =aClass;
    }

    /**
     * Validates fields against the provided FieldValidator implementations.
     * @param fields a map of field names to values
     * @param aClass the class to which the fields belong
     * @return a message indicating the validation result
     */
    public String validateFields(Map<String, Object> fields, Class<?> aClass) {
        Logger logger = LoggerFactory.getLogger(getClass());
        // Check if all fields in the map exist in the class
        HashSet<String> set = new HashSet<>(fields.keySet());
        StringBuilder missingFields = new StringBuilder("Fields that are not exist in your Request body:");

        for (Field field : aClass.getDeclaredFields()) {
            if (!set.contains(field.getName())) {
                missingFields.append(field.getName()).append(",");
            }
        }
        // If missing fields found, return the message
        if (missingFields.length() > "Fields that are not exist in your Request body:".length()) {
            return missingFields.toString();
        }
        // Validate each field in the map
        for (Map.Entry<String, Object> entry : fields.entrySet()) {
            String fieldName = entry.getKey();
            Object value = entry.getValue();

            try {
                Field field = aClass.getDeclaredField(fieldName);
                FieldValidator validator = fieldValidators.get(fieldName);
                if (validator == null || !validator.validate(value)) {
                    logger.info("fieldName {} Validator {}",fieldName , validator);
                    logger.error("Field Name {} value {}", fieldName , value);
                    return ExceptionMessage.invalidFieldMethod(fieldName);
                }
            } catch (NoSuchFieldException e) {
                logger.error("Field {} not found in class {}" , fieldName , aClass.getName());
                return ExceptionMessage.NOT_EXISTS + " " + fieldName;
            }
        }
        return "OK";
    }
}
