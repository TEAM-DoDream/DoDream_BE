package com.dodream.todo.dto.response;

import com.dodream.job.domain.TodoCategory;
import io.swagger.v3.oas.annotations.media.Schema;
import java.util.List;

public record GetTodoGroupByCategoryResponseDto(

    @Schema(description = "투두 카테고리", example = "PREPARE")
    TodoCategory todoCategory,

    @Schema(description = "투두 카테고리", example = "준비하기")
    String todoCategoryValue,

    @Schema(description = "개별 투두 항목")
    List<GetOneTodoResponseDto> todos

) {

    public static GetTodoGroupByCategoryResponseDto of(TodoCategory todoCategory,
        List<GetOneTodoResponseDto> todos) {
        return new GetTodoGroupByCategoryResponseDto(
            todoCategory, todoCategory.getValue(), todos
        );
    }

}
