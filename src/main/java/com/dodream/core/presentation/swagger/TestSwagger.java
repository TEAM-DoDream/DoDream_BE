package com.dodream.core.presentation.swagger;

import com.dodream.core.presentation.RestResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;

@Tag(name = "Test", description = "서버 테스트용 컨트롤러")
public interface TestSwagger {
    @Operation(
            summary = "Health Check API",
            description = "현재 서버가 가동중인지 확인합니다",
            operationId = "test/health"
    )
    ResponseEntity<RestResponse<String>> healthCheck();

    @Operation(
            summary = "DB Test API",
            description = "현재 데이터 베이스가 연결 되어 있는지 확인합니다.",
            operationId = "test/db"
    )
    ResponseEntity<RestResponse<String>> dbHealthCheck();
}
