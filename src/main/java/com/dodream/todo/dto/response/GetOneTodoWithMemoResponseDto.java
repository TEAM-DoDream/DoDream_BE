package com.dodream.todo.dto.response;

import com.dodream.todo.domain.Todo;
import io.swagger.v3.oas.annotations.media.Schema;
import java.util.List;
import lombok.Builder;

@Builder
public record GetOneTodoWithMemoResponseDto(
    @Schema(description = "투두 id", example = "12")
    Long todoId,
    @Schema(description = "투두 제목", example = "“급식 도우미 근무 조건” 키워드로 업무 시간대, 복장, 식단 보조 등 기본 정보 확인하기")
    String title,
    @Schema(description = "공개 여부", example = "false")
    Boolean isPublic,
    @Schema(description = "메모", example = "12/21일 까지 아래 것들 완료해야지")
    String memoText,
    @Schema(description = "이미지들")
    List<TodoImageResponseDto> images

    ) {

    public static GetOneTodoWithMemoResponseDto from(Todo todo) {
        return GetOneTodoWithMemoResponseDto.builder()
            .todoId(todo.getId())
            .title(todo.getTitle())
            .isPublic(todo.getIsPublic())
            .memoText(todo.getMemoText())
            .images(todo.getImages().stream()
                    .map(TodoImageResponseDto::from)
                    .toList())
            .build();
    }
}
