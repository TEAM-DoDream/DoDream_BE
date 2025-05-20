package com.dodream.recruit.presentation.value;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum SortBy {

    DEADLINE_ASC("da", "마감 임박순"),
    DEADLINE_DESC("dd", "마감 여유순"),
    POSTED_DATE_DESC("pd", "post");

    private final String code;

    private final String name;
}
