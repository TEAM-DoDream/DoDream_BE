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
import com.dodream.member.dto.request.ChangeMemberRegionRequestDto;
import com.dodream.member.dto.response.ChangeMemberBirthDateResponseDto;
import com.dodream.member.dto.response.ChangeMemberNickNameResponseDto;
import com.dodream.member.dto.response.ChangeMemberRegionResponseDto;
import com.dodream.member.dto.response.CheckMemberNickNameResponseDto;
import com.dodream.member.exception.MemberErrorCode;
import com.dodream.member.repository.MemberRepository;
import com.dodream.region.domain.Region;
import com.dodream.region.repository.RegionRepository;
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
    private RegionRepository regionRepository;
    @Mock
    private BCryptPasswordEncoder passwordEncoder;
    @InjectMocks
    private MemberService memberService;

    private static final Long TEST_ID = 1L;
    private static final String TEST_LOGIN_ID = "dodream";
    private static final String TEST_PASSWORD = "password";
    private static final String TEST_WRONG_CHECK_PASSWORD = "wrongCheckPassword";
    private static final String TEST_NICKNAME = "nickname";
    private static final String TEST_NEW_NICKNAME = "newNickname";
    private static final LocalDate TEST_BIRTHDATE = LocalDate.of(2000, 1, 1);
    private static final LocalDate TEST_NEW_BIRTHDATE = LocalDate.of(2002, 12, 24);
    private static final Gender TEST_GENDER = Gender.FEMALE;
    private static final State TEST_STATE = State.ACTIVE;
    private static final String TEST_REGION_CODE1 = "11110";
    private static final String TEST_REGION_NAME1 = "서울 종로구";
    private static final String TEST_REGION_CODE2 = "11170";
    private static final String TEST_REGION_NAME2 = "서울 용산구";
    private Member mockMember;
    private Region region1;
    private Region region2;

    @Nested
    @DisplayName("나의 계정 - 정보 변경 테스트")
    class MemberInfoTest {

        @BeforeEach
        void setupSecurityContext() {

            //given
            region1 = Region.builder()
                .id(1L)
                .regionCode(TEST_REGION_CODE1)
                .regionName(TEST_REGION_NAME1)
                .build();

            region2 = Region.builder()
                .id(2L)
                .regionCode(TEST_REGION_CODE2)
                .regionName(TEST_REGION_NAME2)
                .build();

            mockMember = Member.builder()
                .id(TEST_ID)
                .loginId(TEST_LOGIN_ID)
                .password(passwordEncoder.encode(TEST_PASSWORD))
                .nickName(TEST_NICKNAME)
                .birthDate(TEST_BIRTHDATE)
                .gender(TEST_GENDER)
                .region(region1)
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

            ChangeMemberNickNameResponseDto responseDto = memberService.changeMemberNickName(
                requestDto);

            assertAll(
                () -> assertThat(mockMember).isNotNull(),
                () -> assertThat(mockMember.getId()).isEqualTo(responseDto.memberId()),
                () -> assertThat(mockMember.getNickName()).isEqualTo(responseDto.newNickname())
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

            ChangeMemberBirthDateResponseDto responseDto = memberService.changeMemberBirth(
                requestDto);

            assertAll(
                () -> assertThat(mockMember).isNotNull(),
                () -> assertThat(mockMember.getId()).isEqualTo(responseDto.memberId()),
                () -> assertThat(mockMember.getBirthDate()).isEqualTo(responseDto.newBirthDate())
            );

        }

        @Test
        @DisplayName("거주지 변경 성공 테스트")
        void changeMemberRegionSuccess() {

            when(memberRepository.findByIdAndState(1L, State.ACTIVE))
                .thenReturn(Optional.of(mockMember));

            when(regionRepository.findByRegionCode(TEST_REGION_CODE2))
                .thenReturn(Optional.of(region2));

            ChangeMemberRegionRequestDto requestDto = new ChangeMemberRegionRequestDto(
                TEST_REGION_CODE2);

            ChangeMemberRegionResponseDto responseDto = memberService.changeMemberRegion(requestDto);

            assertAll(
                () -> assertThat(mockMember).isNotNull(),
                () -> assertThat(mockMember.getId()).isEqualTo(responseDto.memberId()),
                () -> assertThat(mockMember.getRegion().getRegionCode()).isEqualTo(
                    responseDto.newRegionCode()),
                () -> assertThat(mockMember.getRegion().getRegionName()).isEqualTo(
                    responseDto.newRegionName())
            );

        }

    }
}
