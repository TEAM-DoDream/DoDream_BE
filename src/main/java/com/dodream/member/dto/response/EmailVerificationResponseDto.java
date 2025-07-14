package com.dodream.member.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;

public record EmailVerificationResponseDto(

        @Schema(description = "인증 성공 여부", example = "true")
        boolean verified,

        @Schema(description = "사용자가 입력한 이메일", example = "teamdodream.dev@gmail.com")
        String email,

        @Schema(description = "사용자의 아이디(아이디 찾기에서만)", example = "dodream")
        String loginId
) {
}
