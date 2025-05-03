package com.dodream.job.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum SalaryType {
    MONTHLY("월급"),
    DAILY("일급"),
    PER_CASE("건당");

    private final String description;
}
