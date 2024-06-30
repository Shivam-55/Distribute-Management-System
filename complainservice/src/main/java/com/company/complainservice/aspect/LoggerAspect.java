package com.company.complainservice.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.company.complainservice.utils.logger.CustomLogger;

/**
 * AOP Aspect for logging method entry, exit, and exceptions.
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
    @Pointcut("@within(com.impetus.complainservice.utils.logger.LoggableClass)")
    public void loggableClass() {
    }

    /**
     * Pointcut to capture methods annotated with @LoggableMethod.
     */
    @Pointcut("@annotation(com.impetus.complainservice.utils.logger.LoggableMethod)")
    public void loggableMethod() {
    }

    /**
     * Pointcut to exclude methods annotated with @NotLoggableMethod.
     */
    @Pointcut("@annotation(com.impetus.complainservice.utils.logger.NotLoggableMethod)")
    public void notLoggableMethod() {
    }

    /**
     * Advice to log method entry before execution.
     *
     * @param joinPoint The join point at which the advice is applied.
     */
    @Before("loggableMethod() && !notLoggableMethod()")
    public void logMethodEntry(JoinPoint joinPoint) {
        Class<?> className = joinPoint.getTarget().getClass();
        String methodName = joinPoint.getSignature().getName();
        customLogger.logMethodEntry(className, methodName);
    }

    /**
     * Advice to log method and class name before execution.
     *
     * @param joinPoint The join point at which the advice is applied.
     */
    @Before("loggableClass() && !notLoggableMethod()")
    public void logMethodAndClassNameBefore(JoinPoint joinPoint) {
        Class<?> className = joinPoint.getTarget().getClass();
        String methodName = joinPoint.getSignature().getName();
        customLogger.logMethodAndClassNameBefore(className, methodName);
    }

    /**
     * Advice to log method exit after execution.
     *
     * @param joinPoint   The join point at which the advice is applied.
     * @param returnValue The return value of the method.
     */
    @AfterReturning(pointcut = "loggableClass()||loggableMethod()", returning = "returnValue")
    public void logMethodExit(JoinPoint joinPoint, Object returnValue) {
        Class<?> className = joinPoint.getTarget().getClass();
        String methodName = joinPoint.getSignature().getName();
        customLogger.logMethodAndClassNameAfter(className, methodName, returnValue);
    }

    /**
     * Advice to log exceptions thrown by methods.
     *
     * @param joinPoint The join point at which the advice is applied.
     * @param exception The exception thrown by the method.
     */
    @AfterThrowing(pointcut = "(loggableClass() || loggableMethod()) && !notLoggableMethod()", throwing = "exception")
    public void logException(JoinPoint joinPoint, Exception exception) {
        Class<?> className = joinPoint.getTarget().getClass();
        String methodName = joinPoint.getSignature().getName();
        customLogger.logException(className, methodName, exception);
    }
}
