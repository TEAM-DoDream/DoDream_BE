package com.dodream.member.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;

public record UploadMemberProfileImageResponseDto(
    @Schema(description = "변경된 프로필 이미지 url", example = "www.kr.sdfsd.com/member/1/image.png")
    String imageUrl
) {

    public static UploadMemberProfileImageResponseDto from(String imageUrl) {
        return new UploadMemberProfileImageResponseDto(imageUrl);
    }
}
