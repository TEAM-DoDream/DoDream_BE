package com.dodream.todo.presentation.controller;

import com.dodream.core.presentation.RestResponse;
import com.dodream.todo.application.MemberTodoService;
import com.dodream.todo.dto.response.AddJobTodoResponseDto;
import com.dodream.todo.presentation.swagger.MemberTodoSwagger;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/my-dream")
@RequiredArgsConstructor
@Validated
public class MemberTodoController implements MemberTodoSwagger {

    private final MemberTodoService memberTodoService;

    @Override
    @PostMapping(value = "/job/{jobId}")
    public ResponseEntity<RestResponse<AddJobTodoResponseDto>> addJobToMyTodoList(
        @PathVariable("jobId")Long jobId){
        return ResponseEntity.ok(
            new RestResponse<>(memberTodoService.addJobToMyList(jobId)));
    }

}
