package com.dodream.todo.application;

import com.dodream.core.infrastructure.security.CustomUserDetails;
import com.dodream.member.domain.Gender;
import com.dodream.member.domain.Member;
import com.dodream.member.domain.State;
import com.dodream.member.repository.MemberRepository;
import com.dodream.region.domain.Region;
import com.dodream.region.repository.RegionRepository;
import java.time.LocalDate;
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
public class TodoServiceTest {

    @Mock
    private MemberRepository memberRepository;
    @Mock
    private RegionRepository regionRepository;
    @Mock
    private BCryptPasswordEncoder passwordEncoder;
    @InjectMocks
    private TodoService todoService;

    private static final Long TEST_ID = 1L;
    private static final String TEST_LOGIN_ID = "dodream";
    private static final String TEST_PASSWORD = "password";
    private static final String TEST_NICKNAME = "nickname";
    private static final LocalDate TEST_BIRTHDATE = LocalDate.of(2000, 1, 1);
    private static final Gender TEST_GENDER = Gender.FEMALE;
    private static final State TEST_STATE = State.ACTIVE;
    private static final String TEST_REGION_CODE1 = "11110";
    private static final String TEST_REGION_NAME1 = "서울 종로구";
    private Member mockMember;
    private Region region1;


    @Nested
    @DisplayName("홈화면")
    class HomeTodoTest {

        @BeforeEach
        void setupSecurityContext() {

            //given
            region1 = Region.builder()
                .id(1L)
                .regionCode(TEST_REGION_CODE1)
                .regionName(TEST_REGION_NAME1)
                .build();

            mockMember = Member.builder()
                .id(TEST_ID)
                .loginId(TEST_LOGIN_ID)
                .password(passwordEncoder.encode(TEST_PASSWORD))
                .nickName(TEST_NICKNAME)
                .birthDate(TEST_BIRTHDATE)
                .gender(TEST_GENDER)
                .state(TEST_STATE)
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
        @DisplayName("타유저 목록 조회 (3개씩)")
        void getOtherTodoAtHome() {

        }

        @Test
        @DisplayName("타유저 투두 그룹 조회")
        void getOtherOneTodoGroup() {

        }

        @Test
        @DisplayName("타유저 투두 메모 조회")
        void getOtherOneTodo() {

        }

        @Test
        @DisplayName("특정 직업 타유저 투두 그룹 목록 조회")
        void getOneJobOtherOneTodoGroupList() {

        }

        @Test
        @DisplayName("특정 직업 타유저 투두 그룹 목록 조회")
        void getSimpleOneJobOtherOneTodoGroupList() {

        }


    }


}
