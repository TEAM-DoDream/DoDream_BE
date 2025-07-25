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
    MEMBER_PROFILE_NOT_FOUND(HttpStatus.NOT_FOUND, "삭제할 프로필 이미지가 존재하지 않습니다."),
    PASSWORD_NOT_MATCH(HttpStatus.BAD_REQUEST, "비밀번호가 일치하지 않습니다."),
    DUPLICATE_EMAIL(HttpStatus.CONFLICT, "이미 사용 중인 이메일입니다."),
    DUPLICATE_MEMBER_ID(HttpStatus.CONFLICT, "이미 사용 중인 아이디입니다."),
    DUPLICATE_NICKNAME(HttpStatus.CONFLICT, "이미 사용 중인 닉네임입니다."),
    MEMBER_INACTIVE(HttpStatus.BAD_REQUEST, "비활성화된 계정입니다."),
    PASSWORD_NOT_SAME(HttpStatus.BAD_REQUEST, "두 비밀번호가 일치하지 않습니다."),
    UNSUPPORTED_FILE_EXTENSION(HttpStatus.BAD_REQUEST, "지원하지 않는 파일 확장자입니다.(jpg,jpeg,png만 허용)"),
    FILE_UPLOAD_FAILED(HttpStatus.INTERNAL_SERVER_ERROR, "파일 업로드 중 문제가 발생했습니다."),
    FILE_DELETE_FAILED(HttpStatus.INTERNAL_SERVER_ERROR, "파일 삭제 중 문제가 발생했습니다."),
    DUPLICATED_EMAIL(HttpStatus.CONFLICT, "이미 가입된 이메일입니다."),
    DUPLICATED_PASSWORD(HttpStatus.CONFLICT, "이전과 동일한 비밀번호입니다."),
    EMAIL_NOT_FOUND(HttpStatus.NOT_FOUND, "가입된 이메일이 존재하지 않습니다."),
    EMAIL_AND_LOGINID_NOT_FOUND(HttpStatus.NOT_FOUND, "이메일과 아이디가 일치하지 않습니다."),
    VERIFICATION_NOT_FOUND(HttpStatus.NOT_FOUND, "인증 정보가 존재하지 않습니다."),
    CODE_NOT_MATCH(HttpStatus.BAD_REQUEST, "인증 번호가 일치하지 않습니다.");


    private final HttpStatus httpStatus;

    private final String message;


    @Override
    public DomainException toException() {
        return new DomainException(httpStatus, this);
    }
}
