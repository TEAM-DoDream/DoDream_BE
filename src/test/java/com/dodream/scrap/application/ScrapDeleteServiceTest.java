package com.dodream.scrap.application;

import com.dodream.core.exception.DomainException;
import com.dodream.core.infrastructure.security.CustomUserDetails;
import com.dodream.member.domain.Gender;
import com.dodream.member.domain.Member;
import com.dodream.region.domain.Region;
import com.dodream.scrap.domain.entity.MemberRecruitScrap;
import com.dodream.scrap.domain.entity.MemberTrainingScrap;
import com.dodream.scrap.domain.value.RecruitCloseType;
import com.dodream.scrap.dto.response.ScrapDeletedResponse;
import com.dodream.scrap.repository.recruit.MemberRecruitScrapRepository;
import com.dodream.scrap.repository.training.MemberTrainingScrapRepository;
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

import java.time.LocalDate;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.BDDMockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("[ScrapDeleteService] - 저장 공고 삭제 단위테스트")
public class ScrapDeleteServiceTest {

    @Mock
    private MemberRecruitScrapRepository memberRecruitScrapRepository;

    @Mock
    private MemberTrainingScrapRepository memberTrainingScrapRepository;

    @Mock
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @InjectMocks
    private ScrapDeleteService scrapDeleteService;

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

    private static final String TEST_RECRUIT_ID = "recruit123";
    private static final String TEST_TRAINING_ID = "AGS102394832949";

    private Member mockMember;
    private Region mockRegion;

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
                .expirationDate("2025-12-31")
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

    @Nested
    @DisplayName("[deleteRecruitScrap] - 채용 스크랩 삭제 테스트")
    class DeleteRecruitScrapTest {

        @Test
        @DisplayName("채용 공고 정상 삭제 테스트")
        void deleteRecruitScrap_success() {
            // given
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            CustomUserDetails customUserDetails = (CustomUserDetails) authentication.getPrincipal();

            given(memberRecruitScrapRepository.findByRecruitIdAndMemberId(TEST_RECRUIT_ID, customUserDetails.getId()))
                    .willReturn(Optional.of(mockMemberRecruitScrap));

            // when
            ScrapDeletedResponse result
                    = scrapDeleteService.deleteRecruitScrap(TEST_RECRUIT_ID, customUserDetails);

            // then
            verify(memberRecruitScrapRepository)
                    .delete(mockMemberRecruitScrap);

            assertThat(result.id()).isEqualTo(mockMemberRecruitScrap.getId());
        }

        @Test
        @DisplayName("채용 공고 삭제 실패 테스트 - 스크랩 정보가 없는 경우")
        void deleteRecruitScrap_fail_noScrap() {
            // given
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            CustomUserDetails customUserDetails = (CustomUserDetails) authentication.getPrincipal();

            given(memberRecruitScrapRepository.findByRecruitIdAndMemberId(TEST_RECRUIT_ID, customUserDetails.getId()))
                    .willReturn(Optional.empty());

            // when & then
            assertThatThrownBy(() -> scrapDeleteService.deleteRecruitScrap(TEST_RECRUIT_ID, customUserDetails))
                    .isInstanceOf(DomainException.class)
                    .hasMessageContaining("저장된 공고가 아닙니다.");
        }
    }

    @Nested
    @DisplayName("[deleteTrainingScrap] - 훈련과정 스크랩 삭제 테스트")
    class DeleteTrainingScrapTest{

        @Test
        @DisplayName("훈련과정 스크랩 정상 삭제 테스트")
        void deleteTrainingScrap_success() {
            // given
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            CustomUserDetails customUserDetails = (CustomUserDetails) authentication.getPrincipal();

            given(memberTrainingScrapRepository.findByTrainingIdAndMemberId(TEST_TRAINING_ID, customUserDetails.getId()))
                    .willReturn(Optional.of(mockMemberTrainingScrap));

            // when
            ScrapDeletedResponse result = scrapDeleteService.deleteTrainingScrap(TEST_TRAINING_ID, customUserDetails);

            // then
            verify(memberTrainingScrapRepository)
                    .delete(mockMemberTrainingScrap);

            assertThat(result).isNotNull();
            assertThat(result.id()).isEqualTo(mockMemberTrainingScrap.getId());
        }

        @Test
        @DisplayName("훈련과정 스크랩 삭제 실패 테스트 - 저장되지 않은 훈련과정")
        void deleteTrainingScrap_fail_noScrap() {
            // given
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            CustomUserDetails customUserDetails = (CustomUserDetails) authentication.getDetails();

            given(memberTrainingScrapRepository.findByTrainingIdAndMemberId(TEST_TRAINING_ID, customUserDetails.getId()))
                    .willReturn(Optional.empty());

            // when & then
            assertThatThrownBy(() -> scrapDeleteService.deleteTrainingScrap(TEST_TRAINING_ID, customUserDetails))
                    .isInstanceOf(DomainException.class)
                    .hasMessageContaining("저장된 공고가 아닙니다.");
        }
    }
}
