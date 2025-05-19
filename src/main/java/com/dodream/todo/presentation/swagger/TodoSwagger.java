package com.dodream.todo.presentation.swagger;

import com.dodream.core.config.swagger.ApiErrorCode;
import com.dodream.core.presentation.RestResponse;
import com.dodream.todo.dto.response.GetOneTodoGroupResponseDto;
import com.dodream.todo.dto.response.GetOneTodoResponseDto;
import com.dodream.todo.dto.response.GetOthersTodoGroupResponseDto;
import com.dodream.todo.dto.response.GetOthersTodoSimpleResponseDto;
import com.dodream.todo.exception.TodoErrorCode;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

@Tag(name = "Todo", description = "투두 리스트 관련 API")
public interface TodoSwagger {

    @Operation(
        summary = "홈화면 - 홈화면 타유저 투두 리스트 목록 조회 API",
        description = "홈화면에서  타유저 투두 리스트 목록 조회",
        operationId = "/v1/todo/other"
    )
    @ApiErrorCode(TodoErrorCode.class)
    ResponseEntity<RestResponse<List<GetOthersTodoGroupResponseDto>>> getOneTodoGroupAtHome();

    @Operation(
        summary = "직업 정보 페이지 - 타유저 투두 리스트 목록 조회 API",
        description = "직업 정보 페이지 우측 상단 - 타유저 투두 리스트 목록 조회",
        operationId = "/v1/todo/other/simple/{jobId}"
    )
    @ApiErrorCode(TodoErrorCode.class)
    ResponseEntity<RestResponse<List<GetOthersTodoSimpleResponseDto>>> getOthersTodoSimpleByJob(
        @PathVariable Long jobId);

    @Operation(
        summary = "특정 직업 - 타유저 투두 리스트 목록 조회 API",
        description = "(직업 정보 페이지에서 [더 보러가기] 클릭시, 특정 직업에 대한 타유저 투두 리스트 목록 조회 ",
        operationId = "/v1/todo/other/{jobId}"
    )
    @ApiErrorCode(TodoErrorCode.class)
    ResponseEntity<RestResponse<Page<GetOthersTodoGroupResponseDto>>> getOthersTodoByJob(
        @PathVariable Long jobId, @RequestParam(defaultValue = "0") int page);

    @Operation(
        summary = "타유저 투두 리스트 상세 조회 API",
        description = "타유저 투두 리스트를 상세 조회",
        operationId = "/v1/todo/{todoGroupId}}"
    )
    @ApiErrorCode(TodoErrorCode.class)
    ResponseEntity<RestResponse<GetOneTodoGroupResponseDto>> getOneOthersTodoGroup(
        @PathVariable Long todoGroupId);


}
