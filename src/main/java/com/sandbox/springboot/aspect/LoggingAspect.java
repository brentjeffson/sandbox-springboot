package com.sandbox.springboot.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class LoggingAspect {

    private static final Logger logger = LoggerFactory.getLogger(LoggingAspect.class);

    // Define a pointcut for all methods in the service package
    @Pointcut("execution(* com.sandbox.springboot.controller.*.*(..))")
    public void serviceMethods() {}

    // Around advice to log method execution details
    @Around("serviceMethods()")
    public Object logExecutionDetails(ProceedingJoinPoint joinPoint) throws Throwable {
        long start = System.currentTimeMillis();

        // Log method name and arguments
        logger.info("Method {} called with arguments: {}", joinPoint.getSignature(), joinPoint.getArgs());

        Object result;
        try {
            result = joinPoint.proceed(); // Proceed with the method execution
        } catch (Exception e) {
            // Log any exceptions
            logger.error("Exception in method {}: {}", joinPoint.getSignature(), e.getMessage());
            throw e;
        }

        long executionTime = System.currentTimeMillis() - start;

        // Log method execution time
        logger.info("Method {} executed in {} ms", joinPoint.getSignature(), executionTime);

        return result;
    }
}
