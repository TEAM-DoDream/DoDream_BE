package com.dodream.training.domain;

import com.dodream.core.infrastructure.jpa.entity.BaseLongIdEntity;
import com.dodream.member.domain.Member;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.Fetch;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@SuperBuilder
@Table(name = "member_training_scrap")
public class MemberTrainingScrap extends BaseLongIdEntity {

    @Column(name = "training_id", nullable = false)
    private String trainingId;

    // 훈련 종류
    @Column(name = "training_type", nullable = false)
    @Enumerated(EnumType.STRING)
    private TrainingType trainingType;

    // 훈련 이름
    @Column(name = "training_name", nullable = false)
    private String trainingName;

    // 훈련 기관 이름
    @Column(name = "training_org_name", nullable = false)
    private String trainingOrgName;

    // 훈련 기관 주소
    @Column(name = "training_org_addr", nullable = false)
    private String trainingOrgAddr;

    // 시작 날짜
    @Column(name = "training_start_date", nullable = false)
    private String trainingStartDate;

    // 종료 날짜
    @Column(name = "training_end_date", nullable = false)
    private String trainingEndDate;

    // 회차
    @Column(name = "training_degree", nullable = false)
    private String trainingDegree;

    // 훈련 비용
    @Column(name = "training_manage", nullable = false)
    private String trainingManage;

    // 멤버 연관
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;
}
