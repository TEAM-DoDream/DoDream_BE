package com.dodream.scrap.application;

import com.dodream.core.exception.DomainException;
import com.dodream.core.infrastructure.security.CustomUserDetails;
import com.dodream.member.domain.Gender;
import com.dodream.member.domain.Member;
import com.dodream.member.repository.MemberRepository;
import com.dodream.region.domain.Region;
import com.dodream.scrap.application.save.TrainingScrapService;
import com.dodream.scrap.domain.entity.MemberTrainingScrap;
import com.dodream.scrap.domain.value.TrainingType;
import com.dodream.scrap.dto.request.TrainingSaveReqeustDto;
import com.dodream.scrap.dto.response.TrainingSavedResponseDto;
import com.dodream.scrap.repository.training.MemberTrainingScrapRepository;
import com.dodream.training.dto.response.TrainingDetailApiResponse;
import com.dodream.training.infrastructure.caller.TrainingApiCaller;
import com.dodream.training.infrastructure.mapper.TrainingMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.time.LocalDate;
import java.util.Optional;

import static org.mockito.BDDMockito.*;
import static org.assertj.core.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class TrainingScrapServiceTest {

    @Mock
    private TrainingMapper<TrainingDetailApiResponse> trainingDetailResponseDtoMapper;

    @Mock
    @Qualifier("bootCampApiCaller")
    private TrainingApiCaller bootcampApiCaller;

    @Mock
    @Qualifier("dualTrainingApiCaller")
    private TrainingApiCaller dualTrainingApiCaller;

    @Mock
    private MemberRepository memberRepository;

    @Mock
    private MemberTrainingScrapRepository memberTrainingScrapRepository;

    @Mock
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @InjectMocks
    private TrainingScrapService trainingScrapService;

    private static final Long TEST_ID = 1L;
    private static final String TEST_LOGIN_ID = "dodream";
    private static final String TEST_PASSWORD = "password";
    private static final String TEST_NICKNAME = "nickname";
    private static final LocalDate TEST_BIRTHDATE = LocalDate.of(2000, 1, 1);
    private static final Gender TEST_GENDER = Gender.FEMALE;

    private static final String TEST_REGION_CODE = "11110";
    private static final String TEST_REGION_NAME = "서울 종로구";

    private static final String API_CALLER_RESULT = "response";
    private static final String TEST_TRPR_ID = "AGS10002316559495";
    private static final String TEST_TRPR_DEG = "3";
    private static final String TEST_TORG_ID = "5000612356128";
    private static final String TEST_START_DATE = "2025/05/26";
    private static final String TEST_END_DATE = "2025/06/27";

    private static final TrainingType BOOTCAMP = TrainingType.BOOTCAMP;
    private static final TrainingType DUAL = TrainingType.DUAL;

    private Member mockMember;
    private Region mockRegion;
    private TrainingDetailApiResponse.InstBaseInfo instBaseInfo;
    private TrainingDetailApiResponse.InstDetailInfo instDetailInfo;
    private static TrainingDetailApiResponse TEST_DETAIL_RESPONSE;
    private static TrainingSaveReqeustDto TEST_SAVE_REQEUST;

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

        instBaseInfo = new TrainingDetailApiResponse.InstBaseInfo(
                "https://~",
                "경기도 안양시 만안구",
                3,
                "요양보호사 자격증 취득 과정 (사회복지사)",
                "느티나무요양보호사교육원",
                250000
        );

        instDetailInfo = new TrainingDetailApiResponse.InstDetailInfo("350000");

        TEST_DETAIL_RESPONSE = new TrainingDetailApiResponse(instBaseInfo, instDetailInfo);
        TEST_SAVE_REQEUST = new TrainingSaveReqeustDto(
                TEST_TRPR_ID, TEST_TRPR_DEG, TEST_TORG_ID, TEST_START_DATE, TEST_END_DATE
        );

        CustomUserDetails userDetails = new CustomUserDetails(mockMember);

        Authentication authentication = new UsernamePasswordAuthenticationToken(
                userDetails, "", userDetails.getAuthorities());

        SecurityContext context = SecurityContextHolder.createEmptyContext();
        context.setAuthentication(authentication);
        SecurityContextHolder.setContext(context);
    }

    @Nested
    @DisplayName("[saveTraining] 훈련과정 공고 저장 테스트")
    class SaveTraining{

        @Test
        @DisplayName("국민내일배움카드 훈련과정 저장 성공 테스트")
        void saveTraining_bootcamp_success(){
            // given
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            CustomUserDetails customUserDetails = (CustomUserDetails) authentication.getPrincipal();

            given(memberRepository.findById(customUserDetails.getId())).willReturn(Optional.of(mockMember));
            given(memberTrainingScrapRepository.countByMemberId(customUserDetails.getId())).willReturn(1);
            given(memberTrainingScrapRepository.existsByTrainingIdAndMemberId(TEST_TRPR_ID, TEST_ID))
                    .willReturn(false);

            given(trainingDetailResponseDtoMapper.jsonToResponseDto(any()))
                    .willReturn(TEST_DETAIL_RESPONSE);

            ArgumentCaptor<MemberTrainingScrap> captor = ArgumentCaptor.forClass(MemberTrainingScrap.class);
            given(memberTrainingScrapRepository.save(captor.capture())).willAnswer(inv -> inv.getArgument(0));

            // when
            TrainingSavedResponseDto result = trainingScrapService.saveTraining(TEST_SAVE_REQEUST, BOOTCAMP);

            // then
            assertThat(result).isNotNull();
            assertThat(result.isScrap()).isTrue();
        }

        @Test
        @DisplayName("일병행 훈련과정 저장 성공 테스트")
        void dualTraining_success(){
            // given
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            CustomUserDetails customUserDetails = (CustomUserDetails) authentication.getPrincipal();

            given(memberRepository.findById(customUserDetails.getId())).willReturn(Optional.of(mockMember));
            given(memberTrainingScrapRepository.countByMemberId(customUserDetails.getId())).willReturn(1);
            given(memberTrainingScrapRepository.existsByTrainingIdAndMemberId(TEST_TRPR_ID, TEST_ID))
                    .willReturn(false);

            given(trainingDetailResponseDtoMapper.jsonToResponseDto(any()))
                    .willReturn(TEST_DETAIL_RESPONSE);

            ArgumentCaptor<MemberTrainingScrap> captor = ArgumentCaptor.forClass(MemberTrainingScrap.class);
            given(memberTrainingScrapRepository.save(captor.capture())).willAnswer(inv -> inv.getArgument(0));

            // when
            TrainingSavedResponseDto result = trainingScrapService.saveTraining(TEST_SAVE_REQEUST, DUAL);

            // then
            assertThat(result).isNotNull();
            assertThat(result.isScrap()).isTrue();
        }
    }

    @Nested
    @DisplayName("[saveTraining] 훈련과정 공고 저장 실패 테스트")
    class SaveTrainingFail{

        @Test
        @DisplayName("회원정보가 존재하지 않는 경우")
        void saveTraining_nonMember(){
            // given
            given(memberRepository.findById(TEST_ID)).willReturn(Optional.empty());

            // when & then
            assertThatThrownBy(() -> trainingScrapService.saveTraining(TEST_SAVE_REQEUST, BOOTCAMP))
                    .isInstanceOf(DomainException.class)
                    .hasMessageContaining("회원 정보를 찾을 수 없습니다");
        }

        @Test
        @DisplayName("저장된 훈련 과정이 50개가 넘는 경우")
        void saveTraining_exceedScrap(){
            // given
            given(memberRepository.findById(TEST_ID)).willReturn(Optional.of(mockMember));
            given(memberTrainingScrapRepository.countByMemberId(TEST_ID)).willReturn(51);

            // when & then
            assertThatThrownBy(() -> trainingScrapService.saveTraining(TEST_SAVE_REQEUST, BOOTCAMP))
                    .isInstanceOf(DomainException.class)
                    .hasMessageContaining("저장 공고는 50개를 넘을 수 없습니다.");
        }

        @Test
        @DisplayName("중복으로 저장하는 경우")
        void saveTraining_duplicated(){
            // given
            given(memberRepository.findById(TEST_ID)).willReturn(Optional.of(mockMember));
            given(memberTrainingScrapRepository.countByMemberId(TEST_ID)).willReturn(1);
            given(memberTrainingScrapRepository.existsByTrainingIdAndMemberId(TEST_TRPR_ID, TEST_ID)).willReturn(true);

            // when & then
            assertThatThrownBy(() -> trainingScrapService.saveTraining(TEST_SAVE_REQEUST, BOOTCAMP))
                    .isInstanceOf(DomainException.class)
                    .hasMessageContaining("이미 저장된 공고입니다.");
        }
    }
}
