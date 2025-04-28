package com.dodream.recruit.application;

import com.dodream.recruit.dto.response.RecruitResponseDto;
import com.dodream.recruit.infrastructure.RecruitApiCaller;
import com.dodream.recruit.infrastructure.mapper.RecruitMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RecruitService {

    private final RecruitApiCaller recruitApiCaller;
    private final RecruitMapper recruitMapper;

    public RecruitResponseDto getRecruitList(
            String keyWord, String locationCode, int pageNum
    ){
        String result = recruitApiCaller.recruitListApiListCaller(keyWord, locationCode, pageNum);

        return recruitMapper.recruitListMapper(result);
    }
}
