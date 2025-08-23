package com.dodream.todo.dto.response;

import com.dodream.todo.util.ConvertLocalDateToString;
import io.swagger.v3.oas.annotations.media.Schema;

public record TodoCommunityResponseDto (

        // 투두 식별자, 유저 이름, 유저 이미지, 투두 기간, 투두 내용, 투두 저장 횟수, 회원 레벨 단계
        // 내가 저장한 투두인가 여부(default = false), 내가 저장한 경우 나의 투두에서의 id(취소시 사용)

        @Schema(description = "투두 식별자 ID", example = "1")
        Long id,

        @Schema(description = "유저 이름", example = "해바라기 엄마")
        String name,

        @Schema(description = "회원 레벨 단계", example = "새싹 단계")
        String level,

        @Schema(description = "유저 이미지 url", example = "https://~")
        String imageUrl,

        @Schema(description = "투두 진행 기간", example = "2분 전")
        String dDay,

        @Schema(description = "투두 내용", example = "집에 가고 싶다")
        String description,

        @Schema(description = "투두 저장 횟수", example = "1")
        Long saveCount
){
    public static TodoCommunityResponseDto of(TodoCommunityResponse response) {
        return new TodoCommunityResponseDto(
                response.id(),
                response.name(),
                response.level() == null ? "새싹 단계" : response.level().getValue(),
                response.imageUrl(),
                ConvertLocalDateToString.calculateTimeAgo(response.createdAt()),
                response.description(),
                response.saveCount()
        );
    }
}
