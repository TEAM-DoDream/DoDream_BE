package com.dodream.training.exception;

import com.dodream.core.exception.DomainException;
import com.dodream.core.exception.error.BaseErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum TrainingErrorCode implements BaseErrorCode<DomainException> {
    NOT_CONNECT_EXTERNAL_API(HttpStatus.INTERNAL_SERVER_ERROR, "외부 API 서버에서 값을 불러오는데 실패하였습니다."),
    CANNOT_CONVERT_DATE(HttpStatus.INTERNAL_SERVER_ERROR, "날짜 형식 변환에 실패했습니다.");

    private final HttpStatus httpStatus;

    private final String message;

    @Override
    public DomainException toException() {
        return new DomainException(httpStatus, this);
    }
}
