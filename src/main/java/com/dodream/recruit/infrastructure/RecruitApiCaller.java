package com.dodream.recruit.infrastructure;

import com.dodream.core.infrastructure.cache.annotation.CustomCacheableWithLock;
import com.dodream.recruit.exception.RecruitErrorCode;
import feign.FeignException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class RecruitApiCaller {

    private final RecruitFeignClient recruitFeignClient;

    @Value("${saramin.api-key}")
    private String accessKey;

    @Value("${saramin.page-size}")
    private int pageSize;

    private final String FIELDS = "expiration-date+count";

    @CustomCacheableWithLock(cacheName = "recruitList", ttl = 15)
    public String recruitListApiListCaller(
            String keyWords, String locCd, String startDate, String endDate, int start
    ){
        try{
            return recruitFeignClient.getRecruitList(
                    accessKey,
                    keyWords,
                    locCd,
                    startDate,
                    null,   // 사람인 OpenAPI 관련 오류 해결후 null 해제
                    FIELDS,
                    start,
                    pageSize
            );
        } catch (Exception e){
            throw RecruitErrorCode.API_CONNECTION_ERROR.toException();
        }
    }
    @CustomCacheableWithLock(cacheName = "recruitDetail", ttl = 360)
    public String recruitDetailAPiCaller(
            String id
    ){
        try{
            return recruitFeignClient.getRecruitDetail(
                    accessKey, id , FIELDS
            );
        }catch (Exception e){
            throw RecruitErrorCode.API_CONNECTION_ERROR.toException();
        }
    }
}
