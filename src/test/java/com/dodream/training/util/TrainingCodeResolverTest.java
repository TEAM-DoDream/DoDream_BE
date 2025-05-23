package com.dodream.training.util;

import com.dodream.core.exception.DomainException;
import com.dodream.job.domain.Job;
import com.dodream.job.repository.JobRepository;
import com.dodream.ncs.domain.Ncs;
import com.dodream.ncs.repository.NcsRepository;
import com.dodream.region.domain.Region;
import com.dodream.region.exception.RegionErrorCode;
import com.dodream.region.repository.RegionRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.mockito.BDDMockito.*;
import static org.assertj.core.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class TrainingCodeResolverTest {

    @Mock
    RegionRepository regionRepository;

    @Mock
    JobRepository jobRepository;

    @Mock
    NcsRepository ncsRepository;

    @InjectMocks
    TrainingCodeResolver trainingCodeResolver;

    private final String REGION_NAME_SUCCESS = "경기 안양시 만안구";
    private final String REGION_NAME_FAIL = "로스앤젤레스 애너하임";

    private final String REGION_CODE = "41170";

    private final String JOB_NAME = "요양 보호사";
    private final String NCS_NAME = "요양";
    private final String NCS_CODE = "02010102";

    @Nested
    @DisplayName("[resolveRegionCode] - 단위 테스트")
    class RegionCodeTest{

        @Test
        @DisplayName("지역 코드 정상 반환")
        void resolveRegionCode_success(){
            // given
            Region region = Region.builder()
                    .regionCode(REGION_CODE)
                    .regionName(REGION_NAME_SUCCESS)
                    .build();

            when(regionRepository.findByRegionName(REGION_NAME_SUCCESS))
                    .thenReturn(Optional.of(region));

            // when
            String regionCode = trainingCodeResolver.resolveRegionCode(REGION_NAME_SUCCESS);

            // then
            assertThat(regionCode).isEqualTo(REGION_CODE);
        }

        @Test
        @DisplayName("잘못된 지역은 오류 반환")
        void resolveRegionCode_wrong_region(){
            // given
            when(regionRepository.findByRegionName(REGION_NAME_FAIL))
                    .thenReturn(Optional.empty());

            // when & then
            assertThatThrownBy(() -> trainingCodeResolver.resolveRegionCode(REGION_NAME_FAIL))
                    .isInstanceOf(DomainException.class)
                    .hasMessageContaining("지역 정보를 찾을 수 없습니다.");
        }

        @Test
        @DisplayName("빈 문자열은 지역 null 반환")
        void resolveRegionCode_empty(){
            // when
            String result = trainingCodeResolver.resolveRegionCode("");

            // then
            assertThat(result).isNull();
        }

        @Test
        @DisplayName("null입력시 지역 null 반환 ")
        void resolveRegionCode_null(){
            // when
            String result = trainingCodeResolver.resolveRegionCode(null);

            // then
            assertThat(result).isNull();
        }
    }

    @Nested
    @DisplayName("[resolveNcsCode] - 단위 테스트")
    class NcsCodeTest{

        @Test
        @DisplayName("ncs 코드 정상 반환 테스트")
        void resolveNcsCode_success(){
            // given
            Job job = mock(Job.class);
            Ncs ncs = mock(Ncs.class);

            when(job.getJobName()).thenReturn(JOB_NAME);
            when(job.getNcsName()).thenReturn(NCS_NAME);
            when(ncs.getNcsCode()).thenReturn(NCS_CODE);

            when(jobRepository.findByJobName(job.getJobName()))
                    .thenReturn(Optional.of(job));
            when(ncsRepository.findByNcsName(job.getNcsName()))
                    .thenReturn(Optional.of(ncs));

            // when
            String result = trainingCodeResolver.resolveNcsCode(job.getJobName());

            // then
            assertThat(result).isEqualTo(NCS_CODE);
        }

        @Test
        @DisplayName("직업 이름이 잘못된 경우")
        void resolveNcsCode_wrong_job(){
            // given
            Job job = mock(Job.class);

            when(job.getJobName()).thenReturn(JOB_NAME);

            when(jobRepository.findByJobName(job.getJobName()))
                    .thenReturn(Optional.empty());

            // when & then
            assertThatThrownBy(() -> trainingCodeResolver.resolveNcsCode(job.getJobName()))
                    .isInstanceOf(DomainException.class)
                    .hasMessageContaining("직업 데이터를 찾을 수 없습니다.");
        }

        @Test
        @DisplayName("ncs 정보를 찾을 수 없는 경우")
        void resolveNcsCode_wrong_ncs(){
            // given
            Job job = mock(Job.class);
            Ncs ncs = mock(Ncs.class);

            when(job.getJobName()).thenReturn(JOB_NAME);
            when(jobRepository.findByJobName(job.getJobName()))
                    .thenReturn(Optional.of(job));

            when(ncsRepository.findByNcsName(job.getNcsName()))
                    .thenReturn(Optional.empty());

            // when & then
            assertThatThrownBy(() -> trainingCodeResolver.resolveNcsCode(job.getJobName()))
                    .isInstanceOf(DomainException.class)
                    .hasMessageContaining("NCS 데이터를 찾을 수 없습니다.");
        }

        @Test
        @DisplayName("직업 이름이 빈 문자열인 경우 null 반환")
        void resolveNcsCode_empty_string(){
            // when
            String result = trainingCodeResolver.resolveNcsCode("");

            // then
            assertThat(result).isNull();
        }

        @Test
        @DisplayName("직업 이름이 null인 경우 null 반환")
        void resolveNcsCode_null(){
            // when
            String result = trainingCodeResolver.resolveNcsCode(null);

            // then
            assertThat(result).isNull();
        }
    }
}
