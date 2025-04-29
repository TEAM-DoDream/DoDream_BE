package com.dodream.recruit.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public record RecruitResponseListDto(
        @JsonProperty("count")
        int count,

        @JsonProperty("start")
        int start,

        @JsonProperty("total")
        String total,

        @JsonProperty("job")
        List<Job> job
){
    public record Job(
            @JsonProperty("url")
            String url,

            @JsonProperty("active")
            int active,

            @JsonProperty("title")
            String title,

            @JsonProperty("locationName")
            String locationName,

            @JsonProperty("jobTypeName")
            String jobTypeName,

            @JsonProperty("jobCode")
            String jobCode,

            @JsonProperty("experienceLevel")
            String experienceLevel,

            @JsonProperty("requiredEducationLevel")
            String requiredEducationLevel,

            @JsonProperty("closeType")
            String closeType,

            @JsonProperty("keyword")
            String keyword,

            @JsonProperty("readCnt")
            String readCnt,

            @JsonProperty("salary")
            String salary,

            @JsonProperty("id")
            String id
    ){}
}

