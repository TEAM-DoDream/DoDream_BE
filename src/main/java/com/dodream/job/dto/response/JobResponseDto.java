package com.dodream.job.dto.response;

import com.dodream.job.domain.Job;
import io.swagger.v3.oas.annotations.media.Schema;

import java.util.List;

public record JobResponseDto(
        @Schema(description = "직업 id", example = "1")
        Long jobId,

        @Schema(description="직업 이름", example = "요양보호사")
        String jobName,

        @Schema(description = "직업 소개", example = "요양보호사는 ~")
        String jobDescription,

        @Schema(description = "자격증", example = "요양보호사 자격증")
        List<String> certification,

        @Schema(description = "자격증 준비 기간", example = "약 1개월")
        List<String> certificationPeriod,

        @Schema(description = "급여 태그", example = "월급")
        String salaryType,

        @Schema(description = "급여 금액", example = "약 220만원")
        String salaryCost,

        @Schema(description="강도")
        Strong strong
) {

    public record Strong(

            @Schema(description = "신체활동 정도", example = "움직임이 많은 활동")
            String physical,

            @Schema(description = "정신적 스트레스", example = "높음")
            String stress,

            @Schema(description = "대인관계 빈도", example = "높음")
            String relationship
    ) {}

    public static JobResponseDto from(Job job){
        return new JobResponseDto(
                job.getId(),
                job.getJobName(),
                job.getJobSummary(),
                job.getCertificationNames(job.getCertifications()),
                job.getCertificationPeriods(job.getCertifications()),
                job.getSalaryType().getDescription(),
                String.valueOf(job.getSalaryCost()) + "만원",
                new Strong(
                        job.getPhysicalActivityLevel().getDescription(),
                        job.getEmotionalLaborLevel().getDescription(),
                        job.getInterpersonalContactLevel().getDescription()
                )
        );
    }
}
