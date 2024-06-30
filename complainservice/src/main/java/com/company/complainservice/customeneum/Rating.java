package com.company.complainservice.customeneum;

import com.company.complainservice.exception.IllegalStatusArgumentException;

/**
 * Enum representing the rating values.
 */
public enum Rating {
    GOOD,
    AVERAGE,
    BAD;

    /**
     * Converts a string value to a Rating enum value.
     * @param text The string representation of the rating.
     * @return The corresponding Rating enum value.
     * @throws IllegalStatusArgumentException if the provided string is not a valid rating value.
     */
    public static Rating fromString(String text) {
        for (Rating rating : Rating.values()) {
            if (rating.name().equalsIgnoreCase(text)) {
                return rating;
            }
        }
        throw new IllegalStatusArgumentException("Invalid rating value. Allowed values are: GOOD, AVERAGE, BAD");
    }
}
