package com.dodream.job.controller.swagger;

import com.dodream.core.presentation.RestResponse;
import com.dodream.job.dto.request.clova.ChatRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;

@Tag(name = "Clova", description = "clova관련 테스트 컨트롤러")
public interface ClovaSwagger {

    @Operation(
            summary = "Clova Studio 관련 테스트 컨트롤러",
            description = "Clova Studio관련 테스트를 진행합니다.",
            operationId = "/v1/clova/chat"
    )
    ResponseEntity<RestResponse<String>> getClovaResponse(
            @RequestBody ChatRequest chatRequest
    );
}
