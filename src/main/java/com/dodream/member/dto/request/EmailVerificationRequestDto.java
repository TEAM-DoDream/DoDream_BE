package com.dodream.member.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public record EmailVerificationRequestDto(
        @NotBlank(message = "이메일을 입력해주세요")
        @Pattern(regexp = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+.[A-Za-z]{2,6}$", message = "이메일 형식에 맞지 않습니다.")
        @Schema(description = "입력 이메일", example = "teamdodream.dev@gmail.com")
        String email,

        @Schema(description = "인증 타입(회원 가입, 비밀번호, 아이디 찾기)", example = "SIGN_UP")
        String type,

        @Pattern(regexp = "\\d{6}", message = "인증 코드는 6자리 정수입니다.")
        @Schema(description = "인증 코드", example = "019283")
        String code
) {
}
