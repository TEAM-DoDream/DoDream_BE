package com.dodream.recruit.application;

import com.dodream.recruit.dto.response.RecruitResponseListApiDto;
import com.dodream.recruit.dto.response.RecruitResponseListDto;
import com.dodream.recruit.infrastructure.RecruitApiCaller;
import com.dodream.recruit.infrastructure.mapper.RecruitMapper;
import com.dodream.recruit.util.RecruitCodeResolver;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Log4j2
public class RecruitService {

    private final RecruitApiCaller recruitApiCaller;
    private final RecruitMapper recruitMapper;
    private final RecruitCodeResolver recruitCodeResolver;

    public RecruitResponseListDto getRecruitList(
            String keyWord, String locationName, int pageNum
    ){
        String result
                = recruitApiCaller.recruitListApiListCaller(
                        keyWord, recruitCodeResolver.resolveRecruitLocationName(locationName), pageNum
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
}
