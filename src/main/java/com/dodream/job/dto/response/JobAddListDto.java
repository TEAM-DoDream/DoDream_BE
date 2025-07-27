package com.dodream.job.dto.response;

import com.dodream.job.domain.Job;
import com.dodream.job.infrastructure.JobImageUrlGenerator;
import io.swagger.v3.oas.annotations.media.Schema;

public record JobAddListDto(
    @Schema(name = "직업 DB 식별자", example = "1")
    Long jobId,

    @Schema(name = "직업 이름", example = "요양보호사")
    String jobName,

    @Schema(name = "직업 설명", example = "요양보호사는 ~")
    String jobDescription,

    @Schema(name = "직업 이미지 url", example = "직업 이미지 url")
    String imageUrl
)
{

    public static JobAddListDto from(Job job, JobImageUrlGenerator jobImageUrlGenerator){
        return new JobAddListDto(
            job.getId(),
            job.getJobName(),
            job.getJobSummary(),
            jobImageUrlGenerator.getImageUrl(job.getId())
        );
    }

}
