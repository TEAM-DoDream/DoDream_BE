package com.dodream.scrap.application;

import com.dodream.core.infrastructure.security.CustomUserDetails;
import com.dodream.member.domain.Gender;
import com.dodream.member.domain.Member;
import com.dodream.region.domain.Region;
import com.dodream.scrap.domain.entity.MemberRecruitScrap;
import com.dodream.scrap.domain.entity.MemberTrainingScrap;
import com.dodream.scrap.domain.value.Category;
import com.dodream.scrap.domain.value.RecruitCloseType;
import com.dodream.scrap.dto.response.IsScrapCheckedResponse;
import com.dodream.scrap.dto.response.RecruitScrapResponseDto;
import com.dodream.scrap.dto.response.TrainingScrapResponseDto;
import com.dodream.scrap.repository.recruit.MemberRecruitScrapRepository;
import com.dodream.scrap.repository.training.MemberTrainingScrapRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("[ScrapSearchServiceTest] - 스크랩 조회 테스트")
public class ScrapSearchServiceTest {

    @Mock
    private MemberRecruitScrapRepository memberRecruitScrapRepository;

    @Mock
    private MemberTrainingScrapRepository memberTrainingScrapRepository;

    @Mock
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @InjectMocks
    private ScrapSearchService scrapSearchService;

    private MemberRecruitScrap mockMemberRecruitScrap;
    private MemberTrainingScrap mockMemberTrainingScrap;

    private static final Long TEST_ID = 1L;
    private static final String TEST_LOGIN_ID = "dodream";
    private static final String TEST_PASSWORD = "password";
    private static final String TEST_NICKNAME = "nickname";
    private static final LocalDate TEST_BIRTHDATE = LocalDate.of(2000, 1, 1);
    private static final Gender TEST_GENDER = Gender.FEMALE;

    private static final String TEST_REGION_CODE = "11110";
    private static final String TEST_REGION_NAME = "서울 종로구";

    private static final String TEST_LOC_NAME = "서울 종로구";
    private static final int PAGE_NUM = 0;
    private static final int PAGE_SIZE = 6;
    private static final String SORT_BY = "최신 순";

    private Member mockMember;

    @BeforeEach
    void setUp(){
        Region mockRegion = Region.builder()
                .id(1L)
                .regionCode(TEST_REGION_CODE)
                .regionName(TEST_REGION_NAME)
                .build();

        mockMember = Member.builder()
                .id(TEST_ID)
                .loginId(TEST_LOGIN_ID)
                .password(bCryptPasswordEncoder.encode(TEST_PASSWORD))
                .nickName(TEST_NICKNAME)
                .birthDate(TEST_BIRTHDATE)
                .gender(TEST_GENDER)
                .region(mockRegion)
                .build();

        mockMemberRecruitScrap = MemberRecruitScrap.builder()
                .recruitId("recruit123")
                .title("Developer")
                .companyName("회사명")
                .expirationDate("2025-12-31T15:45:00+0900")
                .locationName("서울 강남구")
                .jobType("FULL_TIME")
                .experienceLevel("MID")
                .educationLevel("BACHELOR")
                .closeType(RecruitCloseType.SPECIFIC_DATE)
                .recruitUrl("http://example.com/recruit/1")
                .member(mockMember)
                .build();

        mockMemberTrainingScrap = MemberTrainingScrap.builder()
                .trainingId("training123")
                .trainingName("자바 백엔드 개발자 과정")
                .trainingOrgName("한국직업전문학교")
                .trainingOrgAddr("서울특별시 강남구")
                .trainingStartDate(LocalDate.of(2025, 6, 1))
                .trainingEndDate(LocalDate.of(2025, 8, 31))
                .trainingDegree("1")
                .trainingManage(1000000)
                .member(mockMember)
                .build();

        CustomUserDetails userDetails = new CustomUserDetails(mockMember);

        Authentication authentication = new UsernamePasswordAuthenticationToken(
                userDetails, "", userDetails.getAuthorities());

        SecurityContext context = SecurityContextHolder.createEmptyContext();
        context.setAuthentication(authentication);
        SecurityContextHolder.setContext(context);
    }

    @Test
    @DisplayName("[getRecruitScrapList] - 채용공고 스크랩 리스트 성공")
    void getRecruitScrapList_success() {
        // given
        List<MemberRecruitScrap> scrapList = List.of(mockMemberRecruitScrap);
        Page<MemberRecruitScrap> mockPage = new PageImpl<>(scrapList);

        when(
                memberRecruitScrapRepository.searchWithFilter(
                        mockMember.getId(), TEST_LOC_NAME, SORT_BY, PAGE_SIZE, PAGE_NUM
                )
        ).thenReturn(mockPage);

        // when
        Page<RecruitScrapResponseDto> result = scrapSearchService.getRecruitScrapList(
                new CustomUserDetails(mockMember), PAGE_NUM, TEST_LOC_NAME, SORT_BY
        );

        // then
        assertThat(result).isNotNull();
        assertThat(result.getTotalElements()).isEqualTo(1);
    }

    @Test
    @DisplayName("[getTrainingScrapList] - 훈련과정 스크랩 리스트 검색 성공")
    void getTrainingScrapList_success() {
        // given
        List<MemberTrainingScrap> trainingScrapList = List.of(mockMemberTrainingScrap);
        Page<MemberTrainingScrap> mockPage = new PageImpl<>(trainingScrapList);

        when(memberTrainingScrapRepository.searchWithFilter(
                mockMember.getId(), TEST_LOC_NAME, SORT_BY, PAGE_SIZE, PAGE_NUM
        )).thenReturn(mockPage);

        // when
        Page<TrainingScrapResponseDto> result = scrapSearchService.getTrainingScrapList(
                new CustomUserDetails(mockMember), PAGE_NUM, TEST_LOC_NAME, SORT_BY
        );

        // then
        assertThat(result).isNotNull();
        assertThat(result.getTotalElements()).isEqualTo(1);
    }

    @Test
    @DisplayName("[isScrapCheck] - 스크랩 체크 성공 테스트")
    void isScrapCheck_success() {
        // given
        String testRecruitId = "test-recruit-190293";
        List<String> idList = List.of(testRecruitId);
        when(memberRecruitScrapRepository.findScrapedRecruitId(mockMember.getId(), idList))
                .thenReturn(List.of(testRecruitId));

        // when
        List<IsScrapCheckedResponse> result = scrapSearchService.isScrapCheck(
                new CustomUserDetails(mockMember), Category.RECRUIT, idList
        );

        // then
        assertThat(result).hasSize(1);
        assertThat(result.get(0).isScrap()).isTrue();
    }
}
