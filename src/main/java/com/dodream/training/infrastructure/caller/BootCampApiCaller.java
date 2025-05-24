package com.dodream.training.infrastructure.caller;

import com.dodream.core.infrastructure.cache.annotation.CustomCacheableWithLock;
import com.dodream.training.exception.TrainingErrorCode;
import com.dodream.training.infrastructure.feign.BootcampFeignClient;
import com.dodream.training.presentation.value.SortBy;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class BootCampApiCaller implements TrainingApiCaller{

    private final BootcampFeignClient bootcampFeignClient;

    private static final String RESPONSE_TYPE_JSON = "JSON";
    private static final String OUT_TYPE_LIST = "1";
    private static final String OUT_TYPE_DETAIL = "2";
    private static final String SORT = "DESC";
    private static final String SORT_COLUMN = "TRNG_BGDE";

    @Value("${work24.bootcamp.api-key}")
    private String apiKey;

    @Value("${work24.page-size}")
    private int pageSize;

    @Override
    @CustomCacheableWithLock(cacheName = "bootcampList", ttl = 15)
    public String getListApi(
            String pageNum, String regionCode, String ncsCode, SortBy sortBy
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
                    sortBy.getSort(),
                    sortBy.getSortCol()
            );
        } catch (Exception e) {
            throw TrainingErrorCode.NOT_CONNECT_EXTERNAL_API.toException();
        }
    }

    @Override
    @CustomCacheableWithLock(cacheName = "bootcampDetail", ttl = 360)
    public String getDetailApi(
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
