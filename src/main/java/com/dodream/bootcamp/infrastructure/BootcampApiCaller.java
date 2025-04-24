package com.dodream.bootcamp.infrastructure;

import com.dodream.bootcamp.exception.BootcampErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class BootcampApiCaller{

    private final BootcampApiProperties bootcampApiProperties;
    private final BootcampFeignClient bootcampFeignClient;

    public String bootcampListApiCaller(
            String pageNum, String pageSize, String regionCode, String ncsCode,
            String startDate, String endDate
    ){
        try {
            return bootcampFeignClient.searchBootCampList(
                    bootcampApiProperties.getApiKey(),
                    "JSON",
                    "1",
                    pageNum,
                    pageSize,
                    regionCode,
                    ncsCode,
                    startDate,
                    endDate,
                    "DESC",    // 내림차순 정렬
                    "TRNG_BGDE"     // 훈련명으로 정렬
            );
        } catch (Exception e) {
            throw BootcampErrorCode.NOT_CONNECT_EXTERNAL_API.toException();
        }
    }
}
