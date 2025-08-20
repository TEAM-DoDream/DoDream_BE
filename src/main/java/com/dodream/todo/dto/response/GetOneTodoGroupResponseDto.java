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
public record GetOneTodoGroupResponseDto(
    @Schema(description = "투두 그룹 id", example = "12")
    Long todoGroupId,
    @Schema(description = "멤버 닉네임", example = "두둠칫")
    String memberNickname,
    @Schema(description = "멤버 프로필 사진", example = "www.~")
    String profileImage,
    @Schema(description = "멤버 지역 이름", example = "대전 서구")
    String regionName,
    @Schema(description = "투두 경과 일자(X일)", example = "38")
    Long daysAgo,
    @Schema(description = "직업 id", example = "1")
    Long jobId,
    @Schema(description = "조회수", example = "101")
    Long totalView,
    @Schema(description = "투두 목록")
    List<GetOneTodoResponseDto> todos

) {

    public static GetOneTodoGroupResponseDto of(Member member, TodoGroup myTodoGroup,
        List<GetOneTodoResponseDto> todos) {
        return GetOneTodoGroupResponseDto.builder()
            .todoGroupId(myTodoGroup.getId())
            .memberNickname(member.getNickName())
            .profileImage(member.getProfileImage())
            .regionName(member.getRegion().getRegionName())
            .daysAgo(
                ChronoUnit.DAYS.between(myTodoGroup.getCreatedAt().toLocalDate(), LocalDate.now())
                    + 1)
            .jobId(myTodoGroup.getJob().getId())
            .totalView(myTodoGroup.getTotalView())
            .todos(todos)
            .build();
    }

    public static GetOneTodoGroupResponseDto empty(Member member) {
        return new GetOneTodoGroupResponseDto(null, member.getNickName(), null,null, null, null,
            null, Collections.emptyList());
    }


}
