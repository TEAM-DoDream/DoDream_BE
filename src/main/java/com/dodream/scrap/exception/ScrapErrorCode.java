package com.dodream.scrap.exception;

import com.dodream.core.exception.DomainException;
import com.dodream.core.exception.error.BaseErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum ScrapErrorCode implements BaseErrorCode<DomainException> {

    POST_IS_SAVED(HttpStatus.CONFLICT, "이미 저장된 공고입니다."),
    SCRAP_LIMIT_EXCEEDED(HttpStatus.BAD_REQUEST, "저장 공고는 50개를 넘을 수 없습니다."),
    NOT_FOUND_SCRAP(HttpStatus.NOT_FOUND, "저장된 공고가 아닙니다."),
    SORT_NAME_ERROR(HttpStatus.BAD_REQUEST, "정렬 방식이 잘못되었습니다.");


    private final HttpStatus httpStatus;

    private final String message;

    @Override
    public DomainException toException() {
        return new DomainException(httpStatus, this);
    }
}
