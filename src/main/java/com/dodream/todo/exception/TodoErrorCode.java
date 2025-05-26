package com.dodream.todo.exception;

import com.dodream.core.exception.DomainException;
import com.dodream.core.exception.error.BaseErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum TodoErrorCode implements BaseErrorCode<DomainException> {

    TODO_NOT_FOUND(HttpStatus.NOT_FOUND, "TODO 데이터를 찾을 수 없습니다."),
    TODO_MEMO_NOT_PUBLIC(HttpStatus.BAD_REQUEST, "공개되지 않은 투두 메모입니다."),
    UNSUPPORTED_FILE_EXTENSION(HttpStatus.BAD_REQUEST, "지원하지 않는 파일 확장자입니다.(jpg,jpeg,png만 허용)"),
    FILE_UPLOAD_FAILED(HttpStatus.INTERNAL_SERVER_ERROR, "파일 업로드 중 문제가 발생했습니다.");

    private final HttpStatus httpStatus;

    private final String message;

    @Override
    public DomainException toException() {
        return new DomainException(httpStatus, this);
    }
}


