package com.company.dms.validation.fieldvalidatoradaptor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.company.dms.validation.ExceptionMessage;
import com.company.dms.validation.fieldvalidation.FieldValidator;

import java.lang.reflect.Field;
import java.util.HashSet;
import java.util.Map;
/**
 * Adapter class to validate fields based on provided validators.
 */
public class FieldValidatorAdapter {
    private Map<String, FieldValidator> fieldValidators;
    private Class<?> aClass;
    /**
     * Constructs a FieldValidatorAdapter with the given field validators and class.
     *
     * @param fieldValidators The map of field names to their corresponding validators.
     * @param aClass          The class containing the fields to be validated.
     */
    public FieldValidatorAdapter(Map<String, FieldValidator> fieldValidators,Class<?>aClass) {
        this.fieldValidators = fieldValidators;
        this.aClass =aClass;
    }
    /**
     * Validates the fields in the provided map based on the class and validators.
     *
     * @param fields The map of field names to their values.
     * @param aClass The class containing the fields to be validated.
     * @return A string indicating the validation result or any missing fields.
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
                    return ExceptionMessage.invalidField(fieldName);
                }
            } catch (NoSuchFieldException e) {
                logger.error("Field {} not found in class {}" , fieldName , aClass.getName());
                return ExceptionMessage.NOT_EXISTS + " " + fieldName;
            }
        }
        return "OK";
    }
}
