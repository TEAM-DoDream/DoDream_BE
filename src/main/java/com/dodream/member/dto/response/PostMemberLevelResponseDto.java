package com.dodream.member.dto.response;

import com.dodream.member.domain.Level;
import io.swagger.v3.oas.annotations.media.Schema;

public record PostMemberLevelResponseDto(

    @Schema(description = "멤버 id(DB상)", example = "1")
    Long memberId,

    @Schema(description = "멤버 단계", example = "새싹")
    String level,

    @Schema(description = "메세지", example = "멤버 단계 설정 완료")
    String message

) {
    public static PostMemberLevelResponseDto of(Long memberId, Level level) {
          return new PostMemberLevelResponseDto(memberId, level.getValue() , "멤버 단계 설정 완료");
      }

}
