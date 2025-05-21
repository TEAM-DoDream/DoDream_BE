package com.dodream.training.presentation.value;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum SortBy {

    DEADLINE_ASC("ASC", "2","마감 임박순"),
    DEADLINE_DESC("DESC", "2", "마감 여유순");

    private final String sort;
    private final String sortCol;
    private final String name;
}
