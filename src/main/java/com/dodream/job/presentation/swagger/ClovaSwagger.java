package com.dodream.job.presentation.swagger;

import com.dodream.core.config.swagger.ApiErrorCode;
import com.dodream.core.presentation.RestResponse;
import com.dodream.job.dto.request.clova.ChatRequest;
import com.dodream.job.dto.response.ChatResponse;
import com.dodream.job.exception.JobErrorCode;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;

@Tag(name = "Test", description = "서버 테스트용 컨트롤러")
public interface ClovaSwagger {

    @Operation(
            summary = "Clova Studio 관련 테스트 컨트롤러",
            description = "Clova Studio관련 테스트를 진행합니다.",
            operationId = "/test/chat"
    )
    @ApiErrorCode(JobErrorCode.class)
    ResponseEntity<RestResponse<ChatResponse>> getClovaResponse(
            @RequestBody ChatRequest chatRequest
    );
}
