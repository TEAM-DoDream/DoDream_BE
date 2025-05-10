package com.dodream.ncs.presentation.controller;

import com.dodream.core.presentation.RestResponse;
import com.dodream.ncs.application.NcsService;
import com.dodream.ncs.dto.response.NcsResponseDto;
import com.dodream.ncs.presentation.swagger.NcsSwagger;
import io.swagger.v3.oas.annotations.Parameter;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
@RequestMapping("/v1/ncs")
@RequiredArgsConstructor
@Validated
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
            @Parameter(name = "name", description = "직무 이름 (예시: 철도차량제작)", example = "화학·바이오공통")
            @NotBlank(message = "직무 이름은 필수입니다")
            @PathVariable String name
    ) {
        return ResponseEntity.ok(new RestResponse<>(ncsService.getNcsByNcsName(name)));
    }

    @Override
    @GetMapping("/code/{code}")
    public ResponseEntity<RestResponse<NcsResponseDto>> getNcsByCode(
            @Parameter(name = "code",
                    description = "NCS 직무 코드 -> 대분류(2자리 정수), 중분류(4자리 정수), " +
                            "소분류(6자리 정수), 세분류(8자리 정수)",
                    example = "01"
            )
            @Pattern(regexp = "^(\\d{2}|\\d{4}|\\d{6}|\\d{8})$", message = "2,4,6,8자리 정수를 입력해 주세요")
            @PathVariable String code
    ) {
        return ResponseEntity.ok(new RestResponse<>(ncsService.getNcsByNcsCode(code)));
    }
}
