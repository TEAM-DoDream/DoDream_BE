package com.dodream.job.controller;

import com.dodream.core.presentation.RestResponse;
import com.dodream.job.application.ClovaStudioService;
import com.dodream.job.controller.swagger.ClovaSwagger;
import com.dodream.job.dto.request.clova.ChatRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/clova")
@RequiredArgsConstructor
public class ClovaController implements ClovaSwagger {

    private final ClovaStudioService clovaStudioService;

    @PostMapping("/chat")
    public ResponseEntity<RestResponse<String>> getClovaResponse(
            @RequestBody ChatRequest chatRequest
    ){
        return ResponseEntity.ok(
            new RestResponse<>(
                clovaStudioService.chatCompletionService(
                        chatRequest.systemMessage(), chatRequest.userMessage()
                )
            )
        );
    }
}
