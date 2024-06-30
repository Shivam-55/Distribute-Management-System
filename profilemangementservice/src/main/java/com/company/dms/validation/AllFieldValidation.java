package com.company.dms.validation;

import java.util.Map;

import com.company.dms.validation.fieldvalidation.*;
/**
 * Utility class for field validation.
 */
public class AllFieldValidation {
    /**
     * Returns a map of field names and their corresponding field validators.
     *
     * @return A map containing field names as keys and FieldValidator instances as values.
     */
    public static Map<String, FieldValidator> fieldValidation() {
        return Map.of(
                "username", new NameValidation(),
                "password", new PasswordValidation(),
                "role", new RoleValidation(),
                "mobile", new MobileValidation(),
                "email", new EmailValidation(),
                "otp", new OtpValidation(),
                "userId", new IdValidation(),
                "area",new AreaValidation(),
                "pinCode", new PinCodeValidation()
        );
    }
}