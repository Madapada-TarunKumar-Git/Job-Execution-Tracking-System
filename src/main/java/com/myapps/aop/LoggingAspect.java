package com.myapps.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class LoggingAspect {

    private static final Logger log = LoggerFactory.getLogger(LoggingAspect.class);
    // Add logging advice methods here
    @SuppressWarnings("unused")
    @Around("execution(* com.myapps.service.*.*(..))")
    public Object logServiceMethods(ProceedingJoinPoint joinPoint) throws Throwable {
        String methodName = joinPoint.getSignature().getName();
        long startTime = System.currentTimeMillis();
        log.info("Entering method: {}", methodName);
        Object result = joinPoint.proceed();
        long endTime = System.currentTimeMillis();
        log.info("Exiting method: {} | Execution time: {} ms", methodName, (endTime - startTime));
        return result;
    }
}
