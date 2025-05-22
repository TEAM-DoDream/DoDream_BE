//package com.dodream.job.application;
//
//import com.dodream.core.exception.DomainException;
//import com.dodream.job.domain.*;
//import com.dodream.job.dto.response.JobListDto;
//import com.dodream.job.dto.response.JobResponseDto;
//import com.dodream.job.repository.JobRepository;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.DisplayName;
//import org.junit.jupiter.api.Nested;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.junit.jupiter.MockitoExtension;
//import org.springframework.data.domain.Page;
//import org.springframework.data.domain.PageImpl;
//import org.springframework.data.domain.PageRequest;
//import org.springframework.data.domain.Pageable;
//
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Optional;
//
//import static org.mockito.BDDMockito.*;
//import static org.assertj.core.api.Assertions.*;
//
//@ExtendWith(MockitoExtension.class)
//public class JobServiceTest {
//
//    @Mock
//    private JobRepository jobRepository;
//
//    @InjectMocks
//    private JobService jobService;
//
//    private static final Long TEST_ID = 1L;
//    private static final int TEST_PAGE_NUMBER = 0;
//
//    Pageable pageable;
//    Job job1, job2;
//    Certification certification1, certification2;
//
//    @BeforeEach
//    void setUp() {
//        job1 = Job.builder()
//                .jobName("요양 보호사")
//                .requiresCertification(Require.REQUIRED)
//                .workTimeSlot(WorkTime.FLEXIBLE)
//                .salaryType(SalaryType.MONTHLY)
//                .salaryCost(2500000)
//                .interpersonalContactLevel(Level.MEDIUM)
//                .physicalActivityLevel(PhysicalActivity.HIGH)
//                .emotionalLaborLevel(Level.HIGH)
//                .jobImageUrl("https://example.com/image.jpg")
//                .ncsName("노인복지서비스")
//                .certifications(new ArrayList<>())
//                .build();
//
//        job2 = Job.builder()
//                .jobName("간병인")
//                .requiresCertification(Require.REQUIRED)
//                .workTimeSlot(WorkTime.EVENT)
//                .salaryType(SalaryType.MONTHLY)
//                .salaryCost(10000)
//                .interpersonalContactLevel(Level.LOW)
//                .physicalActivityLevel(PhysicalActivity.MEDIUM)
//                .emotionalLaborLevel(Level.MEDIUM)
//                .jobImageUrl("https://example.com/image2.jpg")
//                .ncsName("건강관리서비스")
//                .certifications(new ArrayList<>())
//                .build();
//
//        certification1 = Certification.builder()
//                .certificationName("요양보호사 자격증")
//                .certificationPreparationPeriod("3개월")
//                .job(job1)
//                .build();
//
//        certification2 = Certification.builder()
//                .certificationName("심리상담사 자격증")
//                .certificationPreparationPeriod("6개월")
//                .job(job2)
//                .build();
//
//        job1.getCertifications().add(certification1);
//        job2.getCertifications().add(certification2);
//
//        pageable = PageRequest.of(TEST_PAGE_NUMBER, 9);
//    }
//
//    @Nested
//    @DisplayName("[JobServiceTest] - id로 직업 상세보기 반환")
//    class JobServiceTestById{
//
//        @Test
//        @DisplayName("id로 직업 상세보기 찾기 - 성공")
//        void getJobById_success(){
//            // given
//            given(jobRepository.findById(TEST_ID)).willReturn(Optional.of(job1));
//
//            // when
//            JobResponseDto result = jobService.getJobById(TEST_ID);
//
//            // then
//            assertThat(result.jobName()).isEqualTo("요양 보호사");
//            assertThat(result.salaryType()).isEqualTo("월급");
//            assertThat(result.certification()).contains("요양보호사 자격증");
//        }
//
//        @Test
//        @DisplayName("id로 직업 상세보기 찾기 - 실패")
//        void getJobById_fail(){
//            // given
//            given(jobRepository.findById(TEST_ID)).willReturn(Optional.empty());
//
//            // when & then
//            assertThatThrownBy(() -> jobService.getJobById(TEST_ID))
//                    .isInstanceOf(DomainException.class)
//                    .hasMessage("직업 데이터를 찾을 수 없습니다.");
//        }
//    }
//
//    @Nested
//    @DisplayName("[JobServiceTest] - 직업 전체 리스트 반환")
//    class JobServiceTestAllList{
//
//        @Test
//        @DisplayName("전체 리스트 반환 - 성공")
//        void getAllJobs_success(){
//            // given
//            Page<Job> jobPage = new PageImpl<>(List.of(job1, job2), pageable, 2);
//
//            given(jobRepository.findAll(pageable)).willReturn(jobPage);
//
//            // when
//            Page<JobListDto> result = jobService.getAllJobs(TEST_PAGE_NUMBER, null, null, null);
//
//            // then
//            assertThat(result).hasSize(2);
//            assertThat(result.getContent().get(0).jobName()).isEqualTo("요양 보호사");
//            assertThat(result.getContent().get(1).jobName()).isEqualTo("간병인");
//        }
//
//        @Test
//        @DisplayName("빈 직업 리스트 반환 테스트")
//        void getAllJobs_emptyList(){
//            // given
//            Page<Job> emptyPage = new PageImpl<>(List.of(), pageable, 0);
//            given(jobRepository.findAll(pageable)).willReturn(emptyPage);
//
//            // when
//            Page<JobListDto> result = jobService.getAllJobs(TEST_PAGE_NUMBER, null, null, null);
//
//            // then
//            assertThat(result).isEmpty();
//            then(jobRepository).should(times(1)).findAll(any(Pageable.class));
//        }
//    }
//}
