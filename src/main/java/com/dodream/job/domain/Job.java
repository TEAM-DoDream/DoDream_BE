package com.dodream.job.domain;

import com.dodream.core.infrastructure.jpa.entity.BaseLongIdEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@SuperBuilder
@Table(name = "job")
public class Job extends BaseLongIdEntity {

    @Column(nullable = false, name = "job_name")
    private String jobName;

    @Column(nullable = false, name = "requires_certification")
    @Enumerated(EnumType.STRING)
    private Require requiresCertification;

    @Column(nullable = false, name = "work_time_slot")
    @Enumerated(EnumType.STRING)
    private WorkTime workTimeSlot;

    @Column(nullable = false, name = "salary_type")
    @Enumerated(EnumType.STRING)
    private SalaryType salaryType;

    @Column(nullable = false, name = "salary_cost")
    private int salaryCost;

    @Column(nullable = false, name = "interpersonal_contact_level")
    @Enumerated(EnumType.STRING)
    private Level interpersonalContactLevel;

    @Column(nullable = false, name = "physical_activity_level")
    @Enumerated(EnumType.STRING)
    private PhysicalActivity physicalActivityLevel;

    @Column(nullable = false, name = "emotional_labor_level")
    @Enumerated(EnumType.STRING)
    private Level emotionalLaborLevel;

    @Column(name = "job_image_url")
    private String jobImageUrl;

    @Column(nullable = false, name = "ncs_name")
    private String ncsName;

    @OneToMany(mappedBy = "job", cascade = CascadeType.ALL, orphanRemoval = true)
    List<Certification> certifications = new ArrayList<>();


    public List<String> getCertificationNames(List<Certification> certificationList) {
        return certificationList.stream()
                .map(Certification::getCertificationName)
                .toList();
    }

    public List<String> getCertificationPeriods(List<Certification> certificationList) {
        return certificationList.stream()
                .map(Certification::getCertificationPreparationPeriod)
                .toList();
    }
}
