package com.dodream.recruit.exception;

import com.dodream.core.exception.DomainException;
import com.dodream.core.exception.error.BaseErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@RequiredArgsConstructor
@Getter
public enum RecruitException implements BaseErrorCode<DomainException> {
    API_CONNECTION_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "채용정보 API를 불러오는데 실패했습니다."),
    CANNOT_CONVERT_JSON(HttpStatus.INTERNAL_SERVER_ERROR, "Json 객체 변환에 실패했습니다.");

    private final HttpStatus httpStatus;

    private final String message;

    @Override
    public DomainException toException() {
        return new DomainException(httpStatus, this);
    }
}
