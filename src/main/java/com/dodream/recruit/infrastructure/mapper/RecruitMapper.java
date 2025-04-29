package com.dodream.recruit.infrastructure.mapper;

import com.dodream.recruit.dto.response.RecruitResponseDetailDto;
import com.dodream.recruit.dto.response.RecruitResponseListApiDto;
import com.dodream.recruit.dto.response.RecruitResponseListDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class RecruitMapper extends AbstractRecruitMapper<RecruitResponseListApiDto, RecruitResponseDetailDto> {

    protected RecruitMapper(ObjectMapper objectMapper) {
        super(objectMapper);
    }

    public RecruitResponseListApiDto recruitListMapper(String json){
        return parse(json, RecruitResponseListApiDto.class);
    }

    public RecruitResponseDetailDto recruitDetailMapper(String json){
        return parse(json, RecruitResponseDetailDto.class);
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