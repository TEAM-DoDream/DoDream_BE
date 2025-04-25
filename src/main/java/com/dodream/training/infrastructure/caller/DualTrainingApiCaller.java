package com.dodream.training.infrastructure.caller;

import com.dodream.training.exception.TrainingErrorCode;
import com.dodream.training.infrastructure.feign.DualTrainingFeignClient;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class DualTrainingApiCaller implements TrainingApiCaller{

    private final DualTrainingFeignClient dualTrainingFeignClient;

    private static final String RESPONSE_TYPE_JSON = "JSON";
    private static final String OUT_TYPE_LIST = "1";
    private static final String OUT_TYPE_DETAIL = "2";
    private static final String SORT = "DESC";
    private static final String SORT_COLUMN = "TRNG_BGDE";

    @Value("${work24.dual.api-key}")
    private String apiKey;

    @Value("${work24.page-size}")
    private int pageSize;

    @Override
    public String getListApi(String pageNum, String regionCode, String ncsCode, String startDate, String endDate) {
        try {
            return dualTrainingFeignClient.getDualTrainingList(
                    apiKey,
                    RESPONSE_TYPE_JSON,
                    OUT_TYPE_LIST,
                    pageNum,
                    String.valueOf(pageSize),
                    regionCode,
                    ncsCode,
                    startDate,
                    endDate,
                    SORT,
                    SORT_COLUMN
            );
        } catch (Exception e) {
            throw TrainingErrorCode.NOT_CONNECT_EXTERNAL_API.toException();
        }
    }

    @Override
    public String getDetailApi(String srchTrprId, String srchTrprDegr, String srchTorgId) {
        try{
            return dualTrainingFeignClient.getDualTrainingDetail(
                    apiKey,
                    RESPONSE_TYPE_JSON,
                    OUT_TYPE_DETAIL,
                    srchTrprId,
<<<<<<< HEAD
                    srchTrprDegr,
                    srchTorgId
=======
                    srchTrprDegr
>>>>>>> 4d30e0d (feat: 일학습병행 훈련과정 인프라 로직 작성 및 인터페이스로 책임 분리)
            );
        } catch(Exception e) {
            throw TrainingErrorCode.NOT_CONNECT_EXTERNAL_API.toException();
        }
    }
}
