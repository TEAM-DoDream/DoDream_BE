package com.dodream.job.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum TodoCategory {

    SEED("씨앗"),
    SPROUT("새싹"),
    TREE("꿈나무");

    private final String value;
}
