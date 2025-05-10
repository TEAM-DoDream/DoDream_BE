package com.dodream.member.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;

public record CheckMemberNickNameResponseDto(
    @Schema(description = "새로운 닉네임", example = "두둠칫칫")
    String newNickName,
    @Schema(description = "중복 여부(true = 중복, false = 중복X)", example = "false")
    boolean duplicated,
    @Schema(description = "메세지", example = "사용가능한 닉네임입니다")
    String message

) {

    public static CheckMemberNickNameResponseDto of(String newNickName,
        boolean duplicated) {
        return new CheckMemberNickNameResponseDto(newNickName, duplicated,
            "사용가능한 닉네임입니다");
    }
}
