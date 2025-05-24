package com.dodream.scrap.domain.value;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum RecruitCloseType {

    SPECIFIC_DATE("1", "접수 마감일"),
    UNTIL_FILLED("2", "채용시"),
    ALWAYS_OPEN("3", "상시"),
    OCCASIONAL("4", "수시");

    private final String code;
    private final String name;
}
