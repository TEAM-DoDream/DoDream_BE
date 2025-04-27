package com.dodream.core.infrastructure.cache.aspect;

import lombok.extern.log4j.Log4j2;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

@Aspect
@Component
@Log4j2
public class CustomCacheableAspect extends BaseCacheAbstract {

    public CustomCacheableAspect(RedisTemplate<String, Object> redisTemplate) {
        super(redisTemplate);
    }
}
