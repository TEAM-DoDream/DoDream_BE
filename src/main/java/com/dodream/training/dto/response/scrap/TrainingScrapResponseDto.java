package com.dodream.training.dto.response.scrap;

import io.swagger.v3.oas.annotations.media.Schema;

public record TrainingScrapResponseDto(

        @Schema(description = "유저 아이디", example = "1")
        Long userId,

        @Schema(description = "저장 결과", example = "true")
        Boolean isScrap
) {
}
