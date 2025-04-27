package com.dodream.core.infrastructure.cache.aspect;

import com.dodream.core.exception.GlobalErrorCode;
import com.dodream.core.infrastructure.cache.annotation.CustomCacheable;
import com.dodream.core.infrastructure.cache.annotation.CustomCacheableWithLock;
import lombok.extern.log4j.Log4j2;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.util.concurrent.TimeUnit;

@Aspect
@Log4j2
@Component
public class CustomCacheableWithLockAspect extends BaseCacheAbstract{

    private final RedissonClient redissonClient;

    public CustomCacheableWithLockAspect(RedisTemplate<String, Object> redisTemplate, RedissonClient redissonClient) {
        super(redisTemplate);
        this.redissonClient = redissonClient;
    }

    @Around("@annotation(customCacheableWithLock)")
    public Object around(ProceedingJoinPoint joinPoint, CustomCacheableWithLock customCacheableWithLock) throws Throwable {
        String cacheKey = generateCacheKey(joinPoint, customCacheableWithLock);

        ValueOperations<String, Object> operations = redisTemplate.opsForValue();
        Object cacheValue = operations.get(cacheKey);

        if (cacheValue != null) {
            log.info("[{}] 레디스 캐시 저장 확인 - cacheKey=({})",
                    joinPoint.getSignature().getDeclaringTypeName(), cacheKey);
            return cacheValue;
        }

        String lockKey = "lock:" + cacheKey;
        RLock lock = redissonClient.getLock(lockKey);

        try {
            boolean isLocked = lock.tryLock(5, 10, TimeUnit.SECONDS);
            if (!isLocked) {
                log.warn("[{}] 락 획득 실패!! - Lock Key : {}",
                        joinPoint.getSignature().getDeclaringTypeName(), lockKey);
            }

            cacheValue = operations.get(cacheKey);
            if (cacheValue != null) {
                log.info("[{}] 레디스 캐시 저장 확인 - cacheKey=({})",
                        joinPoint.getSignature().getDeclaringTypeName(), cacheKey);
                return cacheValue;
            }

            Object result = joinPoint.proceed();
            operations.set(cacheKey, result, Duration.ofMinutes(customCacheableWithLock.ttl()));
            log.info("[{}] 레디스 캐시 저장 완료 - cacheKey=({})",
                    joinPoint.getSignature().getDeclaringTypeName(), cacheKey);
            return result;

        } catch (Exception e) {
            log.error("[{}] 분산락 획득 중 오류 발생", joinPoint.getSignature().getName());
            throw GlobalErrorCode.INTERNAL_SERVER_ERROR.toException();
        } finally {
            lock.unlock();
        }
    }
}
