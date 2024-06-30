package com.company.dms.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.company.dms.utils.logger.CustomLogger;

/**
 * Aspect class for logging method entry, exit, and exceptions.
 * This aspect is used to log method entry, exit, and exceptions for classes and methods annotated with specific logging annotations.
 */
@Aspect
@Component
public class LoggerAspect {
    private final CustomLogger customLogger;
    @Autowired
    public LoggerAspect(CustomLogger customLogger){
        this.customLogger = customLogger;
    }

    /**
     * Pointcut definition to capture classes annotated with @LoggableClass.
     */
    @Pointcut("@within(com.impetus.dms.utils.logger.LoggableClass)")
    public void loggableClass() {
    }

    /**
     * Pointcut definition to capture methods annotated with @LoggableMethod.
     */
    @Pointcut("@annotation(com.impetus.dms.utils.logger.LoggableMethod)")
    public void loggableMethod() {
    }

    /**
     * Pointcut definition to capture methods annotated with @NotLoggableMethod.
     */
    @Pointcut("@annotation(com.impetus.dms.utils.logger.NotLoggableMethod)")
    public void notLoggableMethod() {
    }

    /**
     * Advice method to log method entry before execution.
     * Logs the entry of methods annotated with @LoggableMethod and not annotated with @NotLoggableMethod.
     * @param joinPoint The JoinPoint representing the intercepted method execution
     */
    @Before("loggableMethod() && !notLoggableMethod()")
    public void logMethodEntry(JoinPoint joinPoint) {
        Class<?> className = joinPoint.getTarget().getClass();
        String methodName = joinPoint.getSignature().getName();
        customLogger.logMethodEntry(className, methodName);
    }

    /**
     * Advice method to log method and class name before execution.
     * Logs the method and class name before execution for classes annotated with @LoggableClass and not annotated with @NotLoggableMethod.
     * @param joinPoint The JoinPoint representing the intercepted method execution
     */
    @Before("loggableClass() && !notLoggableMethod()")
    public void logMethodAndClassNameBefore(JoinPoint joinPoint) {
        Class<?> className = joinPoint.getTarget().getClass();
        String methodName = joinPoint.getSignature().getName();
        customLogger.logMethodAndClassNameBefore(className, methodName);
    }

    /**
     * Advice method to log method exit after successful execution.
     * Logs the exit of methods annotated with @LoggableMethod and classes annotated with @LoggableClass.
     * @param joinPoint The JoinPoint representing the intercepted method execution
     * @param returnValue The return value of the intercepted method
     */
    @AfterReturning(pointcut = "loggableClass()||loggableMethod()", returning = "returnValue")
    public void logMethodExit(JoinPoint joinPoint, Object returnValue) {
        Class<?> className = joinPoint.getTarget().getClass();
        String methodName = joinPoint.getSignature().getName();
        customLogger.logMethodAndClassNameAfter(className, methodName, returnValue);
    }

    /**
     * Advice method to log exceptions thrown during method execution.
     * Logs exceptions thrown by methods annotated with @LoggableMethod and classes annotated with @LoggableClass.
     * @param joinPoint The JoinPoint representing the intercepted method execution
     * @param exception The exception thrown during method execution
     */
    @AfterThrowing(pointcut = "(loggableClass() || loggableMethod()) && !notLoggableMethod()", throwing = "exception")
    public void logException(JoinPoint joinPoint, Exception exception) {
        Class<?> className = joinPoint.getTarget().getClass();
        String methodName = joinPoint.getSignature().getName();
        customLogger.logException(className, methodName, exception);
    }
}
