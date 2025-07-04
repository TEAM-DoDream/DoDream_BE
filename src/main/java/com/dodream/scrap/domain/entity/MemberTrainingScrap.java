package com.dodream.scrap.domain.entity;

import com.dodream.core.infrastructure.jpa.entity.BaseLongIdEntity;
import com.dodream.member.domain.Member;
import com.dodream.scrap.dto.request.TrainingSaveReqeustDto;
import com.dodream.training.dto.response.TrainingDetailApiResponse;
import com.dodream.scrap.utils.TrainingAddressUtils;
import com.dodream.training.util.TrainingUrlUtils;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@SuperBuilder
@Table(
        name = "member_training_scrap",
        indexes = {
                // asc 정렬만 커버되기 때문에 배포 DB에는 desc 정렬에 대해서 sql 입력
                @Index(name = "idx_member_id_created_at_asc", columnList = "member_id, created_at")
        }
)
public class MemberTrainingScrap extends BaseLongIdEntity {

    @Column(name = "training_id", nullable = false)
    private String trainingId;

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
    private LocalDate trainingStartDate;

    // 종료 날짜
    @Column(name = "training_end_date", nullable = false)
    private LocalDate trainingEndDate;

    // 회차
    @Column(name = "training_degree", nullable = false)
    private String trainingDegree;

    // 훈련 비용
    @Column(name = "training_manage", nullable = false)
    private int trainingManage;

    @Column(name = "training_url", nullable = false)
    private String trainingUrl;

    // 멤버 연관
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;

    public static MemberTrainingScrap of(
            Member member, TrainingSaveReqeustDto request, TrainingDetailApiResponse response
    ) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd");

        return MemberTrainingScrap.builder()
                .trainingId(request.trprId())
                .trainingName(response.instBaseInfo().trprNm())
                .trainingOrgName(response.instBaseInfo().inoNm())
                .trainingOrgAddr(TrainingAddressUtils.extractMainRegion(response.instBaseInfo().addr()))
                .trainingStartDate(LocalDate.parse(request.traStartDate(), formatter))
                .trainingEndDate(LocalDate.parse(request.traEndDate(), formatter))
                .trainingDegree(String.valueOf(response.instBaseInfo().trprDegr()))
                .trainingManage(getIntegerPrice(response.instDetailInfo().totalCrsAt()))
                .trainingUrl(getTrainingUrl(request, response.instBaseInfo().hpAddr()))
                .member(member)
                .build();
    }

    private static int getIntegerPrice(String price){
        try{
            return Integer.parseInt(price);
        }catch (NumberFormatException e){
            return 0;
        }
    }

    private static String getTrainingUrl(TrainingSaveReqeustDto request, String hpAddr){
        if(hpAddr == null || hpAddr.isEmpty()){
            return TrainingUrlUtils.generateTrainingUrl(request.trprId(), request.trprDegr(), request.trainstCstId());
        }

        return hpAddr;
    }
}
