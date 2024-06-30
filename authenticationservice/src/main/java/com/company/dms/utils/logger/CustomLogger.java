package com.company.dms.utils.logger;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * Utility class for logging method entry, exit, and exceptions.
 */
@Component
public class CustomLogger {

    /**
     * Retrieves the logger for the specified class.
     *
     * @param className the class for which to retrieve the logger
     * @return the logger instance
     */
    public Logger getLogger(Class<?> className) {
        return LoggerFactory.getLogger(className);
    }

    /**
     * Logs the entry of a method.
     *
     * @param className the class containing the method
     * @param methodName the name of the method
     */
    public void logMethodEntry(Class<?> className, String methodName) {
        getLogger(className).info("Entering method: {}", methodName);
    }

    /**
     * Logs the exit of a method along with the return value.
     *
     * @param className the class containing the method
     * @param methodName the name of the method
     * @param returnValue the return value of the method
     */
    public void logMethodReturning(Class<?> className, String methodName, Object returnValue) {
        getLogger(className).info("Exiting method: {}  . Return value: {}", methodName, returnValue);
    }

    /**
     * Logs an exception that occurred in a method.
     *
     * @param className the class containing the method
     * @param methodName the name of the method
     * @param exception the exception that occurred
     */
    public void logException(Class<?> className, String methodName, Exception exception) {
        getLogger(className).error("Exception in METHOD : {}, Class: {}  ,Exception :{}", methodName, className.getSimpleName(), exception.getMessage());
    }

    /**
     * Logs the entry of a method along with the class name.
     *
     * @param className the class containing the method
     * @param methodName the name of the method
     */
    public void logMethodAndClassNameBefore(Class<?> className, String methodName) {
        getLogger(className).info("ENTERING  METHOD  : {}, Class   : {}", methodName, className.getSimpleName());
    }

    /**
     * Logs the exit of a method along with the class name and return value.
     *
     * @param className the class containing the method
     * @param methodName the name of the method
     * @param returnValue the return value of the method
     */
    public void logMethodAndClassNameAfter(Class<?> className, String methodName, Object returnValue) {
        getLogger(className).info("EXIT  METHOD : {}, Class: {}  ,Return :{}", methodName, className.getSimpleName(), returnValue);
    }
}