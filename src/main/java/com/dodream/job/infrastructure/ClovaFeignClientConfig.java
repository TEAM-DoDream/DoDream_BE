package com.dodream.job.infrastructure;

import feign.RequestInterceptor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.UUID;

@Configuration
public class ClovaFeignClientConfig {

    @Value("${ncp.clova.api-key}")
    private String clovaApiKey;

    @Bean
    public RequestInterceptor clovaRequestInterceptor() {
        return restTemplate -> {
            restTemplate.header("Authorization", "Bearer "+ clovaApiKey);
            restTemplate.header("X-NCP-CLOVASTUDIO-REQUEST-ID", UUID.randomUUID().toString());
            restTemplate.header("Content-Type", "application/json");
        };
    }
}
