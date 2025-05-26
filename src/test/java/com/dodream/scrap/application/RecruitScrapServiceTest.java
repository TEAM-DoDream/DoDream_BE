package com.dodream.scrap.application;

import com.dodream.core.exception.DomainException;
import com.dodream.core.infrastructure.security.CustomUserDetails;
import com.dodream.member.domain.Gender;
import com.dodream.member.domain.Member;
import com.dodream.member.domain.State;
import com.dodream.member.repository.MemberRepository;
import com.dodream.recruit.dto.response.RecruitResponseListApiDto;
import com.dodream.recruit.infrastructure.RecruitApiCaller;
import com.dodream.recruit.infrastructure.mapper.RecruitMapper;
import com.dodream.region.domain.Region;
import com.dodream.scrap.application.save.RecruitScrapService;
import com.dodream.scrap.domain.entity.MemberRecruitScrap;
import com.dodream.scrap.dto.response.RecruitSavedResponseDto;
import com.dodream.scrap.repository.MemberRecruitScrapRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
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
import java.util.Optional;

import static org.mockito.BDDMockito.*;
import static org.assertj.core.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("[RecruitScrapService] - 채용 공고 저장 서비스 테스트")
public class RecruitScrapServiceTest {

    @Mock
    private MemberRecruitScrapRepository memberRecruitScrapRepository;

    @Mock
    private RecruitApiCaller recruitApiCaller;

    @Mock
    private RecruitMapper recruitMapper;

    @Mock
    private MemberRepository memberRepository;

    @Mock
    private BCryptPasswordEncoder passwordEncoder;

    @InjectMocks
    private RecruitScrapService recruitScrapService;


    private static final Long TEST_ID = 1L;
    private static final String TEST_LOGIN_ID = "dodream";
    private static final String TEST_PASSWORD = "password";
    private static final String TEST_NICKNAME = "nickname";
    private static final LocalDate TEST_BIRTHDATE = LocalDate.of(2000, 1, 1);
    private static final Gender TEST_GENDER = Gender.FEMALE;

    private static final String TEST_REGION_CODE = "11110";
    private static final String TEST_REGION_NAME = "서울 종로구";

    private static final String API_CALLER_RESULT = "reponse";

    private Member mockMember;
    private Region mockRegion;
    private RecruitResponseListApiDto.Jobs.Job mockRecruitDetail;
    private RecruitResponseListApiDto.Jobs jobs;
    private RecruitResponseListApiDto TEST_LIST_API_DTO;

    private static final String TEST_RECRUIT_ID = "50493749";

    @BeforeEach
    void setUp(){
        mockRegion = Region.builder()
                .id(1L)
                .regionCode(TEST_REGION_CODE)
                .regionName(TEST_REGION_NAME)
                .build();

        mockMember = Member.builder()
                .id(TEST_ID)
                .loginId(TEST_LOGIN_ID)
                .password(passwordEncoder.encode(TEST_PASSWORD))
                .nickName(TEST_NICKNAME)
                .birthDate(TEST_BIRTHDATE)
                .gender(TEST_GENDER)
                .region(mockRegion)
                .build();

        mockRecruitDetail = new RecruitResponseListApiDto.Jobs.Job(
                "http://url", 1,
                new RecruitResponseListApiDto.Jobs.Job.Company(
                        new RecruitResponseListApiDto.Jobs.Job.Company(
                                new RecruitResponseListApiDto.Jobs.Job.Company.Detail("http://href", "회사명")
                        ).detail()
                ),
                new RecruitResponseListApiDto.Jobs.Job.Position(
                        "개발자", null,
                        new RecruitResponseListApiDto.Jobs.Job.Position.Location(
                                TEST_REGION_CODE, TEST_REGION_NAME
                        ),
                        new RecruitResponseListApiDto.Jobs.Job.Position.JobType(
                                "1", "정규직"
                        ), null, null,
                        new RecruitResponseListApiDto.Jobs.Job.Position.ExperienceLevel(
                                1, 1,1, "경력"
                        ),
                        new RecruitResponseListApiDto.Jobs.Job.Position.RequiredEducationLevel(
                                "1", "대졸이상"
                        )
                ),
                "키워드",
                new RecruitResponseListApiDto.Jobs.Job.Salary("code", "연봉 3000"),
                "RID123",
                "2025-01-01", "2025-01-01", "2025-01-01",
                "2025-01-01", "2025-12-31", "2025-12-31",
                new RecruitResponseListApiDto.Jobs.Job.CloseType("code", "상시"),
                "100", "10"
        );

        jobs = new RecruitResponseListApiDto.Jobs(
                1,
                0,
                "1",
                List.of(mockRecruitDetail)
        );
        TEST_LIST_API_DTO = new RecruitResponseListApiDto(jobs);

        CustomUserDetails userDetails = new CustomUserDetails(mockMember);

        Authentication authentication = new UsernamePasswordAuthenticationToken(
                userDetails, "", userDetails.getAuthorities());

        SecurityContext context = SecurityContextHolder.createEmptyContext();
        context.setAuthentication(authentication);
        SecurityContextHolder.setContext(context);
    }

    @Nested
    @DisplayName("[saveRecruit] - 채용 정보 저장 성공 테스트")
    class SaveRecruitTest{

        @Test
        @DisplayName("채용 정보 저장 성공")
        void saveRecruit_success(){
            // given
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            CustomUserDetails customUserDetails = (CustomUserDetails) authentication.getPrincipal();

            given(memberRepository.findById(customUserDetails.getId())).willReturn(Optional.of(mockMember));
            given(memberRecruitScrapRepository.countByMemberId(customUserDetails.getId())).willReturn(20);
            given(memberRecruitScrapRepository.existsByRecruitIdAndMemberId(TEST_RECRUIT_ID, customUserDetails.getId()))
                    .willReturn(false);


            given(recruitApiCaller.recruitDetailAPiCaller(TEST_RECRUIT_ID)).willReturn(API_CALLER_RESULT);
            given(recruitMapper.parse(API_CALLER_RESULT, RecruitResponseListApiDto.class)).willReturn(TEST_LIST_API_DTO);

            ArgumentCaptor<MemberRecruitScrap> scrapCaptor = ArgumentCaptor.forClass(MemberRecruitScrap.class);
            given(memberRecruitScrapRepository.save(scrapCaptor.capture())).willAnswer(inv -> inv.getArgument(0));

            // when
            RecruitSavedResponseDto result = recruitScrapService.saveRecruit(TEST_RECRUIT_ID);

            // then
            assertThat(result.isScrap()).isEqualTo(true);
            assertThat(result.userId()).isEqualTo(customUserDetails.getId());
        }
    }

    @Nested
    @DisplayName("[saveRecruit] - 채용 정보 저장 예외 테스트")
    class SaveRecruitFailTest{

        @Test
        @DisplayName("회원 정보가 없는 경우")
        void saveRecruit_fail_nonMember(){
            // given
            given(memberRepository.findById(TEST_ID)).willReturn(Optional.empty());

            // when & then
            assertThatThrownBy(() -> recruitScrapService.saveRecruit(TEST_RECRUIT_ID))
                    .isInstanceOf(DomainException.class)
                    .hasMessageContaining("회원 정보를 찾을 수 없습니다");
        }

        @Test
        @DisplayName("저장된 스크랩이 50개가 넘는 경우")
        void saveRecruit_fail_exceed(){
            // given
            given(memberRepository.findById(TEST_ID)).willReturn(Optional.of(mockMember));
            given(memberRecruitScrapRepository.countByMemberId(TEST_ID)).willReturn(51);

            // when & then
            assertThatThrownBy(() -> recruitScrapService.saveRecruit(TEST_RECRUIT_ID))
                    .isInstanceOf(DomainException.class)
                    .hasMessageContaining("저장 공고는 50개를 넘을 수 없습니다.");
        }

        @Test
        @DisplayName("중복으로 공고를 저장한 경우")
        void saveRecruit_fail_duplicate(){
            // given
            given(memberRepository.findById(TEST_ID)).willReturn(Optional.of(mockMember));
            given(memberRecruitScrapRepository.countByMemberId(TEST_ID)).willReturn(10);
            given(memberRecruitScrapRepository.existsByRecruitIdAndMemberId(TEST_RECRUIT_ID, TEST_ID)).willReturn(true);

            // when & then
            assertThatThrownBy(() -> recruitScrapService.saveRecruit(TEST_RECRUIT_ID))
                    .isInstanceOf(DomainException.class)
                    .hasMessageContaining("이미 저장된 공고입니다.");
        }
    }
}
