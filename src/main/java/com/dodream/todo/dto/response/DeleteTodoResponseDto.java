package com.dodream.todo.dto.response;

import com.dodream.todo.domain.Todo;
import io.swagger.v3.oas.annotations.media.Schema;

public record DeleteTodoResponseDto(
    @Schema(description = "삭제된 투두 id", example = "12")
    Long todoId,
    @Schema(description = "메세지", example = "투두가 삭제 되었습니다")
    String message

) {

    public static DeleteTodoResponseDto from(Todo todo) {
        return new DeleteTodoResponseDto(todo.getId(), "투두가 삭제 되었습니다");
    }

}
