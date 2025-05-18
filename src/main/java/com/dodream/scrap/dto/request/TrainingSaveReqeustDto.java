package com.dodream.scrap.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Pattern;

public record TrainingSaveReqeustDto(

        @Schema(description = "훈련 과정 id", example = "AIG20240000469334")
        String trprId,

        @Schema(description = "훈련 과정 차수", example = "3")
        String trprDegr,

        @Schema(description = "훈련 기관 id", example = "500041590848")
        String trainstCstId,

        @Schema(description = "훈련 과정 시작 일자(YYYY.MM.DD)", example = "2025.05.21")
        String traStartDate,

        @Schema(description = "훈련 과정 종료 일자(YYYY.MM.DD)", example = "2025.07.21")
        String traEndDate
) {
}
