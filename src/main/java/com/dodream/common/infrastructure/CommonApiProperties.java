package com.dodream.common.infrastructure;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@ConfigurationProperties(prefix = "work24.common")
@Component
@Data
public class CommonApiProperties {
    private String endpoint;
    private String apiKey;
}

