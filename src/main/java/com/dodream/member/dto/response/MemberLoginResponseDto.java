package com.dodream.member.dto.response;

import com.dodream.ncs.domain.Ncs;

public record MemberLoginResponseDto(
    String memberId,
    String accessToken
) {

    public static MemberLoginResponseDto of(String memberId, String accessToken) {
        return new MemberLoginResponseDto(memberId,accessToken);
    }

}
