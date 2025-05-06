package com.dodream.job.infrastructure.mapper;

import com.dodream.core.infrastructure.converter.XmlToJsonConverter;
import com.dodream.job.dto.response.JobDescriptionDto;
import com.dodream.job.exception.JobErrorCode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class JobDescriptionMapper {

    private final ObjectMapper objectMapper;
    private final XmlToJsonConverter xmlToJsonConverter;

    public JobDescriptionDto toJobDescriptionDto(String xml){
        String json = xmlToJsonConverter.convertXmlToJson(xml);

        try{
            return objectMapper.readValue(json, JobDescriptionDto.class);
        }catch (Exception e){
            throw JobErrorCode.CANNOT_CONVERT_JOB_OBJECT.toException();
        }
    }
}
