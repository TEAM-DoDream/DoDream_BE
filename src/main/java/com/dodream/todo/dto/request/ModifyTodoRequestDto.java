package com.dodream.todo.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import java.util.List;
import org.springframework.web.multipart.MultipartFile;

public record ModifyTodoRequestDto(

    @NotNull
    @Schema(description = "투두 제목", example = "요양보호사 관련 자격증 찾아보기")
    String todoTitle

) {

}
