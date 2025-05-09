package com.dodream.todo.presentation.controller;

import com.dodream.core.presentation.RestResponse;
import com.dodream.todo.application.TodoMemberService;
import com.dodream.todo.dto.response.AddJobTodoResponseDto;
import com.dodream.todo.dto.response.DeleteTodoResponseDto;
import com.dodream.todo.dto.response.GetOneTodoGroupResponseDto;
import com.dodream.todo.dto.response.GetOneTodoWithMemoResponseDto;
import com.dodream.todo.presentation.swagger.TodoMemberSwagger;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/my-dream")
@RequiredArgsConstructor
@Validated
public class TodoMemberController implements TodoMemberSwagger {

    private final TodoMemberService memberTodoService;

    @Override
    @PostMapping(value = "/job/{jobId}")
    public ResponseEntity<RestResponse<AddJobTodoResponseDto>> addJobToMyTodoList(
        @PathVariable Long jobId) {
        return ResponseEntity.ok(
            new RestResponse<>(memberTodoService.addJobToMyList(jobId)));
    }

    @Override
    @GetMapping(value = "/todo/{todoGroupId}")
    public ResponseEntity<RestResponse<GetOneTodoGroupResponseDto>> getOneTodoGroup(
        @PathVariable Long todoGroupId) {
        return ResponseEntity.ok(
            new RestResponse<>(memberTodoService.getTodoGroup(todoGroupId)));
    }

    @Override
    @GetMapping(value = "/todo/{todoGroupId}/{todoId}")
    public ResponseEntity<RestResponse<GetOneTodoWithMemoResponseDto>> getOneTodoMemo(
        @PathVariable Long todoGroupId, @PathVariable Long todoId) {
        return ResponseEntity.ok(
            new RestResponse<>(memberTodoService.getOneTodoWithMemo(todoId)));
    }

    @Override
    @DeleteMapping(value = "/todo/{todoGroupId}/{todoId}")
    public ResponseEntity<RestResponse<DeleteTodoResponseDto>> deleteOneTodo(
        @PathVariable Long todoGroupId, @PathVariable Long todoId) {
        return ResponseEntity.ok(
            new RestResponse<>(memberTodoService.deleteOneTodo(todoId)));
    }

}
