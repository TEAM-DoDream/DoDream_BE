package com.dodream.todo.dto.response;

import com.dodream.member.domain.Member;
import com.dodream.todo.domain.TodoGroup;
import io.swagger.v3.oas.annotations.media.Schema;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Collections;
import java.util.List;
import lombok.Builder;

@Builder
public record GetOneTodoGroupAtHomeResponseDto(

    @Schema(description = "투두 그룹 id", example = "12")
    Long todoGroupId,
    @Schema(description = "멤버 닉네임", example = "두둠칫")
    String memberNickname,
    @Schema(description = "투두 경과 일자(X일)", example = "38")
    Long daysAgo,
    @Schema(description = "직업 이름", example = "요양보호사")
    String jobName,
    @Schema(description = "투두 목록")
    List<GetOneTodoAtHomeResponseDto> todos

) {

    public static GetOneTodoGroupAtHomeResponseDto of(Member member, TodoGroup myTodoGroup,
        List<GetOneTodoAtHomeResponseDto> todos) {
        return GetOneTodoGroupAtHomeResponseDto.builder()
            .todoGroupId(myTodoGroup.getId())
            .memberNickname(member.getNickName())
            .daysAgo(
                ChronoUnit.DAYS.between(myTodoGroup.getCreatedAt().toLocalDate(), LocalDate.now())
                    + 1)
            .jobName(myTodoGroup.getJob().getJobName())
            .todos(todos)
            .build();
    }

    public static GetOneTodoGroupAtHomeResponseDto empty(Member member) {
        return new GetOneTodoGroupAtHomeResponseDto(null, member.getNickName(), null, null,
            Collections.emptyList());
    }

}
