package com.company.dms.utils.logger;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
/**
 * Utility class for custom logging operations.
 */
@Component
public class CustomLogger {

    /**
     * Retrieves the logger for the specified class.
     *
     * @param className The class for which the logger is requested
     * @return Logger instance for the specified class
     */
    public Logger getLogger(Class<?> className) {
        return LoggerFactory.getLogger(className);
    }

    /**
     * Logs method entry.
     *
     * @param className  The class where the method is defined
     * @param methodName The name of the method being entered
     */
    public void logMethodEntry(Class<?> className, String methodName) {
        getLogger(className).info("Entering method: {}", methodName);
    }

    /**
     * Logs method return.
     *
     * @param className   The class where the method is defined
     * @param methodName  The name of the method being exited
     * @param returnValue The return value of the method
     */
    public void logMethodReturning(Class<?> className, String methodName, Object returnValue) {
        getLogger(className).info("Exiting method: {}  . Return value: {}", methodName, returnValue);
    }

    /**
     * Logs exception.
     *
     * @param className  The class where the exception occurred
     * @param methodName The name of the method where the exception occurred
     * @param exception  The exception object
     */
    public void logException(Class<?> className, String methodName, Exception exception) {
        getLogger(className).error("Exception in METHOD : {}, Class: {}  ,Exception :{}", methodName, className.getSimpleName(), exception.getMessage());
    }

    /**
     * Logs method entry along with class name.
     *
     * @param className  The class where the method is defined
     * @param methodName The name of the method being entered
     */
    public void logMethodAndClassNameBefore(Class<?> className, String methodName) {
        getLogger(className).info("ENTERING  METHOD  : {}, Class   : {}", methodName, className.getSimpleName());
    }


    /**
     * Logs method exit along with class name and return value.
     *
     * @param className   The class where the method is defined
     * @param methodName  The name of the method being exited
     * @param returnValue The return value of the method
     */
    public void logMethodAndClassNameAfter(Class<?> className, String methodName, Object returnValue) {
        getLogger(className).info("EXIT  METHOD : {}, Class: {}  ,Return :{}", methodName, className.getSimpleName(), returnValue);
    }
}