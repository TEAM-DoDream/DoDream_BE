package com.dodream.todo.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import java.util.List;

public record GetTodoGroupByCategoryResponseDto(

    @Schema(description = "투두 카테고리", example = "시작하기")
    String todoCategory,

    @Schema(description = "개별 투두 항목")
    List<GetOneTodoResponseDto> todos

) {

    public static GetTodoGroupByCategoryResponseDto of(String todoCategory,
        List<GetOneTodoResponseDto> todos) {
        return new GetTodoGroupByCategoryResponseDto(
            todoCategory, todos
        );
    }

}
