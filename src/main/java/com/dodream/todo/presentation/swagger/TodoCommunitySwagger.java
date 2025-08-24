package com.dodream.todo.presentation.swagger;

import com.dodream.core.infrastructure.security.CustomUserDetails;
import com.dodream.core.presentation.RestResponse;
import com.dodream.todo.dto.response.OtherTodoSaveResponseDto;
import com.dodream.todo.dto.response.TodoCommunityResponseDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.data.domain.Slice;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Tag(name = "Community", description = "투두 커뮤니티 관련 컨트롤러(투두 커뮤니티 페이지에서 사용)")
public interface TodoCommunitySwagger {

    @Operation(
            summary = "커뮤니티 - 직업별 Hot 할일 목록 조회하기",
            description = "커뮤니티에서 직업별 Hot 할일 목록을 조회합니다. 5개 조회됩니다.",
            operationId = "/v1/community/todos/popular"
    )
    ResponseEntity<RestResponse<List<TodoCommunityResponseDto>>> getTop5Todos(
            @RequestParam String jobName
    );

    @Operation(
            summary = "커뮤니티 - 직업별 전체 투두 목록 조회하기",
            description = "커뮤니티에서 직업별 투두 목록을 조회합니다. 무한스크롤 방식으로 구현했습니다.",
            operationId = "/v1/community/todos"
    )
    ResponseEntity<RestResponse<Slice<TodoCommunityResponseDto>>> getTodos(
            @AuthenticationPrincipal CustomUserDetails customUserDetails,
            @RequestParam String jobName,
            @RequestParam String level,
            @RequestParam String sort,
            @RequestParam int page,
            @RequestParam int size
    );

    @Operation(
            summary = "커뮤니티 - 다른 사람의 투두 저장 하기",
            description = "커뮤니티에서 다른 사람의 투두를 저장합니다.",
            operationId = "/v1/community/todos/{id}"
    )
    ResponseEntity<RestResponse<OtherTodoSaveResponseDto>> saveOtherTodo(
            @AuthenticationPrincipal CustomUserDetails customUserDetails,
            @PathVariable Long id
    );

    @Operation(
            summary = "커뮤니티 - 다른 사람의 투두 저장 취소 하기",
            description = "커뮤니티에서 다른 사람의 투두를 저장을 취소합니다.",
            operationId = "/v1/community/todos/{id}"
    )
    ResponseEntity<RestResponse<Boolean>> cancelotherTodo(
            @AuthenticationPrincipal CustomUserDetails customUserDetails,
            @PathVariable Long id
    );
}
