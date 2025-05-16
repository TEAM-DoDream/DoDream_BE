package com.dodream.member.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;

public record MemberNewTokenResponse(
    @Schema(description = "access 토큰", example = "abcaccess1232131")
    String accessToken,
    @Schema(description = "refresh 토큰", example = "dferefresh1243124")
    String refreshToken
) {

    public static MemberNewTokenResponse of(String accessToken, String refreshToken) {
        return new MemberNewTokenResponse(accessToken, refreshToken);
    }

}
