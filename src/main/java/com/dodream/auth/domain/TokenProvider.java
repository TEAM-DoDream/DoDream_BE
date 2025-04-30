package com.dodream.auth.domain;

import com.dodream.member.domain.Member;

public interface TokenProvider {
    String provideAccessToken(Member member);
    String provideRefreshToken(Member member);
    Long getUserId(String token);
}