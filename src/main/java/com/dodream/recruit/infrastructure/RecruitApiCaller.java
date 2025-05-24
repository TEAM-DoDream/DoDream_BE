package com.dodream.recruit.infrastructure;

import com.dodream.core.infrastructure.cache.annotation.CustomCacheableWithLock;
import com.dodream.recruit.exception.RecruitErrorCode;
import com.dodream.recruit.presentation.value.SortBy;
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
            String keyWords, String locCd, int start, String sortBy
    ){
        try{
            String sort = checkSortBy(sortBy);
            System.out.println("sort = " + sort);
            return recruitFeignClient.getRecruitList(
                    accessKey,
                    keyWords,
                    locCd,
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


    private String checkSortBy(String sortBy){
        if(SortBy.POSTED_DATE_DESC.getName().equals(sortBy)){
            return SortBy.POSTED_DATE_DESC.getCode();
        }else if(SortBy.DEADLINE_DESC.getName().equals(sortBy)){
            return SortBy.DEADLINE_DESC.getCode();
        }else{
            return SortBy.DEADLINE_ASC.getCode();
        }
    }
}
