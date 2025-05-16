package com.dodream.job.dto.request.clova;

import io.swagger.v3.oas.annotations.media.Schema;

public record ChatRequest(

        @Schema(description = "챗봇이 할일을 지정합니다.", example = "당신은 개인 ai비서입니다.")
        String systemMessage,

        @Schema(description = "유저가 작성한 메시지를 입력합니다.", example = "안녕하세요")
        String userMessage
) {
}
