package com.dodream.training.infrastructure;

import com.dodream.training.exception.TrainingErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class TrainingApiCaller {

    private final BootcampFeignClient bootcampFeignClient;

    private static final String RESPONSE_TYPE_JSON = "JSON";
    private static final String OUT_TYPE_LIST = "1";
    private static final String OUT_TYPE_DETAIL = "2";
    private static final String SORT = "DESC";
    private static final String SORT_COLUMN = "TRNG_BGDE";

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

    public String bootcampDetailApiCaller(
            String srchTrprId,
            String srchTrprDegr,
            String srchTorgId
    ){
        try{
            return bootcampFeignClient.searchBootCampDetail(
                    apiKey,
                    RESPONSE_TYPE_JSON,
                    OUT_TYPE_DETAIL,
                    srchTrprId,
                    srchTrprDegr,
                    srchTorgId
            );
        } catch(Exception e) {
            throw TrainingErrorCode.NOT_CONNECT_EXTERNAL_API.toException();
        }
    }
}
