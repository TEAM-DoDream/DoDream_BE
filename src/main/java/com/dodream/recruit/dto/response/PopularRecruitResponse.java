package com.dodream.recruit.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;

public record PopularRecruitResponse(

        @JsonProperty("job-name")
        @Schema(description = "인기 직업 이름", example = "요양 보호사")
        String jobName,

        @JsonProperty("count")
        @Schema(description = "현재 공고 건수", example = "23")
        int count
) {}
