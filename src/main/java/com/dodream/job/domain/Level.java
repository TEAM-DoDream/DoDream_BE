package com.dodream.job.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum Level {
    HIGH("높음"),
    MEDIUM("중간"),
    LOW("낮음");

    private final String description;
}
