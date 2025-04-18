package com.dodream.core.exception;

import com.dodream.core.exception.error.BaseErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum GlobalErrorCode implements BaseErrorCode<RuntimeException> {
    REDIS_GET_DATA_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "레디스 데이터 불러오기 실패"),
    REDIS_SET_DATA_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "레디스 데이터 저장 실패"),
    GET_DATA_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "DB 연결은 되었지만 데이터를 가져오지 못했습니다."),
    DB_CONNECTION_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "DB 연결에 실패했습니다."),
    ALREADY_PROCESS_STARTED(HttpStatus.BAD_REQUEST, "이미 처리중인 요청입니다."),
    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "알 수 없는 내부 오류입니다.");

    private final HttpStatus httpStatus;

    private final String message;

    @Override
    public RuntimeException toException() {
        return new RuntimeException(message);
    }
}
