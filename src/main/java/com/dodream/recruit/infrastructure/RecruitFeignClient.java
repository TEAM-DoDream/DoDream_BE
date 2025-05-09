package com.dodream.recruit.infrastructure;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "recruitClient", url = "${saramin.url}")
public interface RecruitFeignClient {
    @GetMapping(value = "${saramin.endpoint}", headers = "Accept=application/json")
    String getRecruitList(
            @RequestParam(name = "access-key") String accessKey,
            @RequestParam(name = "keywords", required = false) String keywords,
            @RequestParam(name = "loc_cd", required = false) String locCd,
            @RequestParam(name = "fields") String fields,
            @RequestParam(name = "start") int start,
            @RequestParam(name = "count") int count
    );

    @GetMapping(value = "${saramin.endpoint}", headers = "Accept=application/json")
    String getRecruitDetail(
            @RequestParam(name = "access-key") String accessKey,
            @RequestParam(name = "id") String id,
            @RequestParam(name = "fields") String fields
    );
}
