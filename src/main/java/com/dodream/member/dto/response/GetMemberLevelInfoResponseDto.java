package com.dodream.member.dto.response;

import com.dodream.member.domain.Level;
import io.swagger.v3.oas.annotations.media.Schema;

public record GetMemberLevelInfoResponseDto(
    @Schema(description = "레벨", example = "SEED")
    Level code,

    @Schema(description = "레벨 한글명", example = "새싹")
    String value,

    @Schema(description = "레벨 설명", example = "관심만 있고, 아직 시작은 안했어요")
    String description

) {

    public static GetMemberLevelInfoResponseDto from(Level level) {
        return new GetMemberLevelInfoResponseDto(level, level.getValue(), level.getDescription());
    }

}
