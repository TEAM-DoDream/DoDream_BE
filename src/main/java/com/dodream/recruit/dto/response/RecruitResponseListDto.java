package com.dodream.recruit.dto.response;

import com.dodream.recruit.util.RecruitDateUtil;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;

import java.util.List;

public record RecruitResponseListDto(
        @JsonProperty("count")
        @Schema(description = "페이지 내 검색 결과 수", example = "6")
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

                @Schema(description = "직업 이름", example = "요양보호사")
                String jobName,

                @JsonProperty("companyName")
                @Schema(description = "회사 이름", example = "재가요양센터")
                String companyName,

                @JsonProperty("locationName")
                @Schema(description = "지역 이름", example = "경기 안양시 만안구")
                String locationName,

                @JsonProperty("jobTypeName")
                @Schema(description = "근무형태", example = "정규직")
                String jobTypeName,

                @JsonProperty("experienceLevel")
                @Schema(description = "경력 정보", example = "경력 무관")
                String experienceLevel,

                @JsonProperty("requiredEducationLevel")
                @Schema(description = "학력 정보", example = "학력 무관")
                String requiredEducationLevel,

                @JsonProperty("closeType")
                @Schema(description = "접수 마감 정보", example = "접수마감일")
                String closeType,

                @JsonProperty("salary")
                @Schema(description = "연봉 값", example = "면접 후 결정")
                String salary,

                @JsonProperty("id")
                @Schema(description = "사람인 공고 ID", example = "50390519")
                String id,

                @Schema(description = "공고 게시일 타임스탬프(DB용)", example = "2025-05-15T23:59:59+0900")
                String postTimestamp,

                @Schema(description = "공고 게시 후 지난 시간(n 시간전)", example = "7시간 전")
                String postDate,

                @JsonProperty("expiration-timestamp")
                @Schema(description = "마감일 타임스탬프(DB저장에 활용)", example = "2025-06-01T23:59:59+0900")
                String expirationTimestamp,

                @JsonProperty("expiration-date")
                @Schema(description = "마감일", example = "05/22(수)")
                String expirationDate,

                @JsonProperty("deadline")
                @Schema(description = "마감일까지 남은 일수(채용시 마감인 경우 채용시 마감)", example = "D-6")
                String deadline,

                @JsonProperty("count")
                @Schema(description = "조회수(N명이 관심을 보였어요)", example = "5")
                String count
        ){}

        public static List<RecruitResponseListDto.Job> toResponseListDto(
                RecruitResponseListApiDto recruitResponseListApiDto,
                String keyword
        ){
                if (recruitResponseListApiDto.jobs().job() == null) {
                        return List.of();
                }

                return recruitResponseListApiDto.jobs().job().stream()
                        .map(job -> new RecruitResponseListDto.Job(
                                job.url(),
                                job.active(),
                                getPositionTitle(job),
                                keyword,
                                getCompanyName(job),
                                getLocationNameSafe(job),
                                getJobTypeName(job),
                                getExperienceLevelName(job),
                                getEducationLevelName(job),
                                getCloseTypeName(job),
                                getSalaryName(job),
                                job.id(),
                                job.postingDate(),
                                RecruitDateUtil.getFormattedPostingDate(job.postingDate()),
                                job.expirationDate(),
                                RecruitDateUtil.getExpirationDate(job.expirationDate(), safeCode(job)),
                                RecruitDateUtil.getRemainingDate(job.expirationDate(), safeCode(job)),
                                job.readCnt()
                        ))
                        .toList();
        }

        private static String getLocationName(String locName){
                if(locName == null || locName.isEmpty()) return null;
                return locName.replace("&gt;","").replaceAll("\\s+", " ");
        }

        private static String getPositionTitle(RecruitResponseListApiDto.Jobs.Job job) {
                return job.position() != null ? job.position().title() : null;
        }

        private static String getCompanyName(RecruitResponseListApiDto.Jobs.Job job) {
                return job.company() != null && job.company().detail() != null
                        ? job.company().detail().name()
                        : null;
        }

        private static String getLocationNameSafe(RecruitResponseListApiDto.Jobs.Job job) {
                if (job.position() != null && job.position().location() != null) {
                        return getLocationName(job.position().location().name());
                }
                return null;
        }

        private static String getJobTypeName(RecruitResponseListApiDto.Jobs.Job job) {
                return job.position() != null && job.position().jobType() != null
                        ? job.position().jobType().name()
                        : null;
        }

        private static String getExperienceLevelName(RecruitResponseListApiDto.Jobs.Job job) {
                return job.position() != null && job.position().experienceLevel() != null
                        ? job.position().experienceLevel().name()
                        : null;
        }

        private static String getEducationLevelName(RecruitResponseListApiDto.Jobs.Job job) {
                return job.position() != null && job.position().requiredEducationLevel() != null
                        ? job.position().requiredEducationLevel().name()
                        : null;
        }

        private static String getCloseTypeName(RecruitResponseListApiDto.Jobs.Job job) {
                return job.closeType() != null ? job.closeType().name() : null;
        }

        private static String getSalaryName(RecruitResponseListApiDto.Jobs.Job job) {
                return job.salary() != null ? job.salary().name() : null;
        }

        private static String safeCode(RecruitResponseListApiDto.Jobs.Job job) {
                return job.closeType() != null ? job.closeType().code() : null;
        }

}