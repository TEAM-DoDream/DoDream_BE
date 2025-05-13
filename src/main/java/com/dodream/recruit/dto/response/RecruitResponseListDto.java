package com.dodream.recruit.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Locale;

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

            @JsonProperty("expiration-timestamp")
            @Schema(description = "마감일 타임스탬프(DB저장에 활용)", example = "2025-06-01T23:59:59+0900")
            String expirationTimestamp,

            @JsonProperty("expiration-date")
            @Schema(description = "마감일", example = "05/22(수)")
            String expirationDate,

            @Schema(description = "마감일까지 남은 일수(채용시 마감인 경우 채용시 마감)", example = "D-6")
            String deadline
    ){}

    public static List<RecruitResponseListDto.Job> toResponseListDto(
            RecruitResponseListApiDto recruitResponseListApiDto
    ){
        return recruitResponseListApiDto.jobs().job() == null
                ? List.of()
                : recruitResponseListApiDto.jobs().job().stream()
                .map(job -> new RecruitResponseListDto.Job(
                        job.url(),
                        job.active(),
                        job.position() != null ? job.position().title() : null,
                        job.company().detail().name(),
                        job.position() != null && job.position().location() != null
                                ? getLocationName(job.position().location().name())
                                : null,
                        job.position() != null && job.position().jobType() != null ? job.position().jobType().name() : null,
                        job.position() != null && job.position().experienceLevel() != null ? job.position().experienceLevel().name() : null,
                        job.position() != null && job.position().requiredEducationLevel() != null ? job.position().requiredEducationLevel().name() : null,
                        job.closeType() != null ? job.closeType().name() : null,
                        job.salary() != null ? job.salary().name() : null,
                        job.id(),
                        job.expirationDate(),
                        getExpirationDate(job.expirationDate(), job.closeType().code()),
                        getRemainingDate(job.expirationDate(), job.closeType().code())
                ))
                .toList();
    }

    private static String getLocationName(String locName){
            return locName.replace("&gt;","").replaceAll("\\s+", " ");
    }

    private static String getRemainingDate(String date, String closeType){
            if(closeType.equals("1")){
                    ZonedDateTime expirationZdt = ZonedDateTime.parse(date, DateTimeFormatter.ofPattern("uuuu-MM-dd'T'HH:mm:ssZ"));
                    LocalDate expirationLocalDate = expirationZdt.toLocalDate();

                    LocalDate todayKst = LocalDate.now(ZoneId.of("Asia/Seoul"));
                    long daysBetween = ChronoUnit.DAYS.between(todayKst, expirationLocalDate);

                    if(daysBetween < 0){
                            return "마감됨";
                    }

                    return "D-" + daysBetween;
            }else if(closeType.equals("2")){
                    return "채용시 마감";
            }else if(closeType.equals("3")) {
                    return "상시 채용";
            }else if(closeType.equals("4")) {
                    return "수시 채용";
            }

            return "일자 변환 오류";
    }

    private static String getExpirationDate(String date, String closeType){
            if(closeType.equals("1")){
                    return getFormattedDate(date);
            }else if(closeType.equals("2")){
                    return "채용시 마감";
            }else if(closeType.equals("3")) {
                    return "상시 채용";
            }else if(closeType.equals("4")) {
                    return "수시 채용";
            }

            return "일자 변환 오류";
    }

    private static String getFormattedDate(String date){
            DateTimeFormatter INPUT_DATE_TIME_FORMATTER =
                    DateTimeFormatter.ofPattern("uuuu-MM-dd'T'HH:mm:ssZ");

            DateTimeFormatter OUTPUT_DATE_FORMATTER =
                    DateTimeFormatter.ofPattern("MM/dd(E)", Locale.KOREAN);

            String trimmedDateString = date.trim();

            ZonedDateTime expirationZdt = ZonedDateTime.parse(trimmedDateString, INPUT_DATE_TIME_FORMATTER);
            LocalDate localDate = expirationZdt.toLocalDate();

            return localDate.format(OUTPUT_DATE_FORMATTER);
    }
}