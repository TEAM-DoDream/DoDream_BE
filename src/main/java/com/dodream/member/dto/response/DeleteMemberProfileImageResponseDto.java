package com.dodream.member.dto.response;

import com.dodream.member.domain.Member;
import io.swagger.v3.oas.annotations.media.Schema;

public record DeleteMemberProfileImageResponseDto(

    @Schema(description = "멤버 id(DB상)", example = "1")
    Long memberId,
    @Schema(description = "메세지", example = "프로필 사진이 삭제되었습니다.")
    String message
) {

    public static DeleteMemberProfileImageResponseDto from(Member member) {
        return new DeleteMemberProfileImageResponseDto(member.getId(), "프로필 사진이 삭제되었습니다.");
    }

}
