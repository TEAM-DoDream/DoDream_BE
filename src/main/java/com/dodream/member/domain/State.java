package com.dodream.member.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum State {
    ACTIVE("활성"),
    INACTIVE("비활성");

    private final String value;
}
