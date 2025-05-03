package com.dodream.member.application;

import com.dodream.auth.application.RefreshTokenService;
import com.dodream.auth.application.TokenService;
import com.dodream.auth.dto.TokenRequest;
import com.dodream.auth.exception.AuthenticationErrorCode;
import com.dodream.core.config.security.SecurityUtils;
import com.dodream.member.domain.Member;
import com.dodream.member.domain.State;
import com.dodream.member.dto.request.MemberLoginRequestDto;
import com.dodream.member.dto.request.MemberSignUpRequestDto;
import com.dodream.member.dto.response.CheckMemberIdResponseDto;
import com.dodream.member.dto.response.CheckMemberNickNameResponseDto;
import com.dodream.member.dto.response.MemberLoginResponseDto;
import com.dodream.member.dto.response.MemberNewTokenResponse;
import com.dodream.member.dto.response.MemberSignUpResponseDto;
import com.dodream.member.exception.MemberErrorCode;
import com.dodream.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class MemberAuthService {

    private final MemberRepository memberRepository;
    private final TokenService tokenService;
    private final RefreshTokenService refreshTokenService;
    private final BCryptPasswordEncoder passwordEncoder;

    @Transactional
    public MemberLoginResponseDto getMemberLogin(MemberLoginRequestDto memberLoginRequestDto) {

        Member member = memberRepository.findByMemberIdAndState(memberLoginRequestDto.memberId(),
                State.ACTIVE)
            .orElseThrow(MemberErrorCode.MEMBER_NOT_FOUND::toException);

        if (!passwordEncoder.matches(memberLoginRequestDto.password(), member.getPassword())) {
            throw MemberErrorCode.PASSWORD_NOT_MATCH.toException();
        }

        String accessToken = tokenService.provideAccessToken(new TokenRequest(member.getId()));
        String refreshToken = tokenService.provideRefreshToken(new TokenRequest(member.getId()));

        refreshTokenService.save(member.getId(), refreshToken);

        return MemberLoginResponseDto.of(member.getMemberId(), member.getNickName(), accessToken, refreshToken);
    }

    public void getMemberLogout() {

        Long currentId = SecurityUtils.getCurrentMemberId();

        refreshTokenService.delete(currentId);
    }

    @Transactional
    public MemberSignUpResponseDto getMemberSignUp(MemberSignUpRequestDto requestDto) {

        if (memberRepository.existsByMemberIdAndState(requestDto.memberId(), State.ACTIVE)) {
            throw MemberErrorCode.DUPLICATE_MEMBER_ID.toException();
        }

        if (memberRepository.existsByNickNameAndState(requestDto.nickName(), State.ACTIVE)) {
            throw MemberErrorCode.DUPLICATE_NICKNAME.toException();
        }

        Member member = Member.builder()
            .memberId(requestDto.memberId())
            .password(passwordEncoder.encode(requestDto.password()))
            .nickName(requestDto.nickName())
            .birthDate(requestDto.birthDate())
            .gender(requestDto.gender())
            .regionCode(requestDto.regionCode())
            .build();

        Member newMember = memberRepository.save(member);

        String accessToken = tokenService.provideAccessToken(new TokenRequest(member.getId()));
        String refreshToken = tokenService.provideRefreshToken(new TokenRequest(member.getId()));

        refreshTokenService.save(member.getId(), refreshToken);

        return new MemberSignUpResponseDto(newMember.getMemberId(), accessToken, refreshToken);
    }


    public CheckMemberIdResponseDto checkDuplicateMemberId(String memberId) {

        if (memberRepository.existsByMemberIdAndState(memberId, State.ACTIVE)) {
            throw MemberErrorCode.DUPLICATE_MEMBER_ID.toException();
        }
        return CheckMemberIdResponseDto.of(memberId, false);
    }


    public CheckMemberNickNameResponseDto checkDuplicateMemberNickName(String nickName) {

        if (memberRepository.existsByNickNameAndState(nickName, State.ACTIVE)) {
            throw MemberErrorCode.DUPLICATE_NICKNAME.toException();
        }
        return CheckMemberNickNameResponseDto.of(nickName, false);
    }

    @Transactional
    public MemberNewTokenResponse issueNewToken(String refreshToken) {

        if (refreshToken == null || refreshToken.isBlank()) {
            throw AuthenticationErrorCode.EMPTY_TOKEN.toException();
        }

        Long id = tokenService.getUserId(refreshToken);

        Member member = memberRepository.findByIdAndState(id,State.ACTIVE)
            .orElseThrow(MemberErrorCode.MEMBER_NOT_FOUND::toException);

        String newAccessToken = tokenService.provideAccessToken(new TokenRequest(member.getId()));
        String newRefreshToken = tokenService.provideRefreshToken(new TokenRequest(member.getId()));

        refreshTokenService.delete(member.getId());
        refreshTokenService.save(member.getId(), newRefreshToken);

        return MemberNewTokenResponse.of(newAccessToken, newRefreshToken);
    }


    @Transactional
    public void withdrawMember() {

        Long currentId = SecurityUtils.getCurrentMemberId();
        Member member = memberRepository.findByIdAndState(currentId,State.ACTIVE)
            .orElseThrow(MemberErrorCode.MEMBER_NOT_FOUND::toException);

        member.withdraw();
    }

}
