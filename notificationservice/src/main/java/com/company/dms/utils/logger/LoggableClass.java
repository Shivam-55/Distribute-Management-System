package com.company.dms.utils.logger;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
/**
 * Annotation used to mark classes for logging.
 * This annotation is applied to classes to indicate that logging should be enabled for them.
 */

@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface LoggableClass {
}
