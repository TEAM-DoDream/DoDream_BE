package com.dodream.todo.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;

public record GetPopularTodoDescriptionDto(

        @Schema(description = "투두 id", example = "1")
        Long id,

        @Schema(description = "투두 내용", example = "투두 내용")
        String title
) {
}
