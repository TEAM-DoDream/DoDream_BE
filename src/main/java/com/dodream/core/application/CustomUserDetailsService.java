package com.dodream.core.application;

import com.dodream.core.infrastructure.security.CustomUserDetails;
import com.dodream.member.domain.Member;
import com.dodream.member.domain.State;
import com.dodream.member.exception.MemberErrorCode;
import com.dodream.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final MemberRepository memberRepository;

    @Override
    public UserDetails loadUserByUsername(String userNickName) throws UsernameNotFoundException {
        Member member = memberRepository.findByNickNameAndState(userNickName, State.ACTIVE)
            .orElseThrow(MemberErrorCode.MEMBER_NOT_FOUND::toException);

        return new CustomUserDetails(member);
    }

    public UserDetails loadUserById(Long id) throws UsernameNotFoundException {
        Member member = memberRepository.findByIdAndState(id,State.ACTIVE)
            .orElseThrow(MemberErrorCode.MEMBER_NOT_FOUND::toException);

        return new CustomUserDetails(member);
    }
}
