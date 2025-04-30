package com.dodream.member.dto.response;

public record MemberNewTokenResponse(
    String accessToken,
    String refreshToken
) {

    public static MemberNewTokenResponse of(String accessToken, String refreshToken) {
        return new MemberNewTokenResponse(accessToken, refreshToken);
    }

}
