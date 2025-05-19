package com.dodream.todo.presentation.controller;

import com.dodream.core.presentation.RestResponse;
import com.dodream.todo.application.TodoMemberService;
import com.dodream.todo.dto.request.PostTodoRequestDto;
import com.dodream.todo.dto.response.AddJobTodoResponseDto;
import com.dodream.todo.dto.response.ChangeCompleteStateTodoResponseDto;
import com.dodream.todo.dto.response.ChangePublicStateTodoResponseDto;
import com.dodream.todo.dto.response.DeleteTodoResponseDto;
import com.dodream.todo.dto.response.GetOneTodoGroupAtHomeResponseDto;
import com.dodream.todo.dto.response.GetOneTodoGroupResponseDto;
import com.dodream.todo.dto.response.GetOneTodoWithMemoResponseDto;
import com.dodream.todo.dto.response.GetTodoJobResponseDto;
import com.dodream.todo.dto.response.PostTodoResponseDto;
import com.dodream.todo.presentation.swagger.TodoMemberSwagger;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/my-dream")
@RequiredArgsConstructor
@Validated
public class TodoMemberController implements TodoMemberSwagger {

    private final TodoMemberService memberTodoService;

    @Override
    @GetMapping(value = "/todo")
    public ResponseEntity<RestResponse<GetOneTodoGroupAtHomeResponseDto>> getOneTodoGroupAtHome() {
        return ResponseEntity.ok(
            new RestResponse<>(memberTodoService.getOneTodoGroupAtHome()));
    }

    @Override
    @PostMapping(value = "/todo-group/{todoGroupId}")
    public ResponseEntity<RestResponse<PostTodoResponseDto>> postNewTodo(
        @PathVariable Long todoGroupId, @RequestBody PostTodoRequestDto postTodoRequestDto) {
        return ResponseEntity.ok(
            new RestResponse<>(memberTodoService.postNewTodo(todoGroupId, postTodoRequestDto)));
    }

    @Override
    @PostMapping(value = "/job/{jobId}")
    public ResponseEntity<RestResponse<AddJobTodoResponseDto>> addJobToMyTodoList(
        @PathVariable Long jobId) {
        return ResponseEntity.ok(
            new RestResponse<>(memberTodoService.addJobToMyList(jobId)));
    }

    @Override
    @GetMapping(value = "/todo/jobs")
    public ResponseEntity<RestResponse<List<GetTodoJobResponseDto>>> getOneTodoGroup() {
        return ResponseEntity.ok(
            new RestResponse<>(memberTodoService.getTodoJobList()));
    }

    @Override
    @GetMapping(value = "/todo-group/{todoGroupId}")
    public ResponseEntity<RestResponse<GetOneTodoGroupResponseDto>> getOneTodoGroup(
        @PathVariable Long todoGroupId) {
        return ResponseEntity.ok(
            new RestResponse<>(memberTodoService.getOneTodoGroup(todoGroupId)));
    }

    @Override
    @GetMapping(value = "/todo/{todoId}")
    public ResponseEntity<RestResponse<GetOneTodoWithMemoResponseDto>> getOneTodoMemo(
        @PathVariable Long todoId) {
        return ResponseEntity.ok(
            new RestResponse<>(memberTodoService.getOneTodoWithMemo(todoId)));
    }

    @Override
    @DeleteMapping(value = "/todo/{todoId}")
    public ResponseEntity<RestResponse<DeleteTodoResponseDto>> deleteOneTodo(
        @PathVariable Long todoId) {
        return ResponseEntity.ok(
            new RestResponse<>(memberTodoService.deleteOneTodo(todoId)));
    }

    @Override
    @PatchMapping(value = "/todo/{todoId}/public-state")
    public ResponseEntity<RestResponse<ChangePublicStateTodoResponseDto>> changeOneTodoPublicState(
        @PathVariable Long todoId) {
        return ResponseEntity.ok(
            new RestResponse<>(memberTodoService.changeOneTodoPublicState(todoId)));
    }

    @Override
    @PatchMapping(value = "/todo/{todoId}/complete-state")
    public ResponseEntity<RestResponse<ChangeCompleteStateTodoResponseDto>> changeOneTodoCompleteState(
        @PathVariable Long todoId) {
        return ResponseEntity.ok(
            new RestResponse<>(memberTodoService.changeOneTodoCompleteState(todoId)));
    }
}
