package com.dodream.recruit.domain;

import com.dodream.core.infrastructure.jpa.entity.BaseLongIdEntity;
import com.dodream.member.domain.Member;
import com.dodream.recruit.dto.response.RecruitResponseListApiDto;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@SuperBuilder
@Table(name = "member_recruit_scrap")
public class MemberRecruitScrap extends BaseLongIdEntity {

    @Column(name = "recruit_id", nullable = false)
    String recruitId;

    @Column(name = "title", nullable = false)
    String title;

    @Column(name = "company_name", nullable = false)
    String companyName;

    @Column(name = "expiration_date", nullable = false)
    String expirationDate;

    @Column(name = "location_name", nullable = false)
    String locationName;

    @Column(name = "job_type", nullable = false)
    String jobType;

    @Column(name = "experience_level", nullable = false)
    String experienceLevel;

    @Column(name = "education_level", nullable = false)
    String educationLevel;

    @Column(name = "close_type", nullable = false)
    String closeType;

    @Column(name = "recruit_url", nullable = false)
    String recruitUrl;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "member_id", nullable = false)
    Member member;

    public static MemberRecruitScrap of(String recruitId, RecruitResponseListApiDto.Jobs.Job job, Member member) {
        return MemberRecruitScrap.builder()
                .recruitId(recruitId)
                .title(job.position().title())
                .companyName(job.company().detail().name())
                .expirationDate(job.expirationTimestamp())
                .locationName(job.position().location().name())
                .jobType(job.position().jobType().name())
                .experienceLevel(job.position().experienceLevel().name())
                .educationLevel(job.position().requiredEducationLevel().name())
                .closeType(job.closeType().name())
                .recruitUrl(job.url())
                .member(member)
                .build();
    }
}
