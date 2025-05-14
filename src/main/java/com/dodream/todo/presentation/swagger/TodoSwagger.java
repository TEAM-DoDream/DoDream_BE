package com.dodream.todo.presentation.swagger;

import com.dodream.core.config.swagger.ApiErrorCode;
import com.dodream.core.presentation.RestResponse;
import com.dodream.todo.dto.response.GetOthersTodoGroupResponseDto;
import com.dodream.todo.exception.TodoErrorCode;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import org.springframework.http.ResponseEntity;

@Tag(name = "Todo - 홈화면", description = "홈화면 - 투두 리스트 관련 API")
public interface TodoSwagger {

    @Operation(
        summary = "홈화면 - 홈화면 타유저 투두 리스트 목록 조회 API",
        description = "홈화면에서  타유저 투두 리스트 목록 조회 API",
        operationId = "/v1/todo/other"
    )
    @ApiErrorCode(TodoErrorCode.class)
    ResponseEntity<RestResponse<List<GetOthersTodoGroupResponseDto>>> getOneTodoGroupAtHome();


}
