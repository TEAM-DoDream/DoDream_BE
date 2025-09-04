package com.dodream.todo.presentation.controller;

import com.dodream.core.infrastructure.security.CustomUserDetails;
import com.dodream.core.presentation.RestResponse;
import com.dodream.todo.application.TodoService;
import com.dodream.todo.dto.response.GetOnePopularTodoGroupResponseDto;
import com.dodream.todo.dto.response.GetOneTodoGroupResponseDto;
import com.dodream.todo.dto.response.GetOneTodoWithMemoResponseDto;
import com.dodream.todo.dto.response.GetOthersTodoGroupResponseDto;
import com.dodream.todo.dto.response.GetPopularTodoDescriptionDto;
import com.dodream.todo.presentation.swagger.TodoSwagger;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/todo")
@RequiredArgsConstructor
@Validated
public class TodoController implements TodoSwagger {

    private final TodoService todoService;

    @Override
    @GetMapping(value = "/other")
    public ResponseEntity<RestResponse<List<GetOthersTodoGroupResponseDto>>> getOneTodoGroupAtHome() {
        return ResponseEntity.ok(
            new RestResponse<>(todoService.getOneTodoGroupAtHome()));
    }

    @Override
    @GetMapping(value = "/other/public")
    public ResponseEntity<RestResponse<List<GetOthersTodoGroupResponseDto>>> getOneTodoGroupPublicAtHome() {
        return ResponseEntity.ok(
            new RestResponse<>(todoService.getOneTodoGroupPublicAtHome()));
    }

    @Override
    @GetMapping(value = "/other/simple/{jobId}")
    public ResponseEntity<RestResponse<List<GetOthersTodoGroupResponseDto>>> getOthersTodoSimpleByJob(
        @PathVariable Long jobId) {
        return ResponseEntity.ok(
            new RestResponse<>(todoService.getOthersTodoSimple(jobId)));
    }

    @Override
    @GetMapping(value = "/other/{jobId}")
    public ResponseEntity<RestResponse<Page<GetOthersTodoGroupResponseDto>>> getOthersTodoByJob(
        @PathVariable Long jobId, @RequestParam(defaultValue = "0") int page) {
        return ResponseEntity.ok(
            new RestResponse<>(todoService.getOthersTodoByJob(jobId, page)));
    }

    @Override
    @GetMapping(value = "/{todoGroupId}")
    public ResponseEntity<RestResponse<GetOneTodoGroupResponseDto>> getOneOthersTodoGroup(
        @PathVariable Long todoGroupId) {
        return ResponseEntity.ok(
            new RestResponse<>(todoService.getOneOthersTodoGroup(todoGroupId)));
    }

    @Override
    @GetMapping(value = "/popular")
      public ResponseEntity<RestResponse<List<GetOnePopularTodoGroupResponseDto>>> getPopularTodos(){
          return ResponseEntity.ok(
              new RestResponse<>(todoService.getPopularTodos()));
      }

    @Override
    @GetMapping("/floating/popular")
    public ResponseEntity<RestResponse<GetPopularTodoDescriptionDto>> getPopularTodo(
            @AuthenticationPrincipal CustomUserDetails customUserDetails
    ) {
        return ResponseEntity.ok(
                new RestResponse<>(todoService.getPopularTodoDescription(customUserDetails))
        );
    }
}
