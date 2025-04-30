package com.dodream.auth.application;

import com.dodream.auth.infrastructure.JwtProperties;
import java.util.concurrent.TimeUnit;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RefreshTokenService {

    private final RedisTemplate<String, Object> redisTemplate;
    private final JwtProperties jwtProperties;

    private static final String PREFIX = "RT:";

    public void save(Long memberId, String refreshToken) {
        long ttl = jwtProperties.getRefreshExpiration();
        redisTemplate.opsForValue()
            .set(PREFIX + String.valueOf(memberId), refreshToken, ttl, TimeUnit.DAYS);
    }

    public boolean isValid(Long memberId, String token) {
        Object stored = redisTemplate.opsForValue().get(PREFIX + memberId);
        return token.equals(stored);
    }

    public void delete(Long memberId) {
        redisTemplate.delete(PREFIX + memberId);
    }

}
