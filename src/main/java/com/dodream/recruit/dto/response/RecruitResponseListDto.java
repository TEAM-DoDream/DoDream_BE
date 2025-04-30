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

    public static List<RecruitResponseListDto.Job> toResponseListDto(RecruitResponseListApiDto recruitResponseListApiDto){
        return recruitResponseListApiDto.jobs().job() == null
                ? List.of()
                : recruitResponseListApiDto.jobs().job().stream()
                .map(job -> new RecruitResponseListDto.Job(
                        job.url(),
                        job.active(),
                        job.position() != null ? job.position().title() : null,
                        job.position() != null && job.position().location() != null ? job.position().location().name().replace("&gt;", ">") : null,
                        job.position() != null && job.position().jobType() != null ? job.position().jobType().name() : null,
                        job.position() != null && job.position().jobCode() != null ? job.position().jobCode().name() : null,
                        job.position() != null && job.position().experienceLevel() != null ? job.position().experienceLevel().name() : null,
                        job.position() != null && job.position().requiredEducationLevel() != null ? job.position().requiredEducationLevel().name() : null,
                        job.closeType() != null ? job.closeType().name() : null,
                        job.keyword(),
                        job.readCnt(),
                        job.salary() != null ? job.salary().name() : null,
                        job.id()
                ))
                .toList();
    }
}