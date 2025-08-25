package com.dodream.job.dto.response;

import com.dodream.job.domain.Job;
import com.dodream.job.domain.TodoCategory;
import io.swagger.v3.oas.annotations.media.Schema;
import java.util.List;

public record JobTodoListResponseDto(
    @Schema(name = "직업 DB 식별자", example = "1")
    Long jobId,

    @Schema(name = "직업 이름", example = "요양보호사")
    String jobName,

    @Schema(name = "투두 카테고리", example = "준비하기")
    String todoCategory,

    @Schema(name = "직업 투두 목록")
    List<JobTodoResponseDto> jobTodos

) {

    public static JobTodoListResponseDto of(Job job, List<JobTodoResponseDto> jobTodos, TodoCategory todoCategory) {
        return new JobTodoListResponseDto(job.getId(), job.getJobName(), todoCategory.getValue(), jobTodos);
    }
}
