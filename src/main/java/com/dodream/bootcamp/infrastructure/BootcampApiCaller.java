package com.dodream.bootcamp.infrastructure;

import com.dodream.bootcamp.exception.BootcampErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class BootcampApiCaller{

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
            throw BootcampErrorCode.NOT_CONNECT_EXTERNAL_API.toException();
        }
    }
}
