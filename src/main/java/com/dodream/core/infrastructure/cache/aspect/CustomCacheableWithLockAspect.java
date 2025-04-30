package com.dodream.core.infrastructure.cache.aspect;

import com.dodream.core.infrastructure.cache.annotation.CustomCacheableWithLock;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.redisson.api.RedissonClient;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class CustomCacheableWithLockAspect extends BaseCacheAbstract {

    public CustomCacheableWithLockAspect(RedisTemplate<String, Object> redisTemplate, RedissonClient redissonClient) {
        super(redisTemplate, redissonClient);
    }

    @Around("@annotation(customCacheableWithLock)")
    public Object aroundWithLock(
            ProceedingJoinPoint joinPoint, CustomCacheableWithLock customCacheableWithLock
    ) throws Throwable {
        return handleCache(joinPoint, customCacheableWithLock, true);
    }
}
