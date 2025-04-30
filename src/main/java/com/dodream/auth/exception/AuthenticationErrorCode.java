package com.dodream.auth.exception;

import com.dodream.core.exception.DomainException;
import com.dodream.core.exception.error.BaseErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum AuthenticationErrorCode implements BaseErrorCode<DomainException> {
    PASSWORD_NOT_MATCH(HttpStatus.BAD_REQUEST, "비밀번호가 일치하지 않습니다."),
    INVALID_SIGNATURE(HttpStatus.UNAUTHORIZED, "유효하지 않은 토큰입니다."),
    EXPIRED_TOKEN(HttpStatus.UNAUTHORIZED, "만료된 토큰입니다."),
    NOT_MATCH_TOKEN_FORMAT(HttpStatus.UNAUTHORIZED, "토큰 형식이 맞지 않습니다."),
    NOT_DEFINE_TOKEN(HttpStatus.UNAUTHORIZED, "정의되지 않은 토큰입니다."),
    EMPTY_TOKEN(HttpStatus.UNAUTHORIZED, "토큰이 입력되지 않았습니다."),
    NOT_AUTHENTICATED(HttpStatus.UNAUTHORIZED, "인증 정보가 없거나 유효하지 않은 사용자입니다.");


    private final HttpStatus httpStatus;

    private final String message;

    @Override
    public DomainException toException() {
        return new DomainException(httpStatus, this);
    }
}