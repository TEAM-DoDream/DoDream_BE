package com.dodream.todo.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import java.util.List;

public record DeleteTodoGroupResponseDto(
    @Schema(description = "삭제된 관심 직업 id 목록")
    List<Long> deletedJobIds,

    @Schema(description = "메세지", example = "관심직업이 변경되었습니다")
    String message

) {

    public static DeleteTodoGroupResponseDto from(List<Long> deletedJobId) {
        return new DeleteTodoGroupResponseDto(deletedJobId, "관심직업이 변경되었습니다");
    }
}

