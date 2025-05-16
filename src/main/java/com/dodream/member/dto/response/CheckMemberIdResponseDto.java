package com.dodream.member.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;

public record CheckMemberIdResponseDto(
    @Schema(description = "로그인 아이디", example = "dodream")
    String loginId,
    @Schema(description = "중복 여부(true = 중복, false = 중복X)", example = "false")
    boolean duplicated,
    @Schema(description = "메세지", example = "사용가능한 아이디입니다")
    String message

) {

    public static CheckMemberIdResponseDto of(String loginId, boolean duplicated) {
        return new CheckMemberIdResponseDto(loginId, duplicated,"사용가능한 아이디입니다");
    }

}
