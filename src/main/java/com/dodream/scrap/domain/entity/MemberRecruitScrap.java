package com.dodream.scrap.domain.entity;

import com.dodream.core.infrastructure.jpa.entity.BaseLongIdEntity;
import com.dodream.member.domain.Member;
import com.dodream.recruit.dto.response.RecruitResponseListApiDto;
import com.dodream.scrap.domain.value.RecruitCloseType;
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
@Table(
        name = "member_recruit_scrap",
        indexes = {
                @Index(name = "idx_recruit_id_member_id", columnList = "recruit_id, member_id")
        }
)
public class MemberRecruitScrap extends BaseLongIdEntity {

    @Column(name = "recruit_id", nullable = false)
    private String recruitId;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "company_name", nullable = false)
    private String companyName;

    @Column(name = "expiration_date", nullable = false)
    private String expirationDate;

    @Column(name = "location_name", nullable = false)
    private String locationName;

    @Column(name = "job_type", nullable = false)
    private String jobType;

    @Column(name = "experience_level", nullable = false)
    private String experienceLevel;

    @Column(name = "education_level", nullable = false)
    private String educationLevel;

    @Column(name = "close_type", nullable = false)
    @Enumerated(EnumType.STRING)
    private RecruitCloseType closeType;

    @Column(name = "recruit_url", nullable = false)
    private String recruitUrl;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;

    public static MemberRecruitScrap of(String recruitId, RecruitResponseListApiDto.Jobs.Job job, Member member) {
        return MemberRecruitScrap.builder()
                .recruitId(recruitId)
                .title(job.position().title())
                .companyName(job.company().detail().name())
                .expirationDate(job.expirationDate())
                .locationName(locationNameFormatter(job.position().location().name()))
                .jobType(job.position().jobType().name())
                .experienceLevel(job.position().experienceLevel().name())
                .educationLevel(job.position().requiredEducationLevel().name())
                .closeType(closeTypeFormatter(job.closeType().code()))
                .recruitUrl(job.url())
                .member(member)
                .build();
    }

    private static String locationNameFormatter(String locName){
        if(locName == null || locName.isEmpty()) return null;
        return locName.replace("&gt;","").replaceAll("\\s+", " ");
    }

    private static RecruitCloseType closeTypeFormatter(String code){
        if(code == null || code.isEmpty()) return null;

        for (RecruitCloseType type : RecruitCloseType.values()) {
            if (type.getCode().equals(code)) {
                return type;
            }
        }

        return null;
    }
}
