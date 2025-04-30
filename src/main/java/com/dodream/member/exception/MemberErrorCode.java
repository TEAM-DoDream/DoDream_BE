package com.dodream.member.exception;

import com.dodream.core.exception.DomainException;
import com.dodream.core.exception.error.BaseErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum MemberErrorCode implements BaseErrorCode<DomainException> {
    MEMBER_NOT_FOUND(HttpStatus.NOT_FOUND, "회원 정보를 찾을 수 없습니다"),
    PASSWORD_NOT_MATCH(HttpStatus.BAD_REQUEST, "비밀번호가 일치하지 않습니다."),
    DUPLICATE_MEMBER_ID(HttpStatus.CONFLICT,"이미 사용 중인 아이디입니다."),
    DUPLICATE_NICKNAME(HttpStatus.CONFLICT ,"이미 사용 중인 닉네임입니다.");


    private final HttpStatus httpStatus;

    private final String message;


    @Override
    public DomainException toException() {
        return new DomainException(httpStatus, this);
    }
}
