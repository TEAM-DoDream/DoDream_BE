package com.dodream.todo.dto.response;

import com.dodream.todo.domain.Todo;
import io.swagger.v3.oas.annotations.media.Schema;

public record ChangeCompleteStateTodoResponseDto(
    @Schema(description = "완료여부 변경된 투두 id", example = "12")
    Long todoId,

    @Schema(description = "완료여부", example = "true")
    Boolean completed,

    @Schema(description = "메세지", example = "투두의 완료 상태가 변경되었습니다")
    String message
) {

    public static ChangeCompleteStateTodoResponseDto from(Todo todo) {
        return new ChangeCompleteStateTodoResponseDto(todo.getId(), todo.getCompleted(),
            "투두의 완료 상태가 변경되었습니다.");
    }
}
