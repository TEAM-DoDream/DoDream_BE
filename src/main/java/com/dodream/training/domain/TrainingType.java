package com.dodream.training.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum TrainingType {

    BOOTCAMP("이론 위주"),
    DUAL("실습 위주");

    private final String description;
}
