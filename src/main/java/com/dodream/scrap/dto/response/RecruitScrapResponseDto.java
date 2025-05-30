package com.dodream.scrap.dto.response;

import com.dodream.recruit.util.RecruitDateUtil;
import com.dodream.scrap.domain.entity.MemberRecruitScrap;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;

public record RecruitScrapResponseDto(

        @Schema(description = "스크랩 id", example = "1")
        Long scrapId,

        @JsonProperty("id")
        @Schema(description = "채용 공고 id", example = "50961016")
        String recruitId,

        @Schema(description = "직업 공고 제목", example = "요양보호사 선생님을 모십니다.")
        String title,

        @JsonProperty("companyName")
        @Schema(description = "기관 이름", example = "(주)이아소실버케어")
        String companyName,

        @JsonProperty("locationName")
        @Schema(description = "공고 지역 이름", example = "경기 고양시 덕양구")
        String locationName,

        @Schema(description = "경력 사항", example = "경력 무관")
        String experienceLevel,

        @JsonProperty("jobTypeName")
        @Schema(description = "고용 형태", example = "정규직")
        String jobTypeName,

        @JsonProperty("expiration-date")
        @Schema(description = "마감일", example = "05/22 (수)")
        String deadline,

        @JsonProperty("deadline")
        @Schema(description = "마감 d-day", example = "D-6")
        String dDay,

        @Schema(description = "사람인 이동 url", example = "https://")
        String url
) {
    public static RecruitScrapResponseDto from(MemberRecruitScrap memberRecruitScrap){
        return new RecruitScrapResponseDto(
                memberRecruitScrap.getId(),
                memberRecruitScrap.getRecruitId(),
                memberRecruitScrap.getTitle(),
                memberRecruitScrap.getCompanyName(),
                memberRecruitScrap.getLocationName(),
                memberRecruitScrap.getExperienceLevel(),
                memberRecruitScrap.getJobType(),
                RecruitDateUtil.getExpirationDate(
                        memberRecruitScrap.getExpirationDate(),
                        memberRecruitScrap.getCloseType().getCode()
                ),
                RecruitDateUtil.getRemainingDate(
                        memberRecruitScrap.getExpirationDate(),
                        memberRecruitScrap.getCloseType().getCode()
                ),
                memberRecruitScrap.getRecruitUrl()
        );
    }
}
