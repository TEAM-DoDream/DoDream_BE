package com.dodream.job.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum Require {
    NONE("불필요함"),
    OPTIONAL("선택 사항"),
    REQUIRED("필요함");

    private final String description;
}
