package com.company.dms.utils;

import java.lang.annotation.ElementType;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Annotation for excluding code from coverage
 */
    @Retention(RetentionPolicy.RUNTIME)
    @Target({ElementType.TYPE, ElementType.METHOD})
    public @interface ExcludeFromCodeCoverage {
        // Optionally, you can include elements in the annotation if needed
    }


