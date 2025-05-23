package com.dodream.member.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Gender {
    FEMALE("여자"),
    MALE("남자");

    private final String value;
}
