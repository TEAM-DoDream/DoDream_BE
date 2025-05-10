package com.dodream.core.infrastructure.cache.aspect;

import com.dodream.core.infrastructure.cache.annotation.CustomCacheable;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.redisson.api.RedissonClient;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class CustomCacheableAspect extends BaseCacheAbstract{

    public CustomCacheableAspect(RedisTemplate<String, Object> redisTemplate, RedissonClient redissonClient) {
        super(redisTemplate, redissonClient);
    }

    @Around("@annotation(customCacheable)")
    public Object around(
            ProceedingJoinPoint joinPoint, CustomCacheable customCacheable
    ) throws Throwable {
        return handleCache(joinPoint, customCacheable, false);
    }
}
