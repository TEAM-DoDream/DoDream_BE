package com.dodream.todo.dto.request;

import com.dodream.job.domain.TodoCategory;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.util.List;
import org.springframework.web.multipart.MultipartFile;

public record PostTodoRequestDto(
    @NotNull
    @Schema(description = "투두 제목", example = "요양보호사 관련 자격증 찾아보기")
    String todoTitle,
    @NotNull
    @Schema(description = "공개여부", example = "true")
    Boolean isPublic,
    @Schema(description = "메모", example = "세상은 빠르게 변화하고 있고,우리는 그 속에서 다양한~")
    String memoText,
    @Schema(description = "첨부 이미지들")
    List<MultipartFile> images

) {

}
