package com.dodream.todo.presentation.swagger;

import com.dodream.core.config.swagger.ApiErrorCode;
import com.dodream.core.presentation.RestResponse;
import com.dodream.todo.dto.request.ModifyTodoRequestDto;
import com.dodream.todo.dto.request.PostTodoRequestDto;
import com.dodream.todo.dto.response.AddJobTodoResponseDto;
import com.dodream.todo.dto.response.ChangeCompleteStateTodoResponseDto;
import com.dodream.todo.dto.response.ChangePublicStateTodoResponseDto;
import com.dodream.todo.dto.response.DeleteTodoResponseDto;
import com.dodream.todo.dto.response.GetOneTodoGroupAtHomeResponseDto;
import com.dodream.todo.dto.response.GetOneTodoGroupResponseDto;
import com.dodream.todo.dto.response.GetOneTodoWithMemoResponseDto;
import com.dodream.todo.dto.response.GetTodoJobResponseDto;
import com.dodream.todo.dto.response.ModifyTodoResponseDto;
import com.dodream.todo.dto.response.PostTodoResponseDto;
import com.dodream.todo.exception.TodoErrorCode;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

@Tag(name = "Todo 마이드림", description = "마이드림 - 투두 리스트 관련 API")
public interface TodoMemberSwagger {

    @Operation(
        summary = "🏠 홈화면 - 투두 리스트 조회 API",
        description = "홈화면에서의 투두리스트 조회한다.",
        operationId = "/v1/my-dream/todo/home"
    )
    @ApiErrorCode(TodoErrorCode.class)
    ResponseEntity<RestResponse<GetOneTodoGroupAtHomeResponseDto>> getOneTodoGroupAtHome();


    @Operation(
        summary = "투두리스트 첫화면 조회 API",
        description = "마이드림 - 투두리스트 첫화면을 조회한다. (마이드림 - 투두리스트)",
        operationId = "/v1/my-dream/todo"
    )
    @ApiErrorCode(TodoErrorCode.class)
    ResponseEntity<RestResponse<GetOneTodoGroupResponseDto>> getOneTodoGroupAtMyDream();


    @Operation(
        summary = "투두리스트 - 새로운 투두 추가 (메모 있을시 메모도 함께 추가)",
        description = "마이드림 - 투두리스트에 새로운 투두를 추가한다. (마이드림 - 투두리스트 - 개별 투두 추가)",
        operationId = "/v1/my-dream/todo-group/{todoGroupId}"
    )
    @ApiErrorCode(TodoErrorCode.class)
    ResponseEntity<RestResponse<PostTodoResponseDto>> postNewTodo(
        @PathVariable Long todoGroupId,
        @ModelAttribute @Valid PostTodoRequestDto postTodoRequestDto);

    @Operation(
        summary = "투두리스트 - 기존 투두 수정 (메모 있을시 메모도 함께 수정)",
        description = "마이드림 - 투두를 수정한다. (마이드림 - 투두리스트 - 개별 투두 수정 API)",
        operationId = "/v1/my-dream/todo/{todoId}"
    )
    @ApiErrorCode(TodoErrorCode.class)
    ResponseEntity<RestResponse<ModifyTodoResponseDto>> modifyTodo(
        @PathVariable Long todoId,
        @ModelAttribute @Valid ModifyTodoRequestDto modifyTodoRequestDto);


    @Operation(
        summary = "직업 담기 API",
        description = "해당 직업의 투두리스트를 담는다.",
        operationId = "/v1/my-dream/job/{jobId}"
    )
    @ApiErrorCode(TodoErrorCode.class)
    ResponseEntity<RestResponse<AddJobTodoResponseDto>> addJobToMyTodoList(
        @PathVariable Long jobId);


    @Operation(
        summary = "투두리스트 - 나의 투두 직업 목록 조회 API",
        description = "나의 투두 직업 목록을 조회한다. (마이드림 - 투두리스트)",
        operationId = "/v1/my-dream/todo/jobs"
    )
    @ApiErrorCode(TodoErrorCode.class)
    ResponseEntity<RestResponse<List<GetTodoJobResponseDto>>> getOneTodoGroup();


    @Operation(
        summary = "투두리스트 - 나의 직업별 투두리스트 조회 API",
        description = "직업별 투두리스트를 조회한다 (마이드림 - 투두리스트 - 개별 조회)",
        operationId = "/v1/my-dream/todo-group/{todoGroupId}"
    )
    @ApiErrorCode(TodoErrorCode.class)
    ResponseEntity<RestResponse<GetOneTodoGroupResponseDto>> getOneTodoGroup(
        @PathVariable Long todoGroupId);


    @Operation(
        summary = "투두리스트 - 개별 투두의 메모 조회 API",
        description = "개별 투두 아이템 메모를 조회한다 (마이드림 - 투두리스트 - 개별 투두 조회 - 투두 메모)",
        operationId = "/v1/my-dream/todo/{todoId}"
    )
    @ApiErrorCode(TodoErrorCode.class)
    ResponseEntity<RestResponse<GetOneTodoWithMemoResponseDto>> getOneTodoMemo(
        @PathVariable Long todoId);


    @Operation(
        summary = "투두리스트 - 개별 투두 삭제 API",
        description = "개별 투두 아이템 메모를 조회한다 (마이드림 - 투두리스트 - 개별 투두 조회 - 삭제)",
        operationId = "/v1/my-dream/todo/{todoId}"
    )
    @ApiErrorCode(TodoErrorCode.class)
    ResponseEntity<RestResponse<DeleteTodoResponseDto>> deleteOneTodo(
        @PathVariable Long todoId);


    @Operation(
        summary = "투두리스트 - 개별 투두 공개 상태 변경 API",
        description = "개별 투두 아이템의 공개 상태를 변경한다 (마이드림 - 투두리스트 - 개별 투두 조회 - 공개상태 변경)",
        operationId = "/v1/my-dream/todo/{todoId}/public-state"
    )
    @ApiErrorCode(TodoErrorCode.class)
    ResponseEntity<RestResponse<ChangePublicStateTodoResponseDto>> changeOneTodoPublicState(
        @PathVariable Long todoId);


    @Operation(
        summary = "투두리스트 - 개별 투두 완료 상태 변경 API",
        description = "개별 투두 아이템의 완료 상태를 변경한다 (마이드림 - 투두리스트 - 개별 투두 조회 - 완료상태 변경)",
        operationId = "/v1/my-dream/todo/{todoId}/complete-state"
    )
    @ApiErrorCode(TodoErrorCode.class)
    ResponseEntity<RestResponse<ChangeCompleteStateTodoResponseDto>> changeOneTodoCompleteState(
        @PathVariable Long todoId);

}