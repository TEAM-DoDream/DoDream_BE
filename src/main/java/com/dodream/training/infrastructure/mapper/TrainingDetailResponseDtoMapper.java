package com.dodream.training.infrastructure.mapper;

import com.dodream.core.infrastructure.mapper.AbstractObjectMapper;
import com.dodream.training.dto.response.TrainingDetailApiResponse;
import com.fasterxml.jackson.databind.ObjectMapper;

public class TrainingDetailResponseDtoMapper extends AbstractObjectMapper<TrainingDetailApiResponse> implements TrainingMapper<TrainingDetailApiResponse> {

    public TrainingDetailResponseDtoMapper(ObjectMapper objectMapper) {
        super(objectMapper);
    }

    @Override
    public TrainingDetailApiResponse jsonToResponseDto(String json) {
        return parse(json, TrainingDetailApiResponse.class);
    }
}
