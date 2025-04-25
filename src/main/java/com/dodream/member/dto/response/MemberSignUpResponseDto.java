package com.dodream.member.dto.response;

public record MemberSignUpResponseDto(
    String memberId,
    String accessToken
) {

    public static MemberSignUpResponseDto of(String memberId, String accessToken) {
        return new MemberSignUpResponseDto(memberId, accessToken);
    }
}

