package com.dodream.training.infrastructure.mapper;

import com.dodream.training.dto.response.TrainingDetailApiResponse;
import com.dodream.training.dto.response.TrainingListApiResponse;
import com.dodream.training.exception.TrainingErrorCode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class TrainingMapper {

    private final ObjectMapper objectMapper;

    public TrainingListApiResponse jsonStringToBootcampListApiResponse(String json) {
        try{
            return objectMapper.readValue(json, TrainingListApiResponse.class);
        }catch(Exception e){
            throw TrainingErrorCode.NOT_CONVERT_JSON_TO_OBJECT.toException();
        }
    }

    public TrainingDetailApiResponse jsonStringToBootcampDetailApiResponse(String json) {
        try{
            return objectMapper.readValue(json, TrainingDetailApiResponse.class);
        }catch(Exception e){
            throw TrainingErrorCode.NOT_CONVERT_JSON_TO_OBJECT.toException();
        }
    }
}
