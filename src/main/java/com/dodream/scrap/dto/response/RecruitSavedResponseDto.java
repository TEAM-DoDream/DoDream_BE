package com.dodream.scrap.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;

public record RecruitSavedResponseDto(

        @Schema(description = "user_id", example = "1")
        Long userId,

        @Schema(description = "저장 성공 여부", example = "true")
        Boolean isScrap
){
}
