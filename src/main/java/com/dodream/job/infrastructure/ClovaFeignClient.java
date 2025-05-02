package com.dodream.job.infrastructure;

import com.dodream.job.dto.request.clova.ClovaStudioRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "clovaFeignClient", url = "${ncp.clova.url}", configuration = ClovaFeignClientConfig.class)
public interface ClovaFeignClient {

    @PostMapping("${ncp.clova.endpoint.studio}")
    String callClovaStudio(
            @RequestBody ClovaStudioRequest clovaStudioRequest
    );
}
