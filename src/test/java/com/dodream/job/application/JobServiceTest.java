package com.dodream.job.application;

import com.dodream.core.exception.DomainException;
import com.dodream.job.domain.*;
import com.dodream.job.dto.response.JobListDto;
import com.dodream.job.dto.response.JobResponseDto;
import com.dodream.job.infrastructure.JobImageUrlGenerator;
import com.dodream.job.repository.JobRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.BDDMockito.*;
import static org.assertj.core.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class JobServiceTest {

    @Mock
    private JobRepository jobRepository;

    @Mock
    private JobImageUrlGenerator jobImageUrlGenerator;

    @InjectMocks
    private JobService jobService;

    private static final Long TEST_ID = 1L;
    private static final int TEST_PAGE_NUMBER = 0;

    Pageable pageable;
    Job job1, job2;
    Certification certification1, certification2;

    @BeforeEach
    void setUp() {
        ReflectionTestUtils.setField(jobImageUrlGenerator, "endpoint", "http://dummy-endpoint");
        ReflectionTestUtils.setField(jobImageUrlGenerator, "bucketName", "dummy-bucket");

        job1 = Job.builder()
                .jobName("요양 보호사")
                .requiresCertification(Require.REQUIRED)
                .workTimeSlot(WorkTime.FLEXIBLE)
                .salaryType(SalaryType.MONTHLY)
                .salaryCost(2500000)
                .interpersonalContactLevel(Level.MEDIUM)
                .physicalActivityLevel(PhysicalActivity.HIGH)
                .emotionalLaborLevel(Level.HIGH)
                .jobImageUrl("https://example.com/image.jpg")
                .ncsName("노인복지서비스")
                .certifications(new ArrayList<>())
                .build();

        job2 = Job.builder()
                .jobName("간병인")
                .requiresCertification(Require.REQUIRED)
                .workTimeSlot(WorkTime.EVENT)
                .salaryType(SalaryType.MONTHLY)
                .salaryCost(10000)
                .interpersonalContactLevel(Level.LOW)
                .physicalActivityLevel(PhysicalActivity.MEDIUM)
                .emotionalLaborLevel(Level.MEDIUM)
                .jobImageUrl("https://example.com/image2.jpg")
                .ncsName("건강관리서비스")
                .certifications(new ArrayList<>())
                .build();

        certification1 = Certification.builder()
                .certificationName("요양보호사 자격증")
                .certificationPreparationPeriod("3개월")
                .job(job1)
                .build();

        certification2 = Certification.builder()
                .certificationName("심리상담사 자격증")
                .certificationPreparationPeriod("6개월")
                .job(job2)
                .build();

        job1.getCertifications().add(certification1);
        job2.getCertifications().add(certification2);

        pageable = PageRequest.of(TEST_PAGE_NUMBER, 9);
    }

    @Nested
    @DisplayName("[findById] 직업 id로 찾기 테스트")
    class FindById {

        @Test
        @DisplayName("id로 찾기 성공 테스트")
        void findById_success(){
            // given
            when(jobRepository.findById(TEST_ID)).thenReturn(Optional.of(job1));

            // when
            JobResponseDto result = jobService.getJobById(TEST_ID);

            // then
            assertThat(result.jobName()).isEqualTo("요양 보호사");
            assertThat(result.salaryCost()).isEqualTo("2500000만원");
        }

        @Test
        @DisplayName("id로 찾기 실패 테스트")
        void findById_fail(){
            // given
            when(jobRepository.findById(TEST_ID)).thenReturn(Optional.empty());

            // when & then
            assertThatThrownBy(() -> jobService.getJobById(TEST_ID))
                    .isInstanceOf(DomainException.class)
                    .hasMessageContaining("직업 데이터를 찾을 수 없습니다.");
        }
    }

    @Nested
    @DisplayName("[getAllJobs] 직업 리스트 반환 테스트")
    class GetAllJobs {

        @Test
        @DisplayName("직업 리스트 반환 성공")
        void getAllJobs_success(){
            // given
            Page<Job> jobPage = new PageImpl<>(List.of(job1, job2));
            Pageable pageable = PageRequest.of(0, 9);

            given(jobRepository.findAll(any(Specification.class), eq(pageable))).willReturn(jobPage);
            given(jobImageUrlGenerator.getImageUrl(job1.getId())).willReturn("http://dummy-endpoint/dummy-bucket/job/" + job1.getId() + ".png");

            // when
            Page<JobListDto> result = jobService.getAllJobs(0, null, null, null);

            // then
            assertThat(result.getContent()).hasSize(2);
            JobListDto dto = result.getContent().get(0);
            assertThat(dto.jobName()).isEqualTo("요양 보호사");
        }
    }
}
