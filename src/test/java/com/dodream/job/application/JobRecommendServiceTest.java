package com.dodream.job.application;

import com.dodream.core.exception.DomainException;
import com.dodream.core.infrastructure.security.CustomUserDetails;
import com.dodream.integration.utils.TestCustomUser;
import com.dodream.job.domain.Job;
import com.dodream.job.dto.request.recommend.OnboardingAnswerSet;
import com.dodream.job.dto.response.ChatResponse;
import com.dodream.job.dto.response.JobRecommendationResponse;
import com.dodream.job.infrastructure.JobImageUrlGenerator;
import com.dodream.job.infrastructure.caller.ClovaChatCompletionCaller;
import com.dodream.job.infrastructure.mapper.ClovaChatResponseMapper;
import com.dodream.job.infrastructure.mapper.JobRecommendMapper;
import com.dodream.job.repository.JobRepository;
import com.dodream.member.domain.Gender;
import com.dodream.member.domain.Member;
import com.dodream.member.domain.State;
import com.dodream.region.domain.Region;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.BDDMockito.*;

@ExtendWith(MockitoExtension.class)
public class JobRecommendServiceTest {

    @Mock
    private JobRepository jobRepository;

    @Mock
    private ClovaChatCompletionCaller clovaChatCompletionCaller;

    @Mock
    private ClovaChatResponseMapper clovaChatResponseMapper;

    @Mock
    private JobRecommendMapper jobRecommendMapper;

    @Mock
    private JobImageUrlGenerator jobImageUrlGenerator;

    @Mock
    private BCryptPasswordEncoder passwordEncoder;

    @InjectMocks
    private JobRecommendService jobRecommendService;

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


    @Nested
    @DisplayName("[recommendJob] - 직업 추천 api 테스트")
    class RecommendJob{
        @Test
        @TestCustomUser
        @DisplayName("직업추천 api 성공 테스트")
        void recommendJob_success() {
            // given
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            CustomUserDetails customUserDetails = (CustomUserDetails) authentication.getPrincipal();

            OnboardingAnswerSet answerSet = new OnboardingAnswerSet(List.of());

            given(jobRepository.findAll()).willReturn(List.of());

            String result = "RESULT_JSON";
            String rawJson = "{ \"recommendedJobs\": [] }";
            JobRecommendationResponse jobRecommendationResponse = new JobRecommendationResponse(List.of());

            // ChatResponse 객체 직접 생성
            ChatResponse.Message message = new ChatResponse.Message("assistant", rawJson);
            ChatResponse.Usage usage = new ChatResponse.Usage(10, 10, 20);
            ChatResponse.Result chatResult = new ChatResponse.Result(message, "stop", usage);
            ChatResponse chatResponse = new ChatResponse(chatResult);

            given(clovaChatCompletionCaller.clovaChatCompletionApiCaller(any(), any())).willReturn(result);
            given(clovaChatResponseMapper.jsonToChatResponse(result)).willReturn(chatResponse);
            given(jobRecommendMapper.parse(rawJson)).willReturn(jobRecommendationResponse);

            // when
            JobRecommendationResponse response = jobRecommendService.recommendJob(customUserDetails, answerSet);

            // then
            assertThat(response).isNotNull();
        }

        @Test
        @DisplayName("직업 추천 api 실패 테스트 - 유저 정보 없음")
        void recommendJob_fail() {
            // given
            OnboardingAnswerSet answerSet = new OnboardingAnswerSet(List.of());

            // when & then
            assertThatThrownBy(() -> jobRecommendService.recommendJob(null, answerSet))
                    .isInstanceOf(DomainException.class)
                    .hasMessageContaining("온보딩을 진행한 유저 정보를 찾을 수 없습니다.");
        }
    }



}
