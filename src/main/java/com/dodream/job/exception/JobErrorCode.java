package com.dodream.job.exception;

import com.dodream.core.exception.DomainException;
import com.dodream.core.exception.error.BaseErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum JobErrorCode implements BaseErrorCode<DomainException> {

    CANNOT_CONVERT_OBJECT(HttpStatus.INTERNAL_SERVER_ERROR, "객체 변환에 실패했습니다."),
    CLOVA_API_CALL_FAILED(HttpStatus.INTERNAL_SERVER_ERROR, "Clova Chat Completion API 호출에 실패했습니다.");

    private final HttpStatus httpStatus;

    private final String message;

    @Override
    public DomainException toException() {
        return new DomainException(httpStatus, this);
    }
}
