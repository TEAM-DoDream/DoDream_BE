package com.dodream.member.dto.response;

public record UploadMemberProfileImageResponseDto(
    String imageUrl
) {

    public static UploadMemberProfileImageResponseDto from(String imageUrl) {
        return new UploadMemberProfileImageResponseDto(imageUrl);
    }
}
