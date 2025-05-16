package com.dodream.auth.application;

import com.dodream.auth.domain.TokenProvider;
import com.dodream.auth.dto.TokenRequest;
import com.dodream.core.exception.DomainException;
import com.dodream.member.domain.Member;
import com.dodream.member.domain.State;
import com.dodream.member.repository.MemberRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class TokenServiceTest {
    @Mock
    private TokenProvider tokenProvider;

    @Mock
    private MemberRepository memberRepository;

    @InjectMocks
    private TokenService tokenService;


    @Test
    @DisplayName("토큰 발급 테스트 성공")
    void 토큰_발급_성공(){
        // given
        Optional<Member> member = Optional.of(mock(Member.class));

        TokenRequest tokenRequest = new TokenRequest(1L);
        when(memberRepository.findByIdAndState(1L, State.ACTIVE)).thenReturn(member);

        // when
        tokenService.provideAccessToken(tokenRequest);

        // then
        verify(tokenProvider, times(1)).provideAccessToken(member.get());
    }

    @Test
    @DisplayName("토큰 발급 실패 - 존재하지 않는 회원")
    void 토큰_발급_실패_회원_x(){
        // given
        TokenRequest tokenRequest = new TokenRequest(1L);
        when(memberRepository.findByIdAndState(1L,State.ACTIVE)).thenReturn(Optional.empty());

        // when & then
        Assertions.assertThatThrownBy(() -> tokenService.provideAccessToken(tokenRequest))
                .isInstanceOf(DomainException.class)
                .hasMessageContaining("회원 정보를 찾을 수 없습니다");
    }
}
