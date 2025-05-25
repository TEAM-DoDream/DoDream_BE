package com.dodream.job.repository;

import com.dodream.job.domain.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

import static org.assertj.core.api.Assertions.*;


@ActiveProfiles("test")
@DataJpaTest
@DisplayName("[CertificationRepository] JPA 단위 테스트")
public class CertificationRepositoryTest {

    @Autowired
    private CertificationRepository certificationRepository;

    @Autowired
    private TestEntityManager testEntityManager;

    private Certification certification;

    @BeforeEach
    void setUp() {
        Job job = Job.builder()
                .jobName("프론트엔드 개발자")
                .requiresCertification(Require.OPTIONAL)
                .workTimeSlot(WorkTime.WEEKDAY_NINE_TO_SIX)
                .salaryType(SalaryType.MONTHLY)
                .salaryCost(3000000)
                .interpersonalContactLevel(Level.MEDIUM)
                .physicalActivityLevel(PhysicalActivity.LOW)
                .emotionalLaborLevel(Level.LOW)
                .jobImageUrl("https://example.com/image.jpg")
                .ncsName("정보기술개발")
                .jobSummary("웹 프론트엔드 UI/UX 개발을 담당합니다.")
                .build();

        testEntityManager.persist(job);

        certification = Certification.builder()
                .certificationName("프론트엔드 자격증")
                .certificationPreparationPeriod("약 6개월")
                .job(job)
                .build();

        certificationRepository.save(certification);
    }

    @Test
    @DisplayName("findAll Test")
    void saveAndFindCertification() {
        // when
        List<Certification> certificationList = certificationRepository.findAll();

        // then
        assertThat(certificationList).isNotEmpty();
        assertThat(certificationList.get(0).getCertificationName()).isEqualTo("프론트엔드 자격증");
    }
}
