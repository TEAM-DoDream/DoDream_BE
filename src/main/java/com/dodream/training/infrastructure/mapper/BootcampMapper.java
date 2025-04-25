package com.dodream.training.infrastructure.mapper;

import com.dodream.training.dto.response.BootcampDetailApiResponse;
import com.dodream.training.dto.response.BootcampListApiResponse;
import com.dodream.training.exception.TrainingErrorCode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class BootcampMapper {

    private final ObjectMapper objectMapper;

    public BootcampListApiResponse jsonStringToBootcampListApiResponse(String json) {
        try{
            return objectMapper.readValue(json, BootcampListApiResponse.class);
        }catch(Exception e){
            throw TrainingErrorCode.NOT_CONVERT_JSON_TO_OBJECT.toException();
        }
    }

    public BootcampDetailApiResponse jsonStringToBootcampDetailApiResponse(String json) {
        try{
            return objectMapper.readValue(json, BootcampDetailApiResponse.class);
        }catch(Exception e){
            throw TrainingErrorCode.NOT_CONVERT_JSON_TO_OBJECT.toException();
        }
    }
}
