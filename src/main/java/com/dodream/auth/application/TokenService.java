package com.dodream.auth.application;

import com.dodream.auth.domain.TokenProvider;
import com.dodream.auth.dto.TokenRequest;
import com.dodream.member.domain.Member;
import com.dodream.member.exception.MemberErrorCode;
import com.dodream.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TokenService {
    private final TokenProvider tokenProvider;
    private final MemberRepository memberRepository;

    public String provideAccessToken(TokenRequest request) {
        Member member = memberRepository.findById(request.memberId())
                .orElseThrow(MemberErrorCode.MEMBER_NOT_FOUND::toException);
        return tokenProvider.provideAccessToken(member);
    }
}
