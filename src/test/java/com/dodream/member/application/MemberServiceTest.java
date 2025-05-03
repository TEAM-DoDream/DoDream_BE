package com.dodream.member.application;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import com.dodream.core.config.security.SecurityUtils;
import com.dodream.utils.TestAccount;
import com.dodream.member.domain.Gender;
import com.dodream.member.domain.Member;
import com.dodream.member.dto.request.ChangeMemberNickNameRequestDto;
import com.dodream.member.repository.MemberRepository;
import java.time.LocalDate;
import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@ExtendWith(MockitoExtension.class)
public class MemberServiceTest {

    @Mock
    private MemberRepository memberRepository;
    @Mock
    private UserDetailsService userDetailsService;
    @Mock
    private BCryptPasswordEncoder passwordEncoder;
    @InjectMocks
    private MemberService memberService;

    private static final Long TEST_ID = 1L;
    private static final String TEST_MEMBER_ID = "dodream";
    private static final String TEST_PASSWORD = "hello2025";
    private static final String TEST_NICKNAME = "nickname";
    private static final String TEST_NEW_NICKNAME = "newNickname";
    private static final LocalDate TEST_BIRTHDATE = LocalDate.of(2000, 1, 1);
    private static final Gender TEST_GENDER = Gender.FEMALE;
    private static final String TEST_REGION_CODE = "11110";

    private Member member;

    @Nested
    @DisplayName("나의 계정 - 정보 변경 테스트")
    class MemberInfoTest {

//        @Test
//        @BeforeEach
//        @DisplayName("멤버 데이터 초기 세팅")
//        void initMember() {
//
//            String encodedPassword = passwordEncoder.encode(TEST_PASSWORD);
//
//            Member member = Member.builder()
//                .id(TEST_ID)
//                .memberId(TEST_MEMBER_ID)
//                .password(encodedPassword)
//                .nickName(TEST_NICKNAME)
//                .birthDate(TEST_BIRTHDATE)
//                .gender(TEST_GENDER)
//                .regionCode(TEST_REGION_CODE)
//                .build();
//
//            UserDetails userDetails = userDetailsService.loadUserByUsername()
//
//            when(memberRepository.save(any(Member.class))).thenReturn(member);
//        }


        // 닉네임
        @Test
        @TestAccount
        @DisplayName("닉네임 변경 성공 테스트")
        void changeMemberNickNameSuccess() {

            // given
//            String encodedPassword = passwordEncoder.encode(TEST_PASSWORD);

//            Member member = Member.builder()
//                .id(TEST_ID)
//                .memberId(TEST_MEMBER_ID)
//                .password(encodedPassword)
//                .nickName(TEST_NICKNAME)
//                .birthDate(TEST_BIRTHDATE)
//                .gender(TEST_GENDER)
//                .regionCode(TEST_REGION_CODE)
//                .build();

            when(memberRepository.findById(1L)).thenReturn(Optional.of(member));
            when(memberRepository.save(any(Member.class))).thenReturn(member);

            ChangeMemberNickNameRequestDto requestDto = new ChangeMemberNickNameRequestDto(
                TEST_NEW_NICKNAME);

            // when
            memberService.changeMemberNickName(requestDto);

            // then
            assertAll(
                () -> assertThat(member.getNickName()).isEqualTo(TEST_NEW_NICKNAME)
            );

        }

        @Test
        @DisplayName("닉네임 변경 실패 테스트 - 이미 존재하는 닉네임인 경우")
        void changeMemberNickNameFail() {

        }

        // 비밀번호
        @Test
        @DisplayName("비밀번호 변경 성공 테스트")
        void changeMemberPasswordSuccess() {

        }

        @Test
        @DisplayName("비밀번호 변경 실패 테스트")
        void changeMemberPasswordFail() {

        }

        // 생년월일
        @Test
        @DisplayName("생년월일 변경 성공 테스트")
        void changeMemberBirthSuccess() {

        }

        @Test
        @DisplayName("생년월일 변경 실패 테스트")
        void changeMemberBirthFail() {

        }

        // 거주지
        @Test
        @DisplayName("거주지 변경 성공 테스트")
        void changeMemberRegionSuccess() {

        }

        @Test
        @DisplayName("거주지 변경 실패 테스트 ")
        void changeMemberRegionFail() {

        }

    }
}
