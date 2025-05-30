package com.dodream.scrap.domain.value;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Category {

    TRAINING("학원"),
    RECRUIT("채용");

    private final String name;
}
