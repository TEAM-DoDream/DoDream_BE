package com.dodream.core.infrastructure.log;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.log4j.Log4j2;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.net.URLDecoder;
import java.util.HashMap;
import java.util.Map;

@Aspect
@Log4j2
@Component
public class LoggingAspect {

    @Pointcut("execution(* com.dodream..presentation.controller..*(..))")
    public void controller() {}

    @Around("controller()")
    public Object logCOntroller(ProceedingJoinPoint joinPoint) throws Throwable {

        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        long startTime = System.currentTimeMillis();
        Object result = null;

        try{
            String controllerName = joinPoint.getSignature().getDeclaringType().getName();
            String methodName = joinPoint.getSignature().getName();
            String decodedUri = URLDecoder.decode(request.getRequestURI(), "UTF-8");

            result = joinPoint.proceed();
            long endTime = System.currentTimeMillis();
            log.info("[{}] {} method : {}.{} time: {}ms",
                    request.getMethod(), decodedUri, controllerName, methodName, endTime - startTime);
        } catch(Exception e) {
            log.error(e.getMessage(), e);
        }

        return result;
    }
}
