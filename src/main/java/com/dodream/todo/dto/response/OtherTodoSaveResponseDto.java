package com.dodream.todo.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;

public record OtherTodoSaveResponseDto (

        @Schema(description = "현재 저장 완료된 투두 id", example = "1")
        Long id,

        @Schema(description = "알림 메시지", example = "저장에 성공했습니다.")
        String message
){
}
