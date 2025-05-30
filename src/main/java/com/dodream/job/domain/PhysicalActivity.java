package com.dodream.job.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum PhysicalActivity {
    HIGH("높은 활동량"),
    MEDIUM("중간 활동량"),
    LOW("낮은 활동량");

    private final String description;
}
