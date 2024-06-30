package com.company.dms.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.company.dms.utils.logger.CustomLogger;


/**
 * Aspect for logging method entry, exit, and exceptions.
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
     * Pointcut to capture classes annotated with @LoggableClass.
     */
    @Pointcut("@within(com.impetus.dms.utils.logger.LoggableClass)")
    public void loggableClass() {
    }

    /**
     * Pointcut to capture methods annotated with @LoggableMethod.
     */
    @Pointcut("@annotation(com.impetus.dms.utils.logger.LoggableMethod)")
    public void loggableMethod() {
    }

    /**
     * Pointcut to exclude methods annotated with @NotLoggableMethod from logging.
     */
    @Pointcut("@annotation(com.impetus.dms.utils.logger.NotLoggableMethod)")
    public void notLoggableMethod() {
    }

    /**
     * Advice to log method entry before executing methods annotated with @LoggableMethod.
     */
    @Before("loggableMethod() && !notLoggableMethod()")
    public void logMethodEntry(JoinPoint joinPoint) {
        Class<?> className = joinPoint.getTarget().getClass();
        String methodName = joinPoint.getSignature().getName();
        customLogger.logMethodEntry(className, methodName);
    }

    /**
     * Advice to log method and class name before executing methods in classes annotated with @LoggableClass.
     */
    @Before("loggableClass() && !notLoggableMethod()")
    public void logMethodAndClassNameBefore(JoinPoint joinPoint) {
        Class<?> className = joinPoint.getTarget().getClass();
        String methodName = joinPoint.getSignature().getName();
        customLogger.logMethodAndClassNameBefore(className, methodName);
    }


    /**
     * Advice to log method exit after executing methods annotated with @LoggableMethod or classes annotated with @LoggableClass.
     */
    @AfterReturning(pointcut = "loggableClass()||loggableMethod()", returning = "returnValue")
    public void logMethodExit(JoinPoint joinPoint, Object returnValue) {
        Class<?> className = joinPoint.getTarget().getClass();
        String methodName = joinPoint.getSignature().getName();
        customLogger.logMethodAndClassNameAfter(className, methodName, returnValue);
    }

    /**
     * Advice to log exceptions thrown by methods annotated with @LoggableMethod or classes annotated with @LoggableClass.
     */
    @AfterThrowing(pointcut = "(loggableClass() || loggableMethod()) && !notLoggableMethod()", throwing = "exception")
    public void logException(JoinPoint joinPoint, Exception exception) {
        Class<?> className = joinPoint.getTarget().getClass();
        String methodName = joinPoint.getSignature().getName();
        customLogger.logException(className, methodName, exception);
    }
}
