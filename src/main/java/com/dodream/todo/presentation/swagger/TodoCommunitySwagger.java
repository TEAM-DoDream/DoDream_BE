package com.dodream.todo.presentation.swagger;

import com.dodream.core.config.swagger.ApiErrorCode;
import com.dodream.core.infrastructure.security.CustomUserDetails;
import com.dodream.core.presentation.RestResponse;
import com.dodream.todo.dto.response.OtherTodoSaveResponseDto;
import com.dodream.todo.dto.response.TodoCommunityResponseDto;
import com.dodream.todo.exception.TodoErrorCode;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
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
            summary = "커뮤니티 - 초기 필터 설정",
            description = "커뮤니티 페이지 초기 렌더링시 직업 필터의 이름을 결정합니다." +
                    "<br>토큰 넣는 경우 유저가 설정한 직업으로, 아닌 경우 저장 횟수가 가장 많은 직업으로 설정",
            operationId = "/v1/community/job"
    )
    ResponseEntity<RestResponse<String>> getFirstRenderingFilter(
            @AuthenticationPrincipal CustomUserDetails customUserDetails
    );

    @Operation(
            summary = "커뮤니티 - 직업별 Hot 할일 목록 조회하기",
            description = "커뮤니티에서 직업별 Hot 할일 목록을 조회합니다. 5개 조회됩니다." +
                    "<br>토큰을 넣을 필요는 없음",
            operationId = "/v1/community/todos/popular"
    )
    ResponseEntity<RestResponse<List<TodoCommunityResponseDto>>> getTop5Todos(
            @RequestParam @Parameter(description = "직업 이름", example = "요양보호사") String jobName
    );

    @Operation(
            summary = "커뮤니티 - 직업별 전체 투두 목록 조회하기",
            description = "커뮤니티에서 직업별 투두 목록을 조회합니다. 무한스크롤 방식으로 구현했습니다." +
                    "<br>로그인한 경우 토큰 필수, 로그인 안한 경우 토큰 x",
            operationId = "/v1/community/todos"
    )
    ResponseEntity<RestResponse<Slice<TodoCommunityResponseDto>>> getTodos(
            @AuthenticationPrincipal CustomUserDetails customUserDetails,
            @RequestParam @Parameter(description = "직업 이름", example = "요양보호사") String jobName,
            @RequestParam @Parameter(description = "유저 단계", example = "1단계: 씨앗") String level,
            @RequestParam @Parameter(description = "정렬 방식", example = "최신순") String sort,
            @RequestParam int page,
            @RequestParam int size
    );

    @Operation(
            summary = "커뮤니티 - 다른 사람의 투두 저장 하기",
            description = "커뮤니티에서 다른 사람의 투두를 저장합니다." +
                    "<br>토큰 필수",
            operationId = "/v1/community/todos/{id}"
    )
    @ApiErrorCode(TodoErrorCode.class)
    ResponseEntity<RestResponse<OtherTodoSaveResponseDto>> saveOtherTodo(
            @AuthenticationPrincipal CustomUserDetails customUserDetails,
            @PathVariable @Parameter(description = "투두 자체 id (투두 식별자)") Long id
    );

    @Operation(
            summary = "커뮤니티 - 다른 사람의 투두 저장 취소 하기",
            description = "커뮤니티에서 다른 사람의 투두를 저장을 취소합니다." +
                    "<br>토큰 필수",
            operationId = "/v1/community/todos/{id}"
    )
    ResponseEntity<RestResponse<Boolean>> cancelotherTodo(
            @AuthenticationPrincipal CustomUserDetails customUserDetails,
            @PathVariable @Parameter(description = "투두 자체 id (투두 식별자)") Long id
    );
}
