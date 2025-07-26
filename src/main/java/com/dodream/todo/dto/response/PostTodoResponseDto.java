package com.dodream.todo.dto.response;

import com.dodream.todo.domain.Todo;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

@Builder
public record PostTodoResponseDto(

    @Schema(description = "투두 그룹 id", example = "12")
    Long todoGroupId,
    @Schema(description = "투두 id", example = "2")
    Long todoId,
    @Schema(description = "투두 제목", example = "요양보호사 관련 자격증 찾아보기")
    String todoTitle,
    @Schema(description = "메세지", example = "투두가 추가되었습니다.")
    String message

) {

    public static PostTodoResponseDto of(Long todoGroupId, Todo todo) {
        return PostTodoResponseDto.builder()
            .todoGroupId(todoGroupId)
            .todoId(todo.getId())
            .todoTitle(todo.getTitle())
            .message("투두가 추가되었습니다.")
            .build();
    }

}
