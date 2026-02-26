package com.employee.management.aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

@Slf4j
@Aspect
@Component
public class LoggingAspect {

    @Before("execution(* com.employee.management..*Controller.*(..)) " +
            "|| execution(* com.employee.management..*Service.*(..))")
    public void logMethodName(JoinPoint joinPoint) {
        String methodName = getMethodName(joinPoint);
        log.info("Calling {}", methodName);
    }

    private String getMethodName(JoinPoint joinPoint) {
        return joinPoint.getTarget().getClass().getSimpleName()
                + "." + joinPoint.getSignature().getName() + "()";
    }
}
