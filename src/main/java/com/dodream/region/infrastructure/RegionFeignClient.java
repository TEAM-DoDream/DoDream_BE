package com.dodream.region.infrastructure;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "regionApi", url = "${work24.url}")
public interface RegionFeignClient {
    @GetMapping("${work24.region.endpoint}")
    String getRegionCode(
            @RequestParam("authKey") String authKey,
            @RequestParam("returnType") String returnType,
            @RequestParam("outType") String outType,
            @RequestParam("srchType") String srchType,
            @RequestParam("srchOption1") String srchOption1,
            @RequestParam("srchOption2") String srchOption2
    );
}
