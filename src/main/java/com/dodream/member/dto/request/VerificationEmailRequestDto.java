package com.dodream.member.dto.request;

import com.dodream.core.util.email.value.VerificationType;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public record VerificationEmailRequestDto(
        @NotBlank(message = "이메일을 입력해주세요")
        @Pattern(regexp = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+.[A-Za-z]{2,6}$", message = "이메일 형식에 맞지 않습니다.")
        @Schema(description = "인증코드를 보낼 이메일 주소", example = "test1@dodream.com")
        String email,

        @Schema(description = "비밀번호를 찾고 싶은 아이디(FIND_PASSWORD일때만 전송)", example = "dodream")
        String loginId,

        @NotBlank
        @Schema(description = "이메일 인증 타입(회원가입, 아이디 찾기, 비밀번호 찾기)", example = "SIGN_UP")
        VerificationType type
) {
}
