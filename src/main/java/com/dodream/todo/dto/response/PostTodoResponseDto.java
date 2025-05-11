package com.dodream.todo.dto.response;

import com.dodream.todo.domain.Todo;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;

public record PostTodoResponseDto(

    @NotNull
    @Schema(description = "투두 그룹 id", example = "2")
    Long todoGroupId,

    @NotNull
    @Schema(description = "투두 제목", example = "요양보호사 관련 자격증 찾아보기")
    String todoTitle,

    @Schema(description = "메세지", example = "할 일이 추가되었습니다")
    String message

) {

    public static PostTodoResponseDto of(Long todoGroupId, Todo todo) {
        return new PostTodoResponseDto(todoGroupId, todo.getTitle(), "할 일이 추가되었습니다");

    }

}
