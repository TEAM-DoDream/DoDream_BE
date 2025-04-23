package com.dodream.ncs.presentation.controller;

import com.dodream.core.presentation.RestResponse;
import com.dodream.ncs.application.NcsService;
import com.dodream.ncs.dto.response.NcsResponseDto;
import com.dodream.ncs.presentation.swagger.NcsSwagger;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
@RequestMapping("/v1/ncs")
@RequiredArgsConstructor
public class NcsController implements NcsSwagger {

    private final NcsService ncsService;

    @Override
    @GetMapping("/all")
    public ResponseEntity<RestResponse<List<NcsResponseDto>>> getAllNcs() {
        return ResponseEntity.ok(new RestResponse<>(ncsService.getAllNcs()));
    }

    @Override
    @GetMapping("/name/{name}")
    public ResponseEntity<RestResponse<NcsResponseDto>> getNcsByName(
            @PathVariable String name
    ) {
        return ResponseEntity.ok(new RestResponse<>(ncsService.getNcsByNcsName(name)));
    }

    @Override
    @GetMapping("/code/{code}")
    public ResponseEntity<RestResponse<NcsResponseDto>> getNcsByCode(
            @PathVariable String code
    ) {
        return ResponseEntity.ok(new RestResponse<>(ncsService.getNcsByNcsCode(code)));
    }
}
