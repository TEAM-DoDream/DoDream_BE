package com.dodream.training.infrastructure.mapper;

import com.dodream.core.infrastructure.mapper.AbstractObjectMapper;
import com.dodream.training.dto.response.TrainingListApiResponse;
import com.fasterxml.jackson.databind.ObjectMapper;

public class TrainingListApiReseponseMapper extends AbstractObjectMapper<TrainingListApiResponse> implements TrainingMapper<TrainingListApiResponse> {

    public TrainingListApiReseponseMapper(ObjectMapper objectMapper) {
        super(objectMapper);
    }

    @Override
    public TrainingListApiResponse jsonToResponseDto(String json) {
        return parse(json, TrainingListApiResponse.class);
    }
}
