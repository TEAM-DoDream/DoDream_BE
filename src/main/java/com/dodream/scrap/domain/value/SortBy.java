package com.dodream.scrap.domain.value;

import com.dodream.scrap.exception.ScrapErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;

@Getter
@RequiredArgsConstructor
public enum SortBy {

    CREATED_AT_DESC("최신 순", Sort.by("createdAt").descending()),
    CREATED_AT_ASC("오래된 순", Sort.by("createdAt").ascending());

    private final String name;
    private final Sort sort;

    public static SortBy fromName(String name) {
        if(name == null || name.isEmpty())
            return SortBy.CREATED_AT_DESC;

        for (SortBy sortBy : values()) {
            if (sortBy.name.equals(name)) {
                return sortBy;
            }
        }
        throw ScrapErrorCode.SORT_NAME_ERROR.toException();
    }
}
