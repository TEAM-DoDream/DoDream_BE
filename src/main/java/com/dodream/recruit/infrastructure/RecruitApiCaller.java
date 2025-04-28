package com.dodream.recruit.infrastructure;

import com.dodream.recruit.exception.RecruitException;
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

    public String recruitListApiListCaller(
            String keyWords, String locCd, int start
    ){
        try{
            return recruitFeignClient.getRecruitList(
                    accessKey,
                    keyWords,
                    locCd,
                    start,
                    pageSize
            );
        } catch (Exception e){
            throw RecruitException.API_CONNECTION_ERROR.toException();
        }
    }

    public String recruitDetatilAPiCaller(
            String id
    ){
        try{
            return recruitFeignClient.getRecruitDetail(
                    accessKey, id
            );
        }catch (Exception e){
            throw RecruitException.API_CONNECTION_ERROR.toException();
        }
    }
}
