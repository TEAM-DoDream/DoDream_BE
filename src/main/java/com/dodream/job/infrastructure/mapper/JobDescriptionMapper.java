package com.dodream.job.infrastructure.mapper;

import com.dodream.core.infrastructure.mapper.AbstractObjectMapper;
import com.dodream.job.dto.response.JobDescriptionDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Component;

@Component
public class JobDescriptionMapper extends AbstractObjectMapper<JobDescriptionDto> {

    public JobDescriptionMapper(ObjectMapper objectMapper) {
        super(objectMapper);
    }

    public JobDescriptionDto toJobDescriptionDto(String json){
        return parse(json, JobDescriptionDto.class);
    }
}
