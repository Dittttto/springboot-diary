package com.example.diary.global.logging;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Slf4j
@Aspect
@Component
public class LoggingAspect {
    @Pointcut("execution(* com.example.diary..*.*(..))")
    private void publicMethodsFromLoggingPackage() {
    }

    @Before(value = "publicMethodsFromLoggingPackage()")
    public void logBefore(JoinPoint joinPoint) {
        log.info("[{}] BEFORE PROCESS: {}", UUID.randomUUID(), joinPoint.getSignature().getName());
    }

    @Around(value = "publicMethodsFromLoggingPackage()")
    public Object logAround(ProceedingJoinPoint joinPoint) throws Throwable {
        UUID uuid = UUID.randomUUID();
        log.info("[{}] AROUND BEFORE: {}", uuid, joinPoint.getSignature().getName());
        Object result = joinPoint.proceed();
        log.info("[{}] AROUND AFTER: {}", uuid, joinPoint.getSignature().getName());
        return result;
    }

    @AfterThrowing(pointcut = "publicMethodsFromLoggingPackage()", throwing = "exception")
    public void logException(JoinPoint joinPoint, Exception exception) {
        log.error("[{}] INTERNAL EXCEPTION: ", UUID.randomUUID(), exception);
    }
}
