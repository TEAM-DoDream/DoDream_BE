package com.dodream.bootcamp.application;

import com.dodream.bootcamp.dto.response.BootcampListApiResponse;
import com.dodream.bootcamp.infrastructure.BootcampApiCaller;
import com.dodream.bootcamp.infrastructure.BootcampDatePolicy;
import com.dodream.bootcamp.infrastructure.mapper.BootcampMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

@Service
@RequiredArgsConstructor
@Log4j2
public class BootcampApiService {

    private final BootcampApiCaller bootcampApiCaller;
    private final BootcampMapper bootcampMapper;

    public BootcampListApiResponse getBootcampList(
            String pageNum, String pageSize, String regionCode, String ncsCode
    ) {
        String startDate = BootcampDatePolicy.calculateStartDate();
        String endDate = BootcampDatePolicy.calculateEndDate();

        log.info("{}, {}", startDate, endDate);

        String result = bootcampApiCaller.bootcampListApiCaller(
                pageNum, pageSize, regionCode, ncsCode, startDate, endDate
        );

        log.info(result);

        return bootcampMapper.jsonStringToBootcampListApiResponse(result);
    }
}
