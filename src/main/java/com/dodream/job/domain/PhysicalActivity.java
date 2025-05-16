package com.dodream.job.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum PhysicalActivity {
    HIGH("움직임이 많은 활동"),
    MEDIUM("가벼운 활동"),
    LOW("정적인 활동");

    private final String description;
}
