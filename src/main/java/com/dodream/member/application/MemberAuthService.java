package com.dodream.member.application;

import com.dodream.auth.application.TokenService;
import com.dodream.auth.dto.TokenRequest;
import com.dodream.member.domain.Member;
import com.dodream.member.dto.request.MemberLoginRequestDto;
import com.dodream.member.dto.request.MemberSignUpRequestDto;
import com.dodream.member.dto.response.MemberLoginResponseDto;
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
    private final BCryptPasswordEncoder passwordEncoder;

    public MemberLoginResponseDto getMemberLogin(MemberLoginRequestDto memberLoginRequestDto) {

        Member member = memberRepository.findByMemberId(memberLoginRequestDto.memberId())
            .orElseThrow(MemberErrorCode.MEMBER_NOT_FOUND::toException);

        if (!passwordEncoder.matches(memberLoginRequestDto.password(), member.getPassword())) {
            throw MemberErrorCode.PASSWORD_NOT_MATCH.toException();
        }
        return MemberLoginResponseDto.of(member.getMemberId(),
            tokenService.provideAccessToken(new TokenRequest(member.getId())));

    }


    @Transactional
    public MemberSignUpResponseDto getMemberSignUp(MemberSignUpRequestDto requestDto) {

        if (memberRepository.existsByMemberId(requestDto.memberId())) {
            throw MemberErrorCode.DUPLICATE_MEMBER_ID.toException();
        }

        if (memberRepository.existsByNickName(requestDto.nickName())) {
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

        return new MemberSignUpResponseDto(newMember.getMemberId(),
            tokenService.provideAccessToken(new TokenRequest(newMember.getId())));
    }


}
