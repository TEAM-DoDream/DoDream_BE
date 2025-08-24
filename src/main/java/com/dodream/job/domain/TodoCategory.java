package com.dodream.job.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum TodoCategory {

    SEED("씨앗 단계"),
    SPROUT("새싹 단계"),
    TREE("꿈나무 단계");

    private final String value;
}
