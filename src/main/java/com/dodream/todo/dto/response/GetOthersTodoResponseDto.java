package com.dodream.todo.dto.response;

import com.dodream.todo.domain.Todo;
import io.swagger.v3.oas.annotations.media.Schema;

public record GetOthersTodoResponseDto(

    @Schema(description = "투두 id", example = "5")
    Long todoId,

    @Schema(description = "투두 제목", example = "“급식 도우미 근무 조건” 키워드로 업무 시간대, 복장, 식단 보조 등 기본 정보 확인하기")
    String title,

    @Schema(description = "완료 여부", example = "false")
    Boolean completed

) {

    public static GetOthersTodoResponseDto from(Todo todo) {
        return new GetOthersTodoResponseDto(todo.getId(), todo.getTitle(), todo.getCompleted());
    }

}
