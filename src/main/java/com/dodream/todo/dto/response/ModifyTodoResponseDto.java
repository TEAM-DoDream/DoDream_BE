package com.dodream.todo.dto.response;

import com.dodream.todo.domain.Todo;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

@Builder
public record ModifyTodoResponseDto(
    @NotNull
       @Schema(description = "투두 그룹 id", example = "12")
       Long todoGroupId,
       @NotNull
       @Schema(description = "투두 id", example = "2")
       Long todoId,
       @NotNull
       @Schema(description = "투두 제목", example = "요양보호사 관련 자격증 찾아보기")
       String todoTitle,
       @Schema(description = "공개 여부", example = "true")
       Boolean isPublic,
       @Schema(description = "메모", example = "세상은 빠르게 변화하고 있고,우리는 그 속에서 다양한~")
       String memoText,
       @Schema(description = "메세지", example = "투두가 수정되었습니다.")
       String message
) {
    public static ModifyTodoResponseDto of(Long todoGroupId, Todo todo) {
           return ModifyTodoResponseDto.builder()
               .todoGroupId(todoGroupId)
               .todoId(todo.getId())
               .todoTitle(todo.getTitle())
               .isPublic(todo.getIsPublic())
               .memoText(builder().memoText)
               .message("투두가 수정되었습니다.")
               .build();
       }

}
