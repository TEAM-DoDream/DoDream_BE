package com.dodream.recruit.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;

import java.util.List;

public record RecruitResponseListDto(
        @JsonProperty("count")
        @Schema(description = "페이지 내 검색 결과 수", example = "20")
        int count,

        @JsonProperty("start")
        @Schema(description = "페이지 번호", example = "0")
        int start,

        @JsonProperty("total")
        @Schema(description = "총 검색 결과 수", example = "28")
        String total,

        @JsonProperty("job")
        List<Job> job
){
    public record Job(
            @JsonProperty("url")
            @Schema(description = "사람인 공고로 가는 url", example = "url")
            String url,

            @JsonProperty("active")
            @Schema(description = "활성/비활성화", example = "1")
            int active,

            @JsonProperty("title")
            @Schema(description = "공고 제목", example = "요양원에서 근무할 남 . 여 요양보호사 채용공고")
            String title,

            @JsonProperty("locationName")
            @Schema(description = "지역 이름", example = "경기 > 안양시 만안구")
            String locationName,

            @JsonProperty("jobTypeName")
            @Schema(description = "근무형태", example = "정규직")
            String jobTypeName,

            @JsonProperty("jobCode")
            @Schema(description = "직무 이름", example = "노인복지, 간병인, 환자 안내")
            String jobCode,

            @JsonProperty("experienceLevel")
            @Schema(description = "경력 정보", example = "경력 무관")
            String experienceLevel,

            @JsonProperty("requiredEducationLevel")
            @Schema(description = "학력 정보", example = "학력 무관")
            String requiredEducationLevel,

            @JsonProperty("closeType")
            @Schema(description = "접수 마감 정보", example = "접수 마감일")
            String closeType,

            @JsonProperty("keyword")
            @Schema(description = "직무 키워드", example = "노인 복지")
            String keyword,

            @JsonProperty("readCnt")
            @Schema(description = "조회수", example = "25")
            String readCnt,

            @JsonProperty("salary")
            @Schema(description = "연봉 값", example = "면접 후 결정")
            String salary,

            @JsonProperty("id")
            @Schema(description = "사람인 공고 ID", example = "50390519")
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