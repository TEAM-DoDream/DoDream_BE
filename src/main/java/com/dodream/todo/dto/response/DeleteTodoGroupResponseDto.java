package com.dodream.todo.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;

public record DeleteTodoGroupResponseDto(
    @Schema(description = "새로 당은 직업 id")
    Long newJobId,

    @Schema(description = "메세지", example = "관심직업이 변경되었습니다")
    String message

) {

    public static DeleteTodoGroupResponseDto from(Long newJobId) {
        return new DeleteTodoGroupResponseDto(newJobId, "관심직업이 변경되었습니다");
    }
}

