package com.dodream.recruit.infrastructure;

import com.dodream.core.infrastructure.cache.annotation.CustomCacheableWithLock;
import com.dodream.recruit.exception.RecruitErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class RecruitApiCaller {

    private final RecruitFeignClient recruitFeignClient;

    @Value("${saramin.api-key}")
    private String accessKey;

    @Value("${saramin.page-size}")
    private int pageSize;

    private final String FIELDS = "count";

    @CustomCacheableWithLock(cacheName = "recruitList", ttl = 3)
    public String recruitListApiListCaller(
            String keyWords, String locCd, int start
    ){
        try{
            return recruitFeignClient.getRecruitList(
                    accessKey,
                    keyWords,
                    locCd,
                    FIELDS,
                    start,
                    pageSize
            );
        } catch (Exception e){
            throw RecruitErrorCode.API_CONNECTION_ERROR.toException();
        }
    }
    @CustomCacheableWithLock(cacheName = "recruitDetail", ttl = 60)
    public String recruitDetatilAPiCaller(
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
