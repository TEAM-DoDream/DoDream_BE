package com.dodream.todo.dto.response;

import com.dodream.todo.domain.TodoGroup;
import io.swagger.v3.oas.annotations.media.Schema;

public record GetTodoJobResponseDto(

    @Schema(description = "직업명", example = "요양보호사")
    String jobName,
    @Schema(description = "투두 그룹 id", example = "12")
    Long todoGroupId
) {

    public static GetTodoJobResponseDto from(TodoGroup todoGroup) {
        return new GetTodoJobResponseDto(todoGroup.getJob().getJobName(), todoGroup.getId());
    }

}
