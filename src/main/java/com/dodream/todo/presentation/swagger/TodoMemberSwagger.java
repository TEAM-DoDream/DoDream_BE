package com.dodream.todo.presentation.swagger;

import com.dodream.core.config.swagger.ApiErrorCode;
import com.dodream.core.presentation.RestResponse;
import com.dodream.todo.dto.response.AddJobTodoResponseDto;
import com.dodream.todo.dto.response.DeleteTodoResponseDto;
import com.dodream.todo.dto.response.GetOneTodoGroupResponseDto;
import com.dodream.todo.dto.response.GetOneTodoWithMemoResponseDto;
import com.dodream.todo.exception.TodoErrorCode;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;

@Tag(name = "Todo", description = "마이드림 - 투두 리스트 관련 API")
public interface TodoMemberSwagger {

    @Operation(
        summary = "직업 담기 API",
        description = "해당 직업의 투두를 담는다. (마이드림 - 투두리스트)",
        operationId = "/v1/my-dream/job/{jobId}"
    )
    @ApiErrorCode(TodoErrorCode.class)
    ResponseEntity<RestResponse<AddJobTodoResponseDto>> addJobToMyTodoList(
        @PathVariable Long jobId);


    @Operation(
        summary = "나의 직업별 투두 조회 API",
        description = "직업별 투두를 조회한다 (마이드림 - 투두리스트 - 개별 조회)",
        operationId = "/v1/my-dream/todo/{todoGroupId}"
    )
    @ApiErrorCode(TodoErrorCode.class)
    ResponseEntity<RestResponse<GetOneTodoGroupResponseDto>> getOneTodoGroup(
        @PathVariable Long todoGroupId);

    @Operation(
        summary = "개별 투두 아이템 메모 조회 API",
        description = "개별 투두 아이템 메모를 조회한다 (마이드림 - 투두리스트 - 개별 조회 - 메모)",
        operationId = "/v1/my-dream/todo/{todoGroupId}/{todoId}"
    )
    @ApiErrorCode(TodoErrorCode.class)
    ResponseEntity<RestResponse<GetOneTodoWithMemoResponseDto>> getOneTodoMemo(
        @PathVariable Long todoGroupId, @PathVariable Long todoId);

    @Operation(
         summary = "개별 투두 삭제 API",
         description = "개별 투두 아이템 메모를 조회한다 (마이드림 - 투두리스트 - 개별 조회 - 삭제)",
         operationId = "/v1/my-dream/todo/{todoGroupId}/{todoId}"
     )
     @ApiErrorCode(TodoErrorCode.class)
     ResponseEntity<RestResponse<DeleteTodoResponseDto>> deleteOneTodo(
         @PathVariable Long todoGroupId, @PathVariable Long todoId);



}
