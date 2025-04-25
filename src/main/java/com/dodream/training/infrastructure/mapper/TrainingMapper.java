package com.dodream.training.infrastructure.mapper;

public interface TrainingMapper<L, D> {
    L jsonToListResponseDto(String json);

    D jsonToDetailResponseDto(String json);
}
