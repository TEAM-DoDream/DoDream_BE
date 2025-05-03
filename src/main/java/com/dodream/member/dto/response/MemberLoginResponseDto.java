package com.dodream.member.dto.response;

import com.dodream.ncs.domain.Ncs;

public record MemberLoginResponseDto(
    String memberId,
    String nickname,
    String accessToken,
    String refreshToken
) {

    public static MemberLoginResponseDto of(String memberId, String nickname, String accessToken,
        String refreshToken) {
        return new MemberLoginResponseDto(memberId, nickname, accessToken, refreshToken);
    }

}
