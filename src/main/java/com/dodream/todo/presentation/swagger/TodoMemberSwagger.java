package com.dodream.todo.presentation.swagger;

import com.dodream.core.config.swagger.ApiErrorCode;
import com.dodream.core.presentation.RestResponse;
import com.dodream.todo.dto.response.AddJobTodoResponseDto;
import com.dodream.todo.dto.response.ChangeCompleteStateTodoResponseDto;
import com.dodream.todo.dto.response.ChangePublicStateTodoResponseDto;
import com.dodream.todo.dto.response.DeleteTodoResponseDto;
import com.dodream.todo.dto.response.GetOneTodoGroupResponseDto;
import com.dodream.todo.dto.response.GetOneTodoWithMemoResponseDto;
import com.dodream.todo.dto.response.GetTodoJobResponseDto;
import com.dodream.todo.exception.TodoErrorCode;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;

@Tag(name = "Todo", description = "마이드림 - 투두 리스트 관련 API")
public interface TodoMemberSwagger {

    @Operation(
        summary = "마이드림 - 투두리스트 첫화면 조회 API",
        description = "마이드림 - 투두리스트 첫화면을 조회한다. (마이드림 - 투두리스트)",
        operationId = "/v1/my-dream/todo"
    )
    @ApiErrorCode(TodoErrorCode.class)
    ResponseEntity<RestResponse<GetOneTodoGroupResponseDto>> getOneTodoGroupAtHome();


    @Operation(
        summary = "직업 담기 API",
        description = "해당 직업의 투두를 담는다. (마이드림 - 투두리스트)",
        operationId = "/v1/my-dream/job/{jobId}"
    )
    @ApiErrorCode(TodoErrorCode.class)
    ResponseEntity<RestResponse<AddJobTodoResponseDto>> addJobToMyTodoList(
        @PathVariable Long jobId);


    @Operation(
        summary = "나의 투두 직업 목록 조회 API",
        description = "나의 투두 직업 목록을 조회한다. (마이드림 - 투두리스트)",
        operationId = "/v1/my-dream/todo/jobs"
    )
    @ApiErrorCode(TodoErrorCode.class)
    ResponseEntity<RestResponse<List<GetTodoJobResponseDto>>> getOneTodoGroup();


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
        description = "개별 투두 아이템 메모를 조회한다 (마이드림 - 투두리스트 - 개별 투두 조회 - 투두 메모)",
        operationId = "/v1/my-dream/todo/{todoGroupId}/{todoId}"
    )
    @ApiErrorCode(TodoErrorCode.class)
    ResponseEntity<RestResponse<GetOneTodoWithMemoResponseDto>> getOneTodoMemo(
        @PathVariable Long todoGroupId, @PathVariable Long todoId);


    @Operation(
        summary = "개별 투두 삭제 API",
        description = "개별 투두 아이템 메모를 조회한다 (마이드림 - 투두리스트 - 개별 투두 조회 - 삭제)",
        operationId = "/v1/my-dream/todo/{todoGroupId}/{todoId}"
    )
    @ApiErrorCode(TodoErrorCode.class)
    ResponseEntity<RestResponse<DeleteTodoResponseDto>> deleteOneTodo(
        @PathVariable Long todoGroupId, @PathVariable Long todoId);


    @Operation(
        summary = "개별 투두 공개 상태 변경 API",
        description = "개별 투두 아이템의 공개 상태를 변경한다 (마이드림 - 투두리스트 - 개별 투두 조회 - 공개상태 변경)",
        operationId = "/v1/my-dream/todo/{todoGroupId}/{todoId}/public-state"
    )
    @ApiErrorCode(TodoErrorCode.class)
    ResponseEntity<RestResponse<ChangePublicStateTodoResponseDto>> changeOneTodoPublicState(
        @PathVariable Long todoGroupId, @PathVariable Long todoId);


    @Operation(
        summary = "개별 투두 완료 상태 변경 API",
        description = "개별 투두 아이템의 완료 상태를 변경한다 (마이드림 - 투두리스트 - 개별 투두 조회 - 완료상태 변경)",
        operationId = "/v1/my-dream/todo/{todoGroupId}/{todoId}/complete-state"
    )
    @ApiErrorCode(TodoErrorCode.class)
    ResponseEntity<RestResponse<ChangeCompleteStateTodoResponseDto>> changeOneTodoCompleteState(
        @PathVariable Long todoGroupId, @PathVariable Long todoId);

}