package com.dodream.todo.presentation.swagger;

import com.dodream.core.config.swagger.ApiErrorCode;
import com.dodream.core.presentation.RestResponse;
import com.dodream.todo.dto.response.AddJobTodoResponseDto;
import com.dodream.todo.exception.MemberTodoErrorCode;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;

@Tag(name = "Todo", description = "마이드림 - 투두 리스트 관련 API")
public interface MemberTodoSwagger {

    @Operation(
        summary = "직업 담기 API",
        description = "해당 직업의 투두를 담는다. (마이드림 - 투두리스트)",
        operationId = "/v1/my-dream/job/{jobId}"
    )
    @ApiErrorCode(MemberTodoErrorCode.class)
    ResponseEntity<RestResponse<AddJobTodoResponseDto>> addJobToMyTodoList(
        @PathVariable Long jobId);


}
