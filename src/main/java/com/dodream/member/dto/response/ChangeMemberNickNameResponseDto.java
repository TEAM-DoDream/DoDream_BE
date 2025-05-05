package com.dodream.member.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;

public record ChangeMemberNickNameResponseDto(
    @Schema(description = "멤버 id(DB상)", example = "1")
    Long memberId,
    @Schema(description = "새로운 닉네임", example = "두둠칫칫")
    String newNickname,
    @Schema(description = "메세지", example = "닉네임 변경 완료")
    String message

) {

    public static ChangeMemberNickNameResponseDto of(Long memberId, String newNickname) {
        return new ChangeMemberNickNameResponseDto(memberId, newNickname, "닉네임 변경 완료");
    }

}