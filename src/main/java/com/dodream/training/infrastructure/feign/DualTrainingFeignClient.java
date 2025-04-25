package com.dodream.training.infrastructure.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "dualclient", url="${work24.url}")
public interface DualTrainingFeignClient {
    @GetMapping("${work24.dual.endpoint.list}")
    String getDualTrainingList(
            @RequestParam(name="authKey") String authKey,
            @RequestParam(name="returnType") String returnType,
            @RequestParam(name="outType") String outType,
            @RequestParam(name="pageNum") String pageNum,
            @RequestParam(name="pageSize") String pageSize,
            @RequestParam(name="srchTraArea1", required = false) String srchTraArea1,
            @RequestParam(name="srchKeco1", required = false) String srchKeco1,
            @RequestParam(name = "srchTraStDt") String srchTraStDt,
            @RequestParam(name = "srchTraEndDt") String srchTraEndDt,
            @RequestParam(name = "sort") String sort,
            @RequestParam(name = "sortCol") String sortCol
    );

    @GetMapping("${work24.dual.endpoint.detail}")
    String getDualTrainingDetail(
            @RequestParam(name="authKey") String authKey,
            @RequestParam(name="returnType") String returnType,
            @RequestParam(name="outType") String outType,
            @RequestParam(name="srchTrprId") String srchTrprId,
<<<<<<< HEAD
            @RequestParam(name = "srchTrprDegr") String srchTrprDegr,
            @RequestParam(name = "srchTorgId") String srchTorgId
=======
            @RequestParam(name = "srchTrprDegr") String srchTrprDegr
>>>>>>> 4d30e0d (feat: 일학습병행 훈련과정 인프라 로직 작성 및 인터페이스로 책임 분리)
    );
}
