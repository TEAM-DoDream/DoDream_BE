package com.dodream.todo.dto.response;

import com.dodream.todo.domain.Todo;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

@Builder
public record GetOneTodoAtHomeResponseDto(

    @Schema(description = "투두 id", example = "12")
    Long todoId,

    @Schema(description = "투두 제목", example = "“급식 도우미 근무 조건” 키워드로 업무 시간대, 복장, 식단 보조 등 기본 정보 확인하기")
    String title,

    @Schema(description = "완료 여부", example = "false")
    Boolean completed
) {

    public static GetOneTodoAtHomeResponseDto from(Todo todo) {
        return GetOneTodoAtHomeResponseDto.builder()
            .todoId(todo.getId())
            .title(todo.getTitle())
            .completed(todo.getCompleted())
            .build();
    }

}
