package com.dodream.scrap.dto.response;

import com.dodream.scrap.domain.entity.MemberTrainingScrap;
import com.dodream.training.util.TrainingDateUtils;
import com.dodream.training.util.TrainingPriceUtils;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;

public record TrainingScrapResponseDto(

        @Schema(description = "스크랩 id", example = "1")
        Long scrapId,

        @JsonProperty("trprId")
        @Schema(description = "훈련 과정 id", example = "AIG20240000492114")
        String trainingId,

        @JsonProperty("title")
        @Schema(description = "훈련 과정 이름", example = "요양보호사자격증취득과정 (사회복지사)")
        String trainingName,

        @JsonProperty("subTitle")
        @Schema(description = "훈련 기관 이름", example = "고색요양보호사교육원")
        String trainingOrgName,

        @JsonProperty("address")
        @Schema(description = "훈련 기관 주소", example = "경기 수원시 권선구")
        String trainingOrgAddr,

        @JsonProperty("trprDegr")
        @Schema(description = "훈련과정 차수", example = "17")
        String trainingDegree,

        @JsonProperty("traDuration")
        @Schema(description = "훈련 과정 기간", example = "약 3개월")
        String trainingDuration,

        @JsonProperty("realMan")
        @Schema(description = "훈련 과정 가격", example = "1000000")
        String trainingPrice,

        @JsonProperty("titleLink")
        @Schema(description = "훈련과정 url", example = "http://~")
        String titleLink
) {

    public static TrainingScrapResponseDto from(MemberTrainingScrap memberTrainingScrap) {
            return new TrainingScrapResponseDto(
                    memberTrainingScrap.getId(),
                    memberTrainingScrap.getTrainingId(),
                    memberTrainingScrap.getTrainingName(),
                    memberTrainingScrap.getTrainingOrgName(),
                    memberTrainingScrap.getTrainingOrgAddr(),
                    memberTrainingScrap.getTrainingDegree(),
                    TrainingDateUtils.calculateDurationByLocalDate(
                            memberTrainingScrap.getTrainingStartDate(),
                            memberTrainingScrap.getTrainingEndDate()
                    ),
                    TrainingPriceUtils.convertDecimalFormatForPrice(memberTrainingScrap.getTrainingManage()),
                    memberTrainingScrap.getTrainingUrl()
            );
    }
}
