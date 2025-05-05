package com.dodream.integration.utils;

import com.dodream.core.infrastructure.security.CustomUserDetails;
import com.dodream.member.domain.Gender;
import com.dodream.member.domain.Member;
import com.dodream.member.domain.State;
import com.dodream.region.domain.Region;
import java.time.LocalDate;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.test.context.support.WithSecurityContextFactory;

@RequiredArgsConstructor
public class WithUserDetailsSecurityContextFactory implements
    WithSecurityContextFactory<TestCustomUser> {

    private final BCryptPasswordEncoder passwordEncoder;

    @Override
    public SecurityContext createSecurityContext(TestCustomUser user) {

        Region mockRegion = Region.builder()
                     .id(1L)
                     .regionCode("11110")
                     .regionName("서울 종로구")
                     .build();

        Member mockMember = Member.builder()
            .id(1L)
            .nickName("nickname")
            .loginId("dodream")
            .password(passwordEncoder.encode("password"))
            .birthDate(LocalDate.of(2000, 1, 1))
            .gender(Gender.FEMALE)
            .region(mockRegion)
            .state(State.ACTIVE)
            .build();

        CustomUserDetails userDetails = new CustomUserDetails(mockMember);

        Authentication authentication = new UsernamePasswordAuthenticationToken(
            userDetails, "", userDetails.getAuthorities());

        SecurityContext context = SecurityContextHolder.createEmptyContext();
        context.setAuthentication(authentication);
        return context;

    }
}
