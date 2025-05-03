package com.dodream.job.dto.response;

import com.dodream.job.domain.Job;
import io.swagger.v3.oas.annotations.media.Schema;

import java.util.List;

public record JobListDto(

        @Schema(name = "직업 DB 식별자", example = "1")
        Long jobId,

        @Schema(name = "직업 이름", example = "요양보호사")
        String jobName,

        @Schema(name = "직업 설명", example = "요양보호사는 ~")
        String jobDescription,

        @Schema(name = "자격증 필요 정보", example = "필요")
        String requiredCertification,

        @Schema(name = "근무 시간대", example = "평일 오전 9시- 18시")
        String workTimeInfo,

        @Schema(name = "신체활동 정보", example = "움직임이 많은 활동")
        String physicalInfo,
        
        @Schema(name = "직업 이미지 url", example = "직업 이미지 url")
        String imageUrl
) {

        public static JobListDto from(Job job){
                return new JobListDto(
                        job.getId(),
                        job.getJobName(),
                        // TODO: 직업 상세 추후 추가
                        "직업 상세는 추후 추가",
                        job.getRequiresCertification().getDescription(),
                        job.getWorkTimeSlot().getDescription(),
                        job.getPhysicalActivityLevel().getDescription(),
                        // TODO: 직업 이미지 추가 후 추가
                        "직업 이미지 url은 추후 추가"
                );
        }
}
