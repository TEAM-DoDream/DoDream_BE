package com.dodream.scrap.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;

public record IsScrapCheckedResponse(

        @Schema(description = "현재 페이지 컴포넌트 인덱스", example = "0")
        int index,

        @Schema(description = "사용자의 스크랩 여부", example = "true")
        boolean isScrap
) {
}
