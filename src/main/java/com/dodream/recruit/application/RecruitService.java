package com.dodream.recruit.application;

import com.dodream.recruit.dto.response.RecruitResponseListApiDto;
import com.dodream.recruit.dto.response.RecruitResponseListDto;
import com.dodream.recruit.infrastructure.RecruitApiCaller;
import com.dodream.recruit.infrastructure.mapper.RecruitMapper;
import com.dodream.recruit.util.RecruitCodeResolver;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

@Service
@RequiredArgsConstructor
@Log4j2
public class RecruitService {

    private final RecruitApiCaller recruitApiCaller;
    private final RecruitMapper recruitMapper;
    private final RecruitCodeResolver recruitCodeResolver;

    public RecruitResponseListDto getRecruitList(
            String keyWord, String locationName, String startDate, String endDate, int pageNum
    ){
        String result = recruitApiCaller.recruitListApiListCaller(
                        keyWord, recruitCodeResolver.resolveRecruitLocationName(locationName),
                        getDateTimeString(startDate), getDateTimeString(endDate), pageNum
                );

        RecruitResponseListApiDto mappedResult = recruitMapper.recruitListMapper(result);

        return recruitMapper.toSimpleListDto(mappedResult);
    }

    public RecruitResponseListDto getRecruitDetail(
            String id
    ) {
        String result = recruitApiCaller.recruitDetailAPiCaller(id);

        RecruitResponseListApiDto mappedResult = recruitMapper.recruitListMapper(result);

        return recruitMapper.toSimpleListDto(mappedResult);
    }

    private String getDateTimeString(String date){
        if(date == null || date.isEmpty()){
            return null;
        }

        DateTimeFormatter inputDateFormatter = DateTimeFormatter.ofPattern("yyyy/MM/dd");

        LocalDate localDate = LocalDate.parse(date, inputDateFormatter);
        LocalDateTime localDateTime = localDate.atStartOfDay();

        ZoneId seoulZoneId = ZoneId.of("Asia/Seoul"); // KST (UTC+9)
        long unixTimestampSecondsSeoul = localDateTime.atZone(seoulZoneId).toEpochSecond();
        return String.valueOf(unixTimestampSecondsSeoul);
    }
}
