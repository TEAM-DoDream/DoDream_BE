package com.dodream.core.infrastructure.cache.aspect;

import com.dodream.core.exception.GlobalErrorCode;
import com.dodream.core.infrastructure.cache.annotation.CustomCacheable;
import com.dodream.core.infrastructure.cache.annotation.CustomCacheableWithLock;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Component;

import java.lang.annotation.Annotation;
import java.time.Duration;
import java.util.Arrays;
import java.util.stream.Collectors;

@Aspect
@Component
@RequiredArgsConstructor
@Log4j2
public abstract class BaseCacheAbstract {

    protected final RedisTemplate<String, Object> redisTemplate;
    protected final RedissonClient redissonClient;

    @Around("@annotation(customCacheable)")
    public Object around(
            ProceedingJoinPoint joinPoint, CustomCacheable customCacheable
    ) throws Throwable {
        return handleCache(joinPoint, customCacheable, false);
    }

    @Around("@annotation(customCacheableWithLock)")
    public Object aroundWithLock(
            ProceedingJoinPoint joinPoint, CustomCacheableWithLock customCacheableWithLock
    ) throws Throwable {
        return handleCache(joinPoint, customCacheableWithLock, true);
    }

    private Object handleCache(ProceedingJoinPoint joinPoint, Annotation annotation, boolean useLock) throws Throwable {
        String cacheKey = generateCacheKey(joinPoint, annotation);
        ValueOperations<String, Object> operations = redisTemplate.opsForValue();

        Object cacheValue = operations.get(cacheKey);
        if (cacheValue != null) {
            log.info("[{}] Redis cache hit - key: {}", joinPoint.getSignature().getDeclaringTypeName(), cacheKey);
            return cacheValue;
        }

        if (useLock) {
            return handleCacheWithLock(joinPoint, annotation, cacheKey, operations);
        } else {
            return handleCacheWithoutLock(joinPoint, annotation, cacheKey, operations);
        }
    }

    private Object handleCacheWithLock(
            ProceedingJoinPoint joinPoint,
            Annotation annotation,
            String cacheKey,
            ValueOperations<String, Object> operations
    ) throws Throwable {
        String lockKey = "lock:" + cacheKey;
        RLock lock = redissonClient.getLock(lockKey);
        boolean acquired = lock.tryLock();

        if (!acquired) {
            log.warn(
                    "[{}] Failed to acquire lock - key: {}",
                    joinPoint.getSignature().getDeclaringTypeName(), lockKey
            );
            return joinPoint.proceed();
        }

        try {
            Object cacheValue = operations.get(cacheKey);
            if (cacheValue != null) {
                log.info(
                        "[{}] Redis cache hit after lock - key: {}",
                        joinPoint.getSignature().getDeclaringTypeName(), cacheKey
                );
                return cacheValue;
            }

            Object result = joinPoint.proceed();
            Long ttlMinutes = (Long) AnnotationUtils.getValue(annotation, "ttl");

            operations.set(cacheKey, result, Duration.ofMinutes(ttlMinutes == null ? 5 : ttlMinutes));
            log.info("[{}] Redis cache saved with lock - key: {}",
                    joinPoint.getSignature().getDeclaringTypeName(), cacheKey
            );

            return result;
        } finally {
            lock.unlock();
        }
    }

    private Object handleCacheWithoutLock(
            ProceedingJoinPoint joinPoint,
            Annotation annotation,
            String cacheKey,
            ValueOperations<String, Object> operations
    ) throws Throwable {
        Object result = joinPoint.proceed();
        Long ttlMinutes = (Long) AnnotationUtils.getValue(annotation, "ttl");

        operations.set(cacheKey, result, Duration.ofMinutes(ttlMinutes == null ? 5 : ttlMinutes));
        log.info("[{}] Redis cache saved - key: {}", joinPoint.getSignature().getDeclaringTypeName(), cacheKey);

        return result;
    }

    protected String generateCacheKey(ProceedingJoinPoint joinPoint, Annotation annotation){
        Object cacheName = AnnotationUtils.getValue(annotation, "cacheName");
        Object cacheKey = AnnotationUtils.getValue(annotation, "key");

        if(cacheName == null){
            throw GlobalErrorCode.CANNOT_GET_CACHE_NAME.toException();
        }

        if(cacheKey != null && cacheKey.toString().isEmpty()){
            return cacheName + "::" + cacheKey;
        }

        String methodName = joinPoint.getSignature().getName();
        String params = Arrays.stream(joinPoint.getArgs())
                .map(args -> args == null ? "null" : args.toString())
                .collect(Collectors.joining(","));

        return cacheName + "::" + methodName + "(" + params + ")";
    }
}
