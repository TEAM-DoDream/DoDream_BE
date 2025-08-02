package com.dodream.todo.dto.response;

import com.dodream.member.domain.Member;
import io.swagger.v3.oas.annotations.media.Schema;

public record DeleteMemberJobResponseDto(
    @Schema(description = "멤버 id(DB상)", example = "1")
    Long memberId,
    @Schema(description = "메세지", example = "담은 직업이 삭제되었습니다.")
    String message
) {

    public static DeleteMemberJobResponseDto from(Member member) {
        return new DeleteMemberJobResponseDto(member.getId(), "담은 직업이 삭제되었습니다.");
    }

}
