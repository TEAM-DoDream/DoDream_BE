package com.dodream.job.dto.response;

import com.dodream.job.domain.Job;
import com.dodream.job.infrastructure.JobImageUrlGenerator;
import io.swagger.v3.oas.annotations.media.Schema;

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

        public static JobListDto from(Job job, JobImageUrlGenerator jobImageUrlGenerator){
                return new JobListDto(
                        job.getId(),
                        job.getJobName(),
                        job.getJobSummary(),
                        job.getRequiresCertification().getDescription(),
                        job.getWorkTimeSlot().getDescription(),
                        job.getPhysicalActivityLevel().getDescription(),
                        jobImageUrlGenerator.getImageUrl(job.getId())
                );
        }
}
