package com.dodream.recruit.application;

import com.dodream.recruit.dto.response.RecruitResponseDetailDto;
import com.dodream.recruit.dto.response.RecruitResponseListDto;
import com.dodream.recruit.infrastructure.RecruitApiCaller;
import com.dodream.recruit.infrastructure.mapper.RecruitMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Log4j2
public class RecruitService {

    private final RecruitApiCaller recruitApiCaller;
    private final RecruitMapper recruitMapper;

    public RecruitResponseListDto getRecruitList(
            String keyWord, String locationCode, int pageNum
    ){
        String result = recruitApiCaller.recruitListApiListCaller(keyWord, locationCode, pageNum);

        return recruitMapper.recruitListMapper(result);
    }

    public RecruitResponseDetailDto getRecruitDetail(
            String id
    ){
        String result = recruitApiCaller.recruitDetatilAPiCaller(id);

        return recruitMapper.recruitDetailMapper(result);
    }
}
