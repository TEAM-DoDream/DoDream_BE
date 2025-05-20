package com.dodream.scrap.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

public record TrainingSaveReqeustDto(

        @Schema(description = "훈련 과정 id", example = "AIG20240000461129")
        @NotNull(message = "훈련 과정 id는 필수 입력 사항입니다.")
        String trprId,

        @Schema(description = "훈련 과정 차수", example = "4")
        @NotNull(message = "훈련 과정 차수는 필수 입력 사항입니다.")
        String trprDegr,

        @Schema(description = "훈련 기관 id", example = "500034772296")
        @NotNull(message = "훈련 기관 id는 필수 입력 사항입니다.")
        String trainstCstId,

        @Schema(description = "훈련 과정 시작 일자(YYYY/MM/DD)", example = "2025/05/21")
        @Pattern(regexp = "^\\d{4}\\/\\d{2}\\/\\d{2}$", message = "날짜 형식은 YYYY/MM/DD 여야 합니다")
        String traStartDate,

        @Schema(description = "훈련 과정 종료 일자(YYYY/MM/DD)", example = "2025/07/21")
        @Pattern(regexp = "^\\d{4}\\/\\d{2}\\/\\d{2}$", message = "날짜 형식은 YYYY/MM/DD 여야 합니다")
        String traEndDate
) {
}
