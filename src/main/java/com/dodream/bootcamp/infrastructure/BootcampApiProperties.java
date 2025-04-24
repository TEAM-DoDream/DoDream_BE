package com.dodream.bootcamp.infrastructure;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@ConfigurationProperties(prefix = "work24.bootcamp")
@Data
@Component
public class BootcampApiProperties {
    private String apiKey;
    private Endpoint endpoint;

    @Data
    public static class Endpoint {
        private String list;
        private String detail;
    }
}
