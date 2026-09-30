package com.vishal.picontrolcentre.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class GlancesClientAspect {

    @Around("execution(* com.vishal.picontrolcentre.client.*(..))")
    public Object GlancesJoinPointAspect(ProceedingJoinPoint joinPoint) throws Throwable {

        System.out.println("Collector process has started.");

        Object result = joinPoint.proceed();

        System.out.println("Collector process has finished.");

        return result;
    }

}
