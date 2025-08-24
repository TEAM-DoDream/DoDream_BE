package com.dodream.todo.presentation.controller;

import com.dodream.core.infrastructure.security.CustomUserDetails;
import com.dodream.core.presentation.RestResponse;
import com.dodream.todo.application.TodoCommunityService;
import com.dodream.todo.dto.response.OtherTodoSaveResponseDto;
import com.dodream.todo.dto.response.TodoCommunityResponseDto;
import com.dodream.todo.presentation.swagger.TodoCommunitySwagger;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Slice;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/community")
@RequiredArgsConstructor
public class TodoCommunityController implements TodoCommunitySwagger {

    private final TodoCommunityService todoCommunityService;

    @Override
    @GetMapping("/todos/popular")
    public ResponseEntity<RestResponse<List<TodoCommunityResponseDto>>> getTop5Todos(
            @RequestParam String jobName
    ) {
        return ResponseEntity.ok(
                new RestResponse<>(todoCommunityService.findTop5TodosBySaveCount(jobName))
        );
    }

    @Override
    @GetMapping("/todos")
    public ResponseEntity<RestResponse<Slice<TodoCommunityResponseDto>>> getTodos(
            @RequestParam String jobName,
            @RequestParam String level,
            @RequestParam String sort,
            @RequestParam int page,
            @RequestParam int size
    ) {
        return ResponseEntity.ok(
                new RestResponse<>(todoCommunityService.searchTodoList(
                        jobName, level, sort, page, size
                ))
        );
    }

    @Override
    @PostMapping("/todos/save/{id}")
    public ResponseEntity<RestResponse<OtherTodoSaveResponseDto>> saveOtherTodo(
            @AuthenticationPrincipal CustomUserDetails customUserDetails,
            @PathVariable Long id
    ) {
        return ResponseEntity.ok(
                new RestResponse<>(
                        todoCommunityService.saveOtherTodo(
                            customUserDetails, id
                        )
                )
        );
    }
}
