package com.dodream.utils;

import com.dodream.core.application.CustomUserDetailsService;
import com.dodream.core.infrastructure.security.CustomUserDetails;
import com.dodream.member.domain.Member;
import java.util.List;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.test.context.support.WithSecurityContextFactory;

@RequiredArgsConstructor
public class WithUserDetailsSecurityContextFactory implements
    WithSecurityContextFactory<TestAccount> {

    private final CustomUserDetailsService userDetailsService;

    @Override
    public SecurityContext createSecurityContext(TestAccount annotation) {

        SecurityContext context = SecurityContextHolder.createEmptyContext();

        Member mockMember = Member.builder()
            .id(1L)
            .nickName("testUser")
            .build();

//        CustomUserDetails userDetails = new CustomUserDetails(mockMember);
        CustomUserDetails userDetails = new CustomUserDetails(mockMember);

        Authentication authentication = new UsernamePasswordAuthenticationToken(
            userDetails, "", userDetails.getAuthorities());

        context.setAuthentication(authentication);
        return context;

    }
}
