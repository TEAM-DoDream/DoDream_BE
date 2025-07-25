package com.dodream.member.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public record ChangeMemberPasswordByEmailRequestDto(

        @NotBlank(message = "이메일을 입력해주세요")
        @Pattern(regexp = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+.[A-Za-z]{2,6}$", message = "이메일 형식에 맞지 않습니다.")
        @Schema(description = "비밀번호 변경할려는 회원 이메일", example = "test1@dodream.com")
        String email,

        @NotBlank
        @Schema(description = "비밀번호 변경할 회원 아이디", example = "test1")
        String loginId,

        @NotBlank
        @Schema(description = "새로운 비밀번호", example = "new_password")
        String newPassword
) {
}
