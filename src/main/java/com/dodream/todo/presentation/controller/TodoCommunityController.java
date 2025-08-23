package com.dodream.todo.presentation.controller;

import com.dodream.core.presentation.RestResponse;
import com.dodream.todo.application.TodoCommunityService;
import com.dodream.todo.dto.response.TodoCommunityResponseDto;
import com.dodream.todo.presentation.swagger.TodoCommunitySwagger;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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
}
