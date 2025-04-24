package com.dodream.bootcamp.infrastructure.mapper;

import com.dodream.bootcamp.dto.response.BootcampListApiResponse;
import com.dodream.bootcamp.exception.BootcampErrorCode;
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
            throw BootcampErrorCode.NOT_CONVERT_JSON_TO_OBJECT.toException();
        }
    }
}
