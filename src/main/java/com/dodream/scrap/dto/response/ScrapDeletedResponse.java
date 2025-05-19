package com.dodream.scrap.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;

public record ScrapDeletedResponse(

        @Schema(description = "스크랩 id", example = "1")
        Long id,

        @Schema(description = "메시지", example = "공고가 성공적으로 삭제되었습니다.")
        String message
) {

    public static ScrapDeletedResponse of(Long id) {
        return new ScrapDeletedResponse(id, "공고가 성공적으로 삭제되었습니다.");
    }
}
