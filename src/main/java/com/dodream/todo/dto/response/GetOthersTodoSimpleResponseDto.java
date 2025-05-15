package com.dodream.todo.dto.response;

import com.dodream.todo.domain.Todo;
import com.dodream.todo.domain.TodoGroup;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

@Builder
public record GetOthersTodoSimpleResponseDto(

    @Schema(description = "투두 그룹 id", example = "12")
    Long todoGroupId,
    @Schema(description = "멤버 닉네임", example = "두둠칫")
    String memberNickname,
    @Schema(description = "투두 개수", example = "12")
    Long todoCount
) {

    public static GetOthersTodoSimpleResponseDto of(TodoGroup todoGroup, Long todoCount) {
        return GetOthersTodoSimpleResponseDto.builder()
            .todoGroupId(todoGroup.getId())
            .memberNickname(todoGroup.getMember().getNickName())
            .todoCount(todoCount)
            .build();
    }

}
