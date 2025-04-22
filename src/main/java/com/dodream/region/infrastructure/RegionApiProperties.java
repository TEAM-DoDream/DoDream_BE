package com.dodream.region.infrastructure;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "work24.region")
@Getter
@RequiredArgsConstructor
@Setter
public class RegionApiProperties {
    private String endpoint;
    private String apiKey;
}

