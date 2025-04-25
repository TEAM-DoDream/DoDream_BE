package com.dodream.training.infrastructure.mapper;

import com.dodream.training.dto.response.TrainingDetailApiResponse;
import com.dodream.training.dto.response.TrainingListApiResponse;
import com.dodream.training.exception.TrainingErrorCode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
public class TrainingMapperImpl extends AbstractTrainingMapper<TrainingListApiResponse, TrainingDetailApiResponse> {

    protected TrainingMapperImpl(ObjectMapper objectMapper) {
        super(objectMapper);
    }

    @Override
    public TrainingListApiResponse jsonToListResponseDto(String json) {
        return parse(json, TrainingListApiResponse.class);
    }

    @Override
    public TrainingDetailApiResponse jsonToDetailResponseDto(String json) {
        return parse(json, TrainingDetailApiResponse.class);
    }
}
