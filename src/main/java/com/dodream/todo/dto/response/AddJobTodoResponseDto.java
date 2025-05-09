package com.dodream.todo.dto.response;

import com.dodream.member.domain.Member;
import com.dodream.todo.domain.TodoGroup;
import io.swagger.v3.oas.annotations.media.Schema;

public record AddJobTodoResponseDto(

    @Schema(description = "멤버 id(DB상)", example = "1")
    Long memberId,

    @Schema(description = "생성된 투두 그룹 id", example = "12")
    Long todoGroupId,

    @Schema(description = "메세지", example = "직업 담기 완료")
    String message
) {

    public static AddJobTodoResponseDto of(Member member, TodoGroup myTodoGroup) {
        return new AddJobTodoResponseDto(member.getId(), myTodoGroup.getId(), "직업 담기 완료");
    }

}
