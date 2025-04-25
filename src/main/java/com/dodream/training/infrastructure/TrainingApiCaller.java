package com.dodream.training.infrastructure;

import com.dodream.training.exception.TrainingErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class TrainingApiCaller {

    private final BootcampFeignClient bootcampFeignClient;

    @Value("${work24.bootcamp.api-key}")
    private String apiKey;

    @Value("${work24.bootcamp.page-size}")
    private int pageSize;

    public String bootcampListApiCaller(
            String pageNum, String regionCode, String ncsCode,
            String startDate, String endDate
    ){
        try {
            return bootcampFeignClient.searchBootCampList(
                    apiKey,
                    "JSON",
                    "1",
                    pageNum,
                    String.valueOf(pageSize),
                    regionCode,
                    ncsCode,
                    startDate,
                    endDate,
                    "DESC",    // 내림차순 정렬
                    "TRNG_BGDE"     // 훈련명으로 정렬
            );
        } catch (Exception e) {
            throw TrainingErrorCode.NOT_CONNECT_EXTERNAL_API.toException();
        }
    }

    public String bootcampDetailApiCaller(
            String srchTrprId,
            String srchTrprDegr,
            String srchTorgId
    ){
        try{
            return bootcampFeignClient.searchBootCampDetail(
                    apiKey,
                    "JSON",
                    "2",
                    srchTrprId,
                    srchTrprDegr,
                    srchTorgId
            );
        } catch(Exception e) {
            throw TrainingErrorCode.NOT_CONNECT_EXTERNAL_API.toException();
        }
    }
}
