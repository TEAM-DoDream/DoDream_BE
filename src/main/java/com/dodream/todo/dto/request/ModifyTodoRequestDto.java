package com.dodream.todo.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import java.util.List;
import org.springframework.web.multipart.MultipartFile;

public record ModifyTodoRequestDto(

    @NotNull
    @Schema(description = "투두 제목", example = "요양보호사 관련 자격증 찾아보기")
    String todoTitle,
    @NotNull
    @Schema(description = "공개여부", example = "true")
    Boolean isPublic,
    @Schema(description = "메모", example = "세상은 빠르게 변화하고 있고,우리는 그 속에서 다양한~")
    String memoText,
    @Schema(description = "링크 미리보기", example = "www.youtube~~")
    String link,
    @Schema(description = "새로운 이미지들")
    List<MultipartFile> newImages,
    @Schema(description = "삭제할 이미지 id 목록")
    List<Long> deleteImages

) {

}
