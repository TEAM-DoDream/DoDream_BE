package com.dodream.job.infrastructure.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "jobDescriptionClient", url = "${work24.url}")
public interface JobDescriptionFeignClient {

    @GetMapping("${work24.job.endpoint}")
    String getJobDescription(
            @RequestParam(name = "authKey") String authKey,
            @RequestParam(name = "returnType") String returnType,
            @RequestParam(name = "target") String target,
            @RequestParam(name = "jobGb") String jobGb,
            @RequestParam(name = "jobCd") String jobCd,
            @RequestParam(name = "dtlGb") String dtlGb
    );
}
