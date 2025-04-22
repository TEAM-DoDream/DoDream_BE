package com.dodream.common.infrastructure;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "work24.common")
@Getter
@RequiredArgsConstructor
@Setter
public class CommonApiProperties {
    private String endpoint;
    private String apiKey;
}

