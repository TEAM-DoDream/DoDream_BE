package com.dodream.core.exception;

import com.dodream.core.exception.error.BaseErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum GlobalErrorCode implements BaseErrorCode<RuntimeException> {
    CANNOT_CONVERT_OBJECT(HttpStatus.INTERNAL_SERVER_ERROR, "객체 변환에 실패했습니다."),
    CANNOT_GET_CACHE_NAME(HttpStatus.INTERNAL_SERVER_ERROR, "어노테이션에서 캐시 이름을 가져오지 못했습니다."),
    CANNOT_GET_LOCK(HttpStatus.INTERNAL_SERVER_ERROR, "캐시 락 획득 실패"),
    XML_TO_JSON_CONVERT_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "JSON 형식으로 변환 실패"),
    REDIS_GET_DATA_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "레디스 데이터 불러오기 실패"),
    REDIS_SET_DATA_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "레디스 데이터 저장 실패"),
    GET_DATA_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "DB 연결은 되었지만 데이터를 가져오지 못했습니다."),
    DB_CONNECTION_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "DB 연결에 실패했습니다."),
    ALREADY_PROCESS_STARTED(HttpStatus.BAD_REQUEST, "이미 처리중인 요청입니다."),
    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "알 수 없는 내부 오류입니다."),
    NOT_CONVERT_JSON_TO_OBJECT(HttpStatus.INTERNAL_SERVER_ERROR, "JSON -> Object 변환을 실패했습니다."),
    CANNOT_SEND_MAIL(HttpStatus.INTERNAL_SERVER_ERROR, "이메일을 전송하는데 실패했습니다.");

    private final HttpStatus httpStatus;

    private final String message;

    @Override
    public RuntimeException toException() {
        return new RuntimeException(message);
    }
}
