package com.dodream.todo.dto.response;

import com.dodream.member.domain.Member;
import com.dodream.todo.domain.TodoGroup;
import io.swagger.v3.oas.annotations.media.Schema;
import java.util.Collections;
import java.util.List;
import lombok.Builder;

@Builder
public record GetOneTodoGroupResponseDto(
    @Schema(description = "생성된 투두 그룹 id", example = "12")
    Long todoGroupId,
    @Schema(description = "멤버 id(DB상)", example = "1")
    Long memberId,
    @Schema(description = "직업 이름", example = "요양보호사")
    String jobName,
    @Schema(description = "조회수", example = "101")
    Long totalView,
    @Schema(description = "카테고리별 투두 항목")
    List<GetTodoGroupByCategoryResponseDto> todos

) {

    public static GetOneTodoGroupResponseDto of(Member member, TodoGroup myTodoGroup,
        List<GetTodoGroupByCategoryResponseDto> todos) {
        return GetOneTodoGroupResponseDto.builder()
            .todoGroupId(myTodoGroup.getId())
            .memberId(member.getId())
            .jobName(myTodoGroup.getJob().getJobName())
            .totalView(myTodoGroup.getTotalView())
            .todos(todos)
            .build();
    }

    public static GetOneTodoGroupResponseDto empty(Member member) {
        return new GetOneTodoGroupResponseDto(null, member.getId(), null, null,
            Collections.emptyList());
    }


}
