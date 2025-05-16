package com.dodream.recruit.exception;

import com.dodream.core.exception.DomainException;
import com.dodream.core.exception.error.BaseErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@RequiredArgsConstructor
@Getter
public enum RecruitErrorCode implements BaseErrorCode<DomainException> {
    API_CONNECTION_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "채용정보 API를 불러오는데 실패했습니다."),
    REGION_CODE_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "사람인 지역코드 정보를 불러오는데 실패했습니다."),
    CANNOT_CONVERT_JSON(HttpStatus.INTERNAL_SERVER_ERROR, "Json 객체 변환에 실패했습니다."),
    CONVERT_TIME_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "마감일 일정 변환에 실패했습니다.");

    private final HttpStatus httpStatus;

    private final String message;

    @Override
    public DomainException toException() {
        return new DomainException(httpStatus, this);
    }
}
