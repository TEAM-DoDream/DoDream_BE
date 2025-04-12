package com.dodream.core.presentation.controller;

import com.dodream.core.presentation.RestResponse;
import com.dodream.core.presentation.swagger.TestSwagger;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/test")
public class TestController implements TestSwagger {
    @Override
    @GetMapping("/health")
    public ResponseEntity<RestResponse<String>> healthCheck() {
        return ResponseEntity.ok(new RestResponse<>("OK"));
    }
}
