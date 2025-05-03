package com.dodream.job.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum WorkTime {
    WEEKDAY_MORNING("평일 오전"),
    WEEKDAY_AFTERNOON("평일 오후"),
    WEEKDAY_NINE_TO_SIX("평일 9-18시"),
    WEEKEND("주말 근무"),
    EVENT("이벤트성"),
    FLEXIBLE("탄력 근무");

    private final String description;
}
