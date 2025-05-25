package com.dodream.job.repository;

import com.dodream.job.domain.*;
import com.dodream.member.domain.Gender;
import com.dodream.member.domain.Member;
import com.dodream.member.domain.State;
import com.dodream.region.domain.Region;
import com.dodream.todo.domain.TodoGroup;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDate;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;

@DataJpaTest
@ActiveProfiles("test")
@DisplayName("[JobRepository] JPA 단위 테스트")
public class JobRepositoryTest {

    @Autowired
    private JobRepository jobRepository;

    @Autowired
    private TestEntityManager testEntityManager;

    private Job job1;
    private Job job2;
    private Member member;
    private Region region;
    
    @BeforeEach
    void setUp() {
        // 더미 지역
        region = Region.builder()
                .regionCode("11110")
                .regionName("서울 종로구")
                .build();
        testEntityManager.persist(region);

        member = Member.builder()
                .loginId("testuser")
                .password("password")
                .nickName("테스트유저")
                .birthDate(LocalDate.of(2002, 1, 1))
                .gender(Gender.MALE)
                .region(region)
                .state(State.ACTIVE)
                .build();
        testEntityManager.persist(member);

        job1 = Job.builder()
                .jobName("프론트엔드 개발자")
                .requiresCertification(Require.NONE)
                .workTimeSlot(WorkTime.WEEKDAY_NINE_TO_SIX)
                .salaryType(SalaryType.MONTHLY)
                .salaryCost(3500000)
                .interpersonalContactLevel(Level.LOW)
                .physicalActivityLevel(PhysicalActivity.LOW)
                .emotionalLaborLevel(Level.LOW)
                .jobImageUrl("https://example.com/fed.jpg")
                .ncsName("정보기술개발")
                .jobSummary("프론트 개발")
                .build();

        job2 = Job.builder()
                .jobName("백엔드 개발자")
                .requiresCertification(Require.OPTIONAL)
                .workTimeSlot(WorkTime.WEEKDAY_NINE_TO_SIX)
                .salaryType(SalaryType.MONTHLY)
                .salaryCost(3700000)
                .interpersonalContactLevel(Level.MEDIUM)
                .physicalActivityLevel(PhysicalActivity.MEDIUM)
                .emotionalLaborLevel(Level.LOW)
                .jobImageUrl("https://example.com/bed.jpg")
                .ncsName("정보기술개발")
                .jobSummary("백엔드 개발")
                .build();

        testEntityManager.persist(job1);
        testEntityManager.persist(job2);

        testEntityManager.persist(TodoGroup.builder().job(job1).member(member).build());
        testEntityManager.persist(TodoGroup.builder().job(job1).member(member).build());
        testEntityManager.persist(TodoGroup.builder().job(job1).member(member).build());
        testEntityManager.persist(TodoGroup.builder().job(job2).member(member).build());
    }

    @Test
    @DisplayName("findMostPopularJob()은 TodoGroup 기준 가장 많이 연결된 Job을 반환한다.")
    void testFindMostPopularJob() {
        Optional<Job> popular = jobRepository.findMostPopularJob();

        assertThat(popular).isPresent();
        assertThat(popular.get().getJobName()).isEqualTo("프론트엔드 개발자");
    }
}
