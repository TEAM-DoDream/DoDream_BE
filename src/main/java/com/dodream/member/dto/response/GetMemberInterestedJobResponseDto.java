package com.dodream.member.dto.response;

import com.dodream.job.domain.Job;
import io.swagger.v3.oas.annotations.media.Schema;

public record GetMemberInterestedJobResponseDto(
    @Schema(description = "직업 id", example = "12")
    Long jobId,
    @Schema(description = "직업명", example = "요양보호사")
    String jobName

) {

    public static GetMemberInterestedJobResponseDto from(Job job) {
        return new GetMemberInterestedJobResponseDto(job.getId(), job.getJobName());
    }

}
