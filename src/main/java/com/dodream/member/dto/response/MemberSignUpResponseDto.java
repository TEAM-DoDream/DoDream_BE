package com.dodream.member.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;

public record MemberSignUpResponseDto(
    @Schema(description = "멤버 id(DB상)", example = "1")
    Long memberId,
    @Schema(description = "access 토큰", example = "abcaccess1232131")
    String accessToken,
    @Schema(description = "refresh 토큰", example = "dferefresh1243124")
    String refreshToken
) {

    public static MemberSignUpResponseDto of(Long memberId, String accessToken,
        String refreshToken) {
        return new MemberSignUpResponseDto(memberId, accessToken, refreshToken);
    }
}

