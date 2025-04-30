package com.dodream.member.dto.response;

public record MemberSignUpResponseDto(
    String memberId,
    String accessToken,
    String refreshToken
) {

    public static MemberSignUpResponseDto of(String memberId, String accessToken,
        String refreshToken) {
        return new MemberSignUpResponseDto(memberId, accessToken, refreshToken);
    }
}

