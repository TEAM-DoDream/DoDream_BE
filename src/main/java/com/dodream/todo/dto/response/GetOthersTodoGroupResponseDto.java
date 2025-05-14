package com.dodream.todo.dto.response;

import com.dodream.todo.domain.Todo;
import com.dodream.todo.domain.TodoGroup;
import io.swagger.v3.oas.annotations.media.Schema;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.stream.Collectors;
import lombok.Builder;

@Builder
public record GetOthersTodoGroupResponseDto(
    @Schema(description = "투두 그룹 id", example = "12")
    Long todoGroupId,
    @Schema(description = "멤버 닉네임", example = "두둠칫")
    String memberNickname,
    @Schema(description = "거주지", example = "대전 광역시 서구")
    String regionName,
    @Schema(description = "투두 경과 일자(X일)", example = "38")
    Long daysAgo,
    @Schema(description = "직업 이름", example = "요양보호사")
    String jobName,
    @Schema(description = "투두 개수", example = "12")
    Long todoCount,
    @Schema(description = "투두 리스트")
    List<GetOthersTodoResponseDto> todos

) {

    public static GetOthersTodoGroupResponseDto of(TodoGroup todoGroup, List<Todo> todos,
        Long todoCount) {
        return GetOthersTodoGroupResponseDto.builder()
            .todoGroupId(todoGroup.getId())
            .memberNickname(todoGroup.getMember().getNickName())
            .regionName(todoGroup.getMember().getRegion().getRegionName())
            .daysAgo(
                ChronoUnit.DAYS.between(todoGroup.getCreatedAt().toLocalDate(), LocalDate.now())
                    + 1)
            .jobName(todoGroup.getJob().getJobName())
            .todoCount(todoCount)
            .todos(todos.stream()
                .map(GetOthersTodoResponseDto::from)
                .collect(Collectors.toList()))
            .build();
    }
}
