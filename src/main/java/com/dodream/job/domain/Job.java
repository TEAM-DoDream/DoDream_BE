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

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@SuperBuilder
@Table(name = "job")
public class Job extends BaseLongIdEntity {

    @Column(nullable = false, name = "job_name")
    private String jobName;

    @OneToMany(mappedBy = "job", cascade = CascadeType.ALL, orphanRemoval = true)
    List<Certification> certifications = new ArrayList<>();

    @Column(nullable = false, name = "requires_certification")
    private boolean requiresCertification;

    @Column(nullable = false, name = "work_time_slot")
    private String workTimeSlot;

    @Column(nullable = false, name = "salay_type")
    private String salayType;

    @Column(nullable = false, name = "salary_cost")
    private int salaryCost;

    @Column(nullable = false, name = "interpersonal_contact_level")
    private Level interpersonalContactLevel;

    @Column(nullable = false, name = "physical_activity_level")
    private Level physicalActivityLevel;

    @Column(nullable = false, name = "emotional_labor_level")
    private Level emotionalLaborLevel;

    @Column(name = "job_image_url")
    private String jobImageUrl;

    @Column(nullable = false, name = "ncs_name")
    private String ncsName;
}
