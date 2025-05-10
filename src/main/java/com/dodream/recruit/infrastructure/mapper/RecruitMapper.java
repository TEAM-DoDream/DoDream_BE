package com.dodream.recruit.infrastructure.mapper;

import com.dodream.core.infrastructure.mapper.AbstractObjectMapper;
import com.dodream.recruit.dto.response.RecruitResponseListApiDto;
import com.dodream.recruit.dto.response.RecruitResponseListDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class RecruitMapper extends AbstractObjectMapper<RecruitResponseListApiDto> {

    protected RecruitMapper(ObjectMapper objectMapper) {
        super(objectMapper);
    }

    public RecruitResponseListApiDto recruitListMapper(String json){
        return parse(json, RecruitResponseListApiDto.class);
    }

    public RecruitResponseListDto toSimpleListDto(
            RecruitResponseListApiDto recruitResponseListApiDto
    ) {
        if (recruitResponseListApiDto == null || recruitResponseListApiDto.jobs() == null) {
            return new RecruitResponseListDto(0, 0, "0", List.of());
        }

        List<RecruitResponseListDto.Job> simpleJobs =
                RecruitResponseListDto.toResponseListDto(recruitResponseListApiDto);

        return new RecruitResponseListDto(
                recruitResponseListApiDto.jobs().count(),
                recruitResponseListApiDto.jobs().start(),
                recruitResponseListApiDto.jobs().total(),
                simpleJobs
        );
    }
}