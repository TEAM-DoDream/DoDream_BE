package com.dodream.todo.dto.response;

import com.dodream.todo.domain.Todo;
import io.swagger.v3.oas.annotations.media.Schema;

public record ChangePublicStateTodoResponseDto(
    @Schema(description = "삭제된 투두 id", example = "12")
    Long todoId,

    @Schema(description = "공개여부", example = "true")
    Boolean isPublic,

    @Schema(description = "메세지", example = "투두의 공개 상태가 변경되었습니다")
    String message

) {

    public static ChangePublicStateTodoResponseDto from(Todo todo) {
        return new ChangePublicStateTodoResponseDto(todo.getId(), todo.getIsPublic(),
            "투두의 공개 상태가 변경되었습니다.");
    }

}

