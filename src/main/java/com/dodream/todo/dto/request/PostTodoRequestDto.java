package com.dodream.todo.dto.request;

import com.dodream.job.domain.TodoCategory;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record PostTodoRequestDto(
    @NotNull
    @Schema(description = "투두 제목", example = "요양보호사 관련 자격증 찾아보기")
    String todoTitle,

    @NotNull
    @Schema(description = "투두 카테고리", example = "PREPARE")
    TodoCategory todoCategory

) {

}
