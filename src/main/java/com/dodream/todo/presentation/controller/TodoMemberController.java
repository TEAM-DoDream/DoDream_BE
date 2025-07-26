package com.dodream.todo.presentation.controller;

import com.dodream.core.presentation.RestResponse;
import com.dodream.todo.application.TodoMemberService;
import com.dodream.todo.dto.request.ModifyTodoRequestDto;
import com.dodream.todo.dto.request.PostTodoRequestDto;
import com.dodream.todo.dto.response.AddJobTodoResponseDto;
import com.dodream.todo.dto.response.ChangeCompleteStateTodoResponseDto;
import com.dodream.todo.dto.response.DeleteTodoResponseDto;
import com.dodream.todo.dto.response.GetOneTodoGroupAtHomeResponseDto;
import com.dodream.todo.dto.response.GetOneTodoGroupResponseDto;
import com.dodream.todo.dto.response.ModifyTodoResponseDto;
import com.dodream.todo.dto.response.PostTodoResponseDto;
import com.dodream.todo.presentation.swagger.TodoMemberSwagger;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
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
    @GetMapping(value = "/todo/home")
    public ResponseEntity<RestResponse<GetOneTodoGroupAtHomeResponseDto>> getOneTodoGroupAtHome() {
        return ResponseEntity.ok(
            new RestResponse<>(memberTodoService.getOneTodoGroupAtHome()));
    }

    @Override
    @GetMapping(value = "/todo")
    public ResponseEntity<RestResponse<GetOneTodoGroupResponseDto>> getOneTodoGroupAtMyDream() {
        return ResponseEntity.ok(
            new RestResponse<>(memberTodoService.getOneTodoGroupAtMyDream()));
    }

    @Override
    @PostMapping(value = "/todo-group/{todoGroupId}")
    public ResponseEntity<RestResponse<PostTodoResponseDto>> postNewTodo(
        @PathVariable Long todoGroupId, @RequestBody PostTodoRequestDto postTodoRequestDto) {
        return ResponseEntity.ok(
            new RestResponse<>(memberTodoService.postNewTodo(todoGroupId, postTodoRequestDto)));
    }

    @Override
    @PutMapping(value = "/todo/{todoId}")
    public ResponseEntity<RestResponse<ModifyTodoResponseDto>> modifyTodo(
        @PathVariable Long todoId, @RequestBody ModifyTodoRequestDto modifyTodoRequestDto) {
        return ResponseEntity.ok(
            new RestResponse<>(memberTodoService.modifyTodo(todoId, modifyTodoRequestDto)));
    }

    @Override
    @PostMapping(value = "/job/{jobId}")
    public ResponseEntity<RestResponse<AddJobTodoResponseDto>> addJobToMyTodoList(
        @PathVariable Long jobId) {
        return ResponseEntity.ok(
            new RestResponse<>(memberTodoService.addJobToMyList(jobId)));
    }


    @Override
    @DeleteMapping(value = "/todo/{todoId}")
    public ResponseEntity<RestResponse<DeleteTodoResponseDto>> deleteOneTodo(
        @PathVariable Long todoId) {
        return ResponseEntity.ok(
            new RestResponse<>(memberTodoService.deleteOneTodo(todoId)));
    }

    @Override
    @PatchMapping(value = "/todo/{todoId}/complete-state")
    public ResponseEntity<RestResponse<ChangeCompleteStateTodoResponseDto>> changeOneTodoCompleteState(
        @PathVariable Long todoId) {
        return ResponseEntity.ok(
            new RestResponse<>(memberTodoService.changeOneTodoCompleteState(todoId)));
    }
}
