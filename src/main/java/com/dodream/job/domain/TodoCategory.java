package com.dodream.job.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum TodoCategory {

    START("시작하기"),
    PREPARE("준비하기"),
    CHALLENGE("도전하기");

    private final String value;
}
