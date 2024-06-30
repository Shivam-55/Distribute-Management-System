package com.example.inventoryservice.Validation.fieldvalidation;

/**
 * Interface for field validation.
 * @param <T> the type of value to validate
 */
public interface FieldValidator<T> {
    /**
     * Validates a field value.
     * @param value the value to validate
     * @return true if the value is valid, false otherwise
     */
    boolean validate(T value);
}
