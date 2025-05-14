package com.dodream.todo.presentation.controller;

import com.dodream.core.presentation.RestResponse;
import com.dodream.todo.application.TodoService;
import com.dodream.todo.dto.response.GetOthersTodoGroupResponseDto;
import com.dodream.todo.presentation.swagger.TodoSwagger;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/todo")
@RequiredArgsConstructor
@Validated
public class TodoController implements TodoSwagger {

    private final TodoService todoService;

    @Override
    @GetMapping(value = "")
    public ResponseEntity<RestResponse<List<GetOthersTodoGroupResponseDto>>> getOneTodoGroupAtHome() {
        return ResponseEntity.ok(
            new RestResponse<>(todoService.getOneTodoGroupAtHome()));
    }

}
