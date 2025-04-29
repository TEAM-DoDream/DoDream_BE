package com.dodream.recruit.infrastructure.mapper;

import com.dodream.recruit.dto.response.RecruitResponseDetailDto;
import com.dodream.recruit.dto.response.RecruitResponseListDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Component;

@Component
public class RecruitMapper extends AbstractRecruitMapper<RecruitResponseListDto, RecruitResponseDetailDto> {

    protected RecruitMapper(ObjectMapper objectMapper) {
        super(objectMapper);
    }

    public RecruitResponseListDto recruitListMapper(String json){
        return parse(json, RecruitResponseListDto.class);
    }

    public RecruitResponseDetailDto recruitDetailMapper(String json){
        return parse(json, RecruitResponseDetailDto.class);
    }
}
