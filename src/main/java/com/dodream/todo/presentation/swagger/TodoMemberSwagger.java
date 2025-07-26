package com.dodream.todo.presentation.swagger;

import com.dodream.core.config.swagger.ApiErrorCode;
import com.dodream.core.presentation.RestResponse;
import com.dodream.todo.dto.request.ModifyTodoRequestDto;
import com.dodream.todo.dto.request.PostTodoRequestDto;
import com.dodream.todo.dto.response.AddJobTodoResponseDto;
import com.dodream.todo.dto.response.ChangeCompleteStateTodoResponseDto;
import com.dodream.todo.dto.response.DeleteTodoResponseDto;
import com.dodream.todo.dto.response.GetOneTodoGroupAtHomeResponseDto;
import com.dodream.todo.dto.response.GetOneTodoGroupResponseDto;
import com.dodream.todo.dto.response.ModifyTodoResponseDto;
import com.dodream.todo.dto.response.PostTodoResponseDto;
import com.dodream.todo.exception.TodoErrorCode;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
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
        summary = "나의 할일(투두리스트) 조회 API",
        description = "나의 할일 - 할일 목록을 조회한다.",
        operationId = "/v1/my-dream/todo"
    )
    @ApiErrorCode(TodoErrorCode.class)
    ResponseEntity<RestResponse<GetOneTodoGroupResponseDto>> getOneTodoGroupAtMyDream();


    @Operation(
        summary = "새로운 투두 생성 API",
        description = "투두리스트에 새로운 투두를 생성한다. (나의 할일, 플로팅 버튼)",
        operationId = "/v1/my-dream/todo-group"
    )
    @ApiErrorCode(TodoErrorCode.class)
    ResponseEntity<RestResponse<PostTodoResponseDto>> postNewTodo(
        @RequestBody @Valid PostTodoRequestDto postTodoRequestDto);

    @Operation(
        summary = "기존 투두 수정 API",
        description = "기존 투두를 수정한다.",
        operationId = "/v1/my-dream/todo/{todoId}"
    )
    @ApiErrorCode(TodoErrorCode.class)
    ResponseEntity<RestResponse<ModifyTodoResponseDto>> modifyTodo(
        @PathVariable Long todoId,
        @RequestBody @Valid ModifyTodoRequestDto modifyTodoRequestDto);


    @Operation(
        summary = "직업 담기 API",
        description = "해당 직업을 담는다.",
        operationId = "/v1/my-dream/job/{jobId}"
    )
    @ApiErrorCode(TodoErrorCode.class)
    ResponseEntity<RestResponse<AddJobTodoResponseDto>> addJobToMyTodoList(
        @PathVariable Long jobId);



    @Operation(
        summary = "개별 투두 삭제 API",
        description = "개별 투두 항목을 삭제한다.",
        operationId = "/v1/my-dream/todo/{todoId}"
    )
    @ApiErrorCode(TodoErrorCode.class)
    ResponseEntity<RestResponse<DeleteTodoResponseDto>> deleteOneTodo(
        @PathVariable Long todoId);


    @Operation(
        summary = "개별 투두 완료 상태 변경 API",
        description = "개별 투두 아이템의 완료 상태를 변경한다.",
        operationId = "/v1/my-dream/todo/{todoId}/complete-state"
    )
    @ApiErrorCode(TodoErrorCode.class)
    ResponseEntity<RestResponse<ChangeCompleteStateTodoResponseDto>> changeOneTodoCompleteState(
        @PathVariable Long todoId);

}