package com.dodream.member.application;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.Mockito.when;

import com.dodream.core.exception.DomainException;
import com.dodream.core.infrastructure.security.CustomUserDetails;
import com.dodream.member.domain.Gender;
import com.dodream.member.domain.Member;
import com.dodream.member.domain.State;
import com.dodream.member.dto.request.ChangeMemberBirthDateRequestDto;
import com.dodream.member.dto.request.ChangeMemberNickNameRequestDto;
import com.dodream.member.dto.request.ChangeMemberPasswordRequestDto;
import com.dodream.member.dto.request.ChangeMemberRegionCodeRequestDto;
import com.dodream.member.exception.MemberErrorCode;
import com.dodream.member.repository.MemberRepository;
import java.time.LocalDate;
import java.util.Optional;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@ExtendWith(MockitoExtension.class)
public class MemberServiceTest {

    @Mock
    private MemberRepository memberRepository;
    @Mock
    private BCryptPasswordEncoder passwordEncoder;
    @InjectMocks
    private MemberService memberService;

    private static final Long TEST_ID = 1L;
    private static final String TEST_MEMBER_ID = "dodream";
    private static final String TEST_PASSWORD = "password";
    private static final String TEST_WRONG_CHECK_PASSWORD = "wrongCheckPassword";
    private static final String TEST_NICKNAME = "nickname";
    private static final String TEST_NEW_NICKNAME = "newNickname";
    private static final LocalDate TEST_BIRTHDATE = LocalDate.of(2000, 1, 1);
    private static final LocalDate TEST_NEW_BIRTHDATE = LocalDate.of(2002, 12, 24);
    private static final Gender TEST_GENDER = Gender.FEMALE;
    private static final State TEST_STATE = State.ACTIVE;
    private static final String TEST_REGION_CODE = "11110";
    private static final String TEST_NEW_REGION_CODE = "11111";

    private Member mockMember;

    @Nested
    @DisplayName("나의 계정 - 정보 변경 테스트")
    class MemberInfoTest {

        @BeforeEach
        void setupSecurityContext() {

            mockMember = Member.builder()
                .id(TEST_ID)
                .memberId(TEST_MEMBER_ID)
                .password(passwordEncoder.encode(TEST_PASSWORD))
                .nickName(TEST_NICKNAME)
                .birthDate(TEST_BIRTHDATE)
                .gender(TEST_GENDER)
                .regionCode(TEST_REGION_CODE)
                .build();

            CustomUserDetails userDetails = new CustomUserDetails(mockMember);

            Authentication authentication = new UsernamePasswordAuthenticationToken(
                userDetails, "", userDetails.getAuthorities());

            SecurityContext context = SecurityContextHolder.createEmptyContext();
            context.setAuthentication(authentication);
            SecurityContextHolder.setContext(context);

        }

        @AfterEach
        void clearSecurityContext() {
            SecurityContextHolder.clearContext();
        }


        @Test
        @DisplayName("닉네임 변경 성공 테스트")
        void changeMemberNickNameSuccess() {

            when(memberRepository.findByIdAndState(1L, TEST_STATE))
                .thenReturn(Optional.of(mockMember));

            ChangeMemberNickNameRequestDto requestDto = new ChangeMemberNickNameRequestDto(
                TEST_NEW_NICKNAME);

            memberService.changeMemberNickName(requestDto);

            assertAll(
                () -> assertThat(mockMember).isNotNull(),
                () -> assertThat(mockMember.getId()).isEqualTo(TEST_ID),
                () -> assertThat(mockMember.getNickName()).isEqualTo(TEST_NEW_NICKNAME)
            );

        }

        @Test
        @DisplayName("닉네임 변경 실패 테스트 - 이미 존재하는 닉네임인 경우")
        void changeMemberNickNameFail() {

            when(memberRepository.findByIdAndState(1L, State.ACTIVE))
                .thenReturn(Optional.of(mockMember));

            when(memberRepository.existsByNickNameAndState(TEST_NEW_NICKNAME, TEST_STATE))
                .thenReturn(true);

            ChangeMemberNickNameRequestDto requestDto = new ChangeMemberNickNameRequestDto(
                TEST_NEW_NICKNAME);

            assertThatThrownBy(() -> memberService.changeMemberNickName(requestDto))
                .isInstanceOf(DomainException.class)
                .hasMessageContaining(MemberErrorCode.DUPLICATE_NICKNAME.getMessage());

        }

        @Test
        @DisplayName("비밀번호 변경 성공 테스트")
        void changeMemberPasswordSuccess() {

            when(memberRepository.findByIdAndState(1L, State.ACTIVE))
                .thenReturn(Optional.of(mockMember));

            ChangeMemberPasswordRequestDto requestDto = new ChangeMemberPasswordRequestDto(
                TEST_PASSWORD, TEST_PASSWORD);

            memberService.changeMemberPassword(requestDto);

            String newEncodedPassword = passwordEncoder.encode(requestDto.newPassword());

            assertAll(
                () -> assertThat(mockMember).isNotNull(),
                () -> assertThat(mockMember.getId()).isEqualTo(TEST_ID),
                () -> assertThat(mockMember.getPassword()).isEqualTo(newEncodedPassword)
            );

        }

        @Test
        @DisplayName("비밀번호 변경 실패 테스트 - 비밀번호 & 비밀번호 확인일 일치하지 않는 경우")
        void changeMemberPasswordFail() {
            when(memberRepository.findByIdAndState(1L, State.ACTIVE))
                .thenReturn(Optional.of(mockMember));

            ChangeMemberPasswordRequestDto requestDto = new ChangeMemberPasswordRequestDto(
                TEST_PASSWORD, TEST_WRONG_CHECK_PASSWORD);

            assertThatThrownBy(() -> memberService.changeMemberPassword(requestDto))
                .isInstanceOf(DomainException.class)
                .hasMessageContaining(MemberErrorCode.PASSWORD_NOT_SAME.getMessage());

        }

        @Test
        @DisplayName("생년월일 변경 성공 테스트")
        void changeMemberBirthSuccess() {

            when(memberRepository.findByIdAndState(1L, State.ACTIVE))
                .thenReturn(Optional.of(mockMember));

            ChangeMemberBirthDateRequestDto requestDto = new ChangeMemberBirthDateRequestDto(
                TEST_NEW_BIRTHDATE);

            memberService.changeMemberBirth(requestDto);

            assertAll(
                () -> assertThat(mockMember).isNotNull(),
                () -> assertThat(mockMember.getId()).isEqualTo(TEST_ID),
                () -> assertThat(mockMember.getBirthDate()).isEqualTo(TEST_NEW_BIRTHDATE)
            );

        }

        @Test
        @DisplayName("거주지 변경 성공 테스트")
        void changeMemberRegionSuccess() {

            when(memberRepository.findByIdAndState(1L, State.ACTIVE))
                .thenReturn(Optional.of(mockMember));

            ChangeMemberRegionCodeRequestDto requestDto = new ChangeMemberRegionCodeRequestDto(
                TEST_NEW_REGION_CODE);

            memberService.changeMemberRegion(requestDto);

            assertAll(
                () -> assertThat(mockMember).isNotNull(),
                () -> assertThat(mockMember.getId()).isEqualTo(TEST_ID),
                () -> assertThat(mockMember.getRegionCode()).isEqualTo(TEST_NEW_REGION_CODE)
            );

        }

    }
}
