package com.dodream.todo.dto.response;

import com.dodream.todo.domain.Todo;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

@Builder
public record GetOnePopularTodoGroupResponseDto(
    @Schema(description = "투두 id", example = "12")
    Long todoId,
    @Schema(description = "투두 제목", example = "요양보호사 자격증 준비하기")
    String title,
    @Schema(description = "멤버 프로필 사진", example = "www.~")
    String profileImage,
    @Schema(description = "멤버 닉네임", example = "두둠칫")
    String memberNickname,
    @Schema(description = "멤버 단계", example = "새싹 단계")
    String memberLevel,
    @Schema(description = "직업 이름", example = "요양보호사")
    String jobName,
    @Schema(description = "담은 횟수 ", example = "101")
    Long saveCount
) {

    public static GetOnePopularTodoGroupResponseDto from(Todo todo) {
        return GetOnePopularTodoGroupResponseDto.builder()
            .todoId(todo.getId())
            .title(todo.getTitle())
            .profileImage(todo.getMember().getProfileImage() == null ? null
                : todo.getMember().getProfileImage())
            .memberNickname(todo.getMember().getNickName())
            .memberLevel(
                todo.getMember().getLevel() == null ? null : todo.getMember().getLevel().getValue())
            .jobName(todo.getTodoGroup().getJob().getJobName())
            .saveCount(todo.getSaveCount())
            .build();
    }

}
