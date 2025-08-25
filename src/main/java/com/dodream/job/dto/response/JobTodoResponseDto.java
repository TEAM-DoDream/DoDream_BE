package com.dodream.job.dto.response;

import com.dodream.job.domain.JobTodo;
import io.swagger.v3.oas.annotations.media.Schema;

public record JobTodoResponseDto(
    @Schema(name = "JobTodo id", example = "5")
    Long JobTodoId,
    @Schema(name = "JobTodo 내용", example = "요양 보호사에 대한 자격증 준비하기")
    String title

) {

    public static JobTodoResponseDto from(JobTodo jobTodo) {
        return new JobTodoResponseDto(jobTodo.getId(), jobTodo.getTitle());
    }
}
