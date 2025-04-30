package com.dodream.core.infrastructure.cache.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface CustomCacheableWithLock {
    String cacheName();
    String key() default "";
    long ttl() default 5;
    long waitTime() default 10;
    long leaseTime() default 30;
}
