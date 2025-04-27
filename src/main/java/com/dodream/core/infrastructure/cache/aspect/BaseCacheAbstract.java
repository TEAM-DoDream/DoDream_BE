package com.dodream.core.infrastructure.cache.aspect;

import com.dodream.core.exception.GlobalErrorCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.aspectj.lang.ProceedingJoinPoint;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;

import java.lang.annotation.Annotation;
import java.time.Duration;
import java.util.Arrays;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Log4j2
public abstract class BaseCacheAbstract {
    protected final RedisTemplate<String, Object> redisTemplate;
    protected final RedissonClient redissonClient;

    protected Object handleCache(ProceedingJoinPoint joinPoint, Annotation annotation, boolean useLock) throws Throwable {
        String cacheKey = generateCacheKey(joinPoint, annotation);
        ValueOperations<String, Object> operations = redisTemplate.opsForValue();

        log.info("[{}] key: {}", joinPoint.getSignature().getDeclaringTypeName(), cacheKey);

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

        log.info("캐시 이름 : {} 캐시 키 : {}", cacheName, cacheKey);

        if(cacheName == null){
            throw GlobalErrorCode.CANNOT_GET_CACHE_NAME.toException();
        }

        if(cacheKey == null){
            return cacheName + "::" + cacheKey;
        }

        String methodName = joinPoint.getSignature().getName();
        String params = Arrays.stream(joinPoint.getArgs())
                .map(args -> args == null ? "null" : args.toString())
                .collect(Collectors.joining(","));

        return cacheName + "::" + methodName + "(" + params + ")";
    }
}
