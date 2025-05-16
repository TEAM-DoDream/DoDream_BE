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

    private final String FIELDS = "expiration-date+count+posting-date";

    private final String SORTED_BY_END_DATE = "da";
    private final String SORTED_BY_POST_DATE = "pd";

    @CustomCacheableWithLock(cacheName = "recruitList", ttl = 15)
    public String recruitListApiListCaller(
            String keyWords, String locCd, String startDate, String endDate, int start, String sortBy
    ){
        try{
            String sort = SORTED_BY_END_DATE;
            if(sortBy != null){
                sort = SORTED_BY_POST_DATE;
            }

            return recruitFeignClient.getRecruitList(
                    accessKey,
                    keyWords,
                    locCd,
                    startDate,
                    endDate,
                    FIELDS,
                    start,
                    pageSize,
                    sort
            );
        } catch (FeignException e){
            System.out.println(e.contentUTF8());
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
