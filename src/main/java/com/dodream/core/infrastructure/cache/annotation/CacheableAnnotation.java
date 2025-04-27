package com.dodream.core.infrastructure.cache.annotation;

public interface CacheableAnnotation {
    String cacheName();
    String key();
    long ttl();
}
