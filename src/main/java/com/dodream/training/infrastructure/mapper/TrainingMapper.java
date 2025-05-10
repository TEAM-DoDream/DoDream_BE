package com.dodream.training.infrastructure.mapper;

public interface TrainingMapper<T> {
    T jsonToResponseDto(String json);
}
