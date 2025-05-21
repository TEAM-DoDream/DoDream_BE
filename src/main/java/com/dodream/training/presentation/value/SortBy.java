package com.dodream.training.presentation.value;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum SortBy {

    DEADLINE_ASC("2", "ASC","마감 임박순"),
    DEADLINE_DESC("2", "DESC", "마감 여유순");

    private final String sort;
    private final String sortCol;
    private final String name;
}
