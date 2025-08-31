package com.dodream.todo.dto.response;

import com.dodream.job.domain.JobTodo;
import com.dodream.member.domain.Member;
import io.swagger.v3.oas.annotations.media.Schema;

public record AddTodoResponseDto(
    @Schema(description = "멤버 id(DB상)", example = "1")
    Long memberId,

    @Schema(description = "추가된 JobTodo id", example = "5")
    Long jobTodoId,

    @Schema(description = "메세지", example = "할일 추가 완료")
    String message
) {

    public static AddTodoResponseDto of(Member member, JobTodo jobTodo) {
        return new AddTodoResponseDto(member.getId(), jobTodo.getId(), "할일 추가 완료");
    }

}