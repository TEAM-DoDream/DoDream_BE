package com.dodream.job.infrastructure.caller;

import com.dodream.core.infrastructure.cache.annotation.CustomCacheableWithLock;
import com.dodream.job.infrastructure.feign.JobDescriptionFeignClient;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class JobApiCaller {

    private final JobDescriptionFeignClient jobDescriptionFeignClient;

    @Value("${work24.job.api-key}")
    private String apiKey;

    private static final String RETURN_TYPE = "XML";
    private static final String TARGET = "JOBDTL";
    private static final String JOB_GB = "1";
    private static final String DTL_GB = "1";

    @CustomCacheableWithLock(cacheName = "jobDescription", ttl = 360)
    private String jobDesriptionApiCaller(String jobCode){
        return jobDescriptionFeignClient.getJobDescription(
                apiKey,
                RETURN_TYPE,
                TARGET,
                JOB_GB,
                jobCode,
                DTL_GB
        );
    }
}
