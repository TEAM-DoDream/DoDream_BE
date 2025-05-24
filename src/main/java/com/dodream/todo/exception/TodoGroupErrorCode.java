package com.dodream.todo.exception;

import com.dodream.core.exception.DomainException;
import com.dodream.core.exception.error.BaseErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum TodoGroupErrorCode implements BaseErrorCode<DomainException> {

    TODO_GROUP_NOT_FOUND(HttpStatus.NOT_FOUND, "TODO GROUP 데이터를 찾을 수 없습니다."),
    JOB_EXISTS(HttpStatus.BAD_REQUEST, "이미 담은 직업입니다.");


    private final HttpStatus httpStatus;

    private final String message;

    @Override
    public DomainException toException() {
        return new DomainException(httpStatus, this);
    }
}
