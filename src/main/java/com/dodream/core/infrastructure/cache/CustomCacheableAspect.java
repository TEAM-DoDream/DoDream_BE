package com.dodream.core.infrastructure.cache;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.util.Arrays;
import java.util.stream.Collectors;

@Aspect
@Component
@RequiredArgsConstructor
@Log4j2
public class CustomCacheableAspect {

    private final RedisTemplate<String, Object> redisTemplate;

    @Around("@annotation(customCacheable)")
    public Object around(ProceedingJoinPoint joinPoint, CustomCacheable customCacheable) throws Throwable {
        String cacheKey = generateCacheKey(joinPoint, customCacheable);

        ValueOperations<String, Object> operations = redisTemplate.opsForValue();
        Object cacheValue = operations.get(cacheKey);

        if(cacheValue != null) {
            log.info("[{}] 레디스 캐시 저장 확인 - cacheKey=({})",
                    joinPoint.getSignature().getDeclaringTypeName(), cacheKey);
            return cacheValue;
        }

        Object result = joinPoint.proceed();
        operations.set(cacheKey, result, Duration.ofMinutes(customCacheable.ttl()));
        log.info(
                "[{}] 레디스 캐시 저장 완료 - cacheKey=({})",
                joinPoint.getSignature().getDeclaringTypeName(), cacheKey
        );

        return result;
    }

    private String generateCacheKey(ProceedingJoinPoint joinPoint, CustomCacheable customCacheable) {
        if(!customCacheable.key().isEmpty()){
            return customCacheable.cacheName() + "::" + customCacheable.key();
        }

        String methodName = joinPoint.getSignature().getName();
        String params = Arrays.stream(joinPoint.getArgs())
                .map(arg -> arg == null ? "null" : arg.toString())
                .collect(Collectors.joining(","));

        return customCacheable.cacheName() + "::" + methodName + "(" + params + ")";
    }
}
