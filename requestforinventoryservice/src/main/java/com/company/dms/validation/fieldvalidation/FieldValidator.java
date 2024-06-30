package com.company.dms.validation.fieldvalidation;


/**
 * Interface for defining field validators.
 *
 * @param <T> The type of value to be validated.
 */
public interface FieldValidator<T> {

    /**
     * Validates the given value.
     *
     * @param value The value to be validated.
     * @return {@code true} if the value passes validation, {@code false} otherwise.
     */
    boolean validate(T value);
}
