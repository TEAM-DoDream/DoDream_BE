package com.dodream.common.infrastructure;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "regionApi", url = "${work24.url}")
public interface CommonFeignClient {
    @GetMapping("${work24.common.endpoint}")
    String getCommonCode(
            @RequestParam("authKey") String authKey,
            @RequestParam("returnType") String returnType,
            @RequestParam("outType") String outType,
            @RequestParam("srchType") String srchType,
            @RequestParam("srchOption1") String srchOption1,
            @RequestParam("srchOption2") String srchOption2
    );
}
