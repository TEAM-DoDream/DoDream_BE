package com.dodream.recruit.infrastructure.mapper;

<<<<<<< HEAD
import com.dodream.recruit.dto.response.RecruitResponseDetailDto;
import com.dodream.recruit.dto.response.RecruitResponseListApiDto;
import com.dodream.recruit.dto.response.RecruitResponseListDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class RecruitMapper extends AbstractRecruitMapper<RecruitResponseListApiDto, RecruitResponseDetailDto> {
=======
import com.dodream.recruit.dto.response.RecruitResponseDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Component;

@Component
public class RecruitMapper extends AbstractRecruitMapper<RecruitResponseDto> {
>>>>>>> a7e3773 (feat: recruitList관련 mapper, 서비스 로직 작성)

    protected RecruitMapper(ObjectMapper objectMapper) {
        super(objectMapper);
    }

<<<<<<< HEAD
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

        List<RecruitResponseListDto.Job> simpleJobs = recruitResponseListApiDto.jobs().job() == null
                ? List.of()
                : recruitResponseListApiDto.jobs().job().stream()
                .map(job -> new RecruitResponseListDto.Job(
                        job.url(),
                        job.active(),
                        job.position() != null ? job.position().title() : null,
                        job.position() != null && job.position().location() != null ? job.position().location().name() : null,
                        job.position() != null && job.position().jobType() != null ? job.position().jobType().name() : null,
                        job.position() != null && job.position().jobCode() != null ? job.position().jobCode().name() : null,
                        job.position() != null && job.position().experienceLevel() != null ? job.position().experienceLevel().name() : null,
                        job.position() != null && job.position().requiredEducationLevel() != null ? job.position().requiredEducationLevel().name() : null,
                        job.closeType() != null ? job.closeType().name() : null,
                        job.keyword(),
                        job.readCnt(),
                        job.salary() != null ? job.salary().name() : null,
                        job.id()
                ))
                .toList();

        return new RecruitResponseListDto(
                recruitResponseListApiDto.jobs().count(),
                recruitResponseListApiDto.jobs().start(),
                recruitResponseListApiDto.jobs().total(),
                simpleJobs
        );
=======
    public RecruitResponseDto recruitListMapper(String json){
        return parse(json, RecruitResponseDto.class);
>>>>>>> a7e3773 (feat: recruitList관련 mapper, 서비스 로직 작성)
    }
}
