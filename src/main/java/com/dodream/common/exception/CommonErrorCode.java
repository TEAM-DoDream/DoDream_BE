package com.dodream.common.exception;

import com.dodream.core.exception.DomainException;
import com.dodream.core.exception.error.BaseErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum CommonErrorCode implements BaseErrorCode<DomainException> {
    JSON_TO_OBJECT_CONVERT_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "객체 변환에 실패했습니다."),
    EXTERNAL_API_CONNECTION_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "OPEN API 연결에 실패했습니다.");
    private final HttpStatus httpStatus;

    private final String message;

    @Override
    public DomainException toException() {
        return new DomainException(httpStatus, this);
    }
}
