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
    @GetMapping("/job")
    public ResponseEntity<RestResponse<String>> getFirstRenderingFilter(
            @AuthenticationPrincipal CustomUserDetails customUserDetails
    ) {
        return ResponseEntity.ok(
                new RestResponse<>(
                    todoCommunityService.getJobFilterName(customUserDetails)
                )
        );
    }

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
            @AuthenticationPrincipal CustomUserDetails customUserDetails,
            @RequestParam String jobName,
            @RequestParam String level,
            @RequestParam String sort,
            @RequestParam int page,
            @RequestParam int size
    ) {
        if(customUserDetails == null) {
            return ResponseEntity.ok(
                    new RestResponse<>(todoCommunityService.searchTodoList(
                            null, jobName, level, sort, page, size
                    ))
            );
        }
        else{
            return ResponseEntity.ok(
                    new RestResponse<>(todoCommunityService.searchTodoList(
                            customUserDetails.getId(), jobName, level, sort, page, size
                    ))
            );
        }
    }

    @Override
    @PostMapping("/todos/{id}")
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

    @Override
    @DeleteMapping("/todos/{id}")
    public ResponseEntity<RestResponse<Boolean>> cancelotherTodo(
            @AuthenticationPrincipal CustomUserDetails customUserDetails,
            @PathVariable Long id
    ) {
        return ResponseEntity.ok(
                new RestResponse<>(
                        todoCommunityService.cancelSaveOtherTodo(
                                customUserDetails, id
                        )
                )
        );
    }
}
