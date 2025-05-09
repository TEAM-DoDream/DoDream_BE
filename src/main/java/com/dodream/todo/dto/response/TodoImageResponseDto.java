package com.dodream.todo.dto.response;

import com.dodream.todo.domain.MemoImage;
import io.swagger.v3.oas.annotations.media.Schema;

public record TodoImageResponseDto(

    @Schema(description = "이미지 id", example = "2")
    Long imageId,
    @Schema(description = "이미지 url", example = "www.kr.sdfsd.com/member/1/image.png")
    String imageUrl

) {

    public static TodoImageResponseDto from(MemoImage memoImage) {
        return new TodoImageResponseDto(memoImage.getId(), memoImage.getImageUrl());
    }

}
