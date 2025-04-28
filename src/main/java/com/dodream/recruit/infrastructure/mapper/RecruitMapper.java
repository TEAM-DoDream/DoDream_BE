package com.dodream.recruit.infrastructure.mapper;

import com.dodream.recruit.dto.response.RecruitResponseDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Component;

@Component
public class RecruitMapper extends AbstractRecruitMapper<RecruitResponseDto> {

    protected RecruitMapper(ObjectMapper objectMapper) {
        super(objectMapper);
    }

    public RecruitResponseDto recruitListMapper(String json){
        return parse(json, RecruitResponseDto.class);
    }
}
