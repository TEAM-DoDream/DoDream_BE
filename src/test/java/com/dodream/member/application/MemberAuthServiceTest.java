package com.dodream.member.application;

import static com.dodream.member.exception.MemberErrorCode.MEMBER_NOT_FOUND;
import static com.dodream.member.exception.MemberErrorCode.PASSWORD_NOT_MATCH;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import com.dodream.auth.application.RefreshTokenService;
import com.dodream.auth.application.TokenService;
import com.dodream.auth.dto.TokenRequest;
import com.dodream.core.exception.DomainException;
import com.dodream.member.domain.Gender;
import com.dodream.member.domain.Member;
import com.dodream.member.domain.State;
import com.dodream.member.dto.request.MemberLoginRequestDto;
import com.dodream.member.dto.request.MemberSignUpRequestDto;
import com.dodream.member.dto.response.MemberLoginResponseDto;
import com.dodream.member.dto.response.MemberSignUpResponseDto;
import com.dodream.member.repository.MemberRepository;
import com.dodream.region.domain.Region;
import com.dodream.region.repository.RegionRepository;
import java.time.LocalDate;
import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@ExtendWith(MockitoExtension.class)
public class MemberAuthServiceTest {

    @Mock
    private MemberRepository memberRepository;
    @Mock
    private RegionRepository regionRepository;
    @Mock
    private TokenService tokenService;
    @Mock
    private BCryptPasswordEncoder passwordEncoder;
    @Mock
    private RefreshTokenService refreshTokenService;
    @InjectMocks
    private MemberAuthService memberAuthService;

    private static final Long TEST_ID = 1L;
    private static final String TEST_LOGIN_ID = "dodream";
    private static final String TEST_PASSWORD = "hello2025";
    private static final String TEST_WRONG_PASSWORD = "wrongPW";
    private static final String TEST_NICKNAME = "두둠칫";
    private static final LocalDate TEST_BIRTHDATE = LocalDate.of(2000, 1, 1);
    private static final Gender TEST_GENDER = Gender.FEMALE;
    private static final String TEST_REGION_CODE = "11110";
    private static final String TEST_REGION_NAME = "서울 종로구";
    private static final String TEST_ACCESS_TOKEN = "access12345";
    private static final String TEST_REFRESH_TOKEN = "refresh12345";


    @Nested
    @DisplayName("회원가입 테스트")
    class MemberSignUpTest {

        @Test
        @DisplayName("회원가입 성공 테스트")
        void signUpMemberSuccess() {

            // given
            Region region = Region.builder()
                .id(1L)
                .regionCode(TEST_REGION_CODE)
                .regionName(TEST_REGION_NAME)
                .build();

            Member member = Member.builder()
                .loginId(TEST_LOGIN_ID)
                .password(TEST_PASSWORD)
                .nickName(TEST_NICKNAME)
                .birthDate(TEST_BIRTHDATE)
                .gender(TEST_GENDER)
                .region(region)
                .build();

            TokenRequest tokenRequest = new TokenRequest(TEST_ID);

            when(memberRepository.save(any(Member.class))).thenReturn(member);
            when(tokenService.provideAccessToken(any(TokenRequest.class))).thenReturn(
                TEST_ACCESS_TOKEN);
            when(tokenService.provideRefreshToken(any(TokenRequest.class))).thenReturn(
                TEST_REFRESH_TOKEN);
            when(regionRepository.findByRegionCode(TEST_REGION_CODE))
                .thenReturn(Optional.of(region));

            MemberSignUpRequestDto requestDto = new MemberSignUpRequestDto(TEST_LOGIN_ID,
                TEST_PASSWORD, TEST_NICKNAME, TEST_BIRTHDATE, TEST_GENDER, TEST_REGION_CODE);

            // when
            MemberSignUpResponseDto response = memberAuthService.getMemberSignUp(requestDto);

            // then
            assertAll(
                () -> assertThat(response).isNotNull(),
                () -> assertThat(response.memberId()).isEqualTo(member.getId()),
                () -> assertThat(response.accessToken()).isEqualTo(TEST_ACCESS_TOKEN),
                () -> assertThat(response.refreshToken()).isEqualTo(TEST_REFRESH_TOKEN)
            );

        }
    }

    @Nested
    @DisplayName("로그인 테스트")
    class MemberLoginTest {

        @Test
        @DisplayName("로그인 성공 테스트")
        void logInSuccess() {

            // given
            String encodedPassword = passwordEncoder.encode(TEST_PASSWORD);

            Region region = Region.builder()
                .id(1L)
                .regionCode(TEST_REGION_CODE)
                .regionName(TEST_REGION_NAME)
                .build();

            Member member = Member.builder()
                .id(TEST_ID)
                .loginId(TEST_LOGIN_ID)
                .password(encodedPassword)
                .nickName(TEST_NICKNAME)
                .birthDate(TEST_BIRTHDATE)
                .gender(TEST_GENDER)
                .region(region)
                .build();

            MemberLoginRequestDto requestDto = new MemberLoginRequestDto(TEST_LOGIN_ID,
                TEST_PASSWORD);

            when(memberRepository.findByLoginIdAndState(TEST_LOGIN_ID, State.ACTIVE)).thenReturn(
                Optional.of(member));
            when(passwordEncoder.matches(TEST_PASSWORD, encodedPassword)).thenReturn(true);
            when(regionRepository.findByRegionCode(TEST_REGION_CODE))
                         .thenReturn(Optional.of(region));

            // when
            MemberLoginResponseDto response = memberAuthService.getMemberLogin(requestDto);

            // then
            assertAll(
                () -> assertThat(response).isNotNull(),
                () -> assertThat(response.memberId()).isEqualTo(TEST_ID)
            );

        }

        @Test
        @DisplayName("로그인 실패 테스트 - 아이디가 존재하지 않을 때")
        void logInFailNotFoundMember() {

            // given
            when(memberRepository.findByLoginIdAndState(TEST_LOGIN_ID, State.ACTIVE)).thenReturn(
                Optional.empty());

            MemberLoginRequestDto requestDto = new MemberLoginRequestDto(TEST_LOGIN_ID,
                TEST_PASSWORD);

            // when & then
            assertThatThrownBy(() -> memberAuthService.getMemberLogin(requestDto))
                .isInstanceOf(DomainException.class)
                .hasMessageContaining(MEMBER_NOT_FOUND.getMessage());
        }

        @Test
        @DisplayName("로그인 실패 테스트 - 비밀번호가 일치하지 않을 때")
        void logInFailPasswordDismatch() {

            // given
            Region region = Region.builder()
                .id(1L)
                .regionCode(TEST_REGION_CODE)
                .regionName(TEST_REGION_NAME)
                .build();

            String encodedPassword = passwordEncoder.encode(TEST_PASSWORD);

            Member member = Member.builder()
                .id(TEST_ID)
                .loginId(TEST_LOGIN_ID)
                .password(encodedPassword)
                .nickName(TEST_NICKNAME)
                .birthDate(TEST_BIRTHDATE)
                .gender(TEST_GENDER)
                .region(region)
                .build();

            MemberLoginRequestDto requestDto = new MemberLoginRequestDto(TEST_LOGIN_ID,
                TEST_WRONG_PASSWORD);

            when(memberRepository.findByLoginIdAndState(TEST_LOGIN_ID, State.ACTIVE)).thenReturn(
                Optional.of(member));
            when(passwordEncoder.matches(TEST_WRONG_PASSWORD, encodedPassword)).thenReturn(false);

            // then
            assertThatThrownBy(() -> memberAuthService.getMemberLogin(requestDto))
                .isInstanceOf(DomainException.class)
                .hasMessageContaining(PASSWORD_NOT_MATCH.getMessage());


        }

    }

}
