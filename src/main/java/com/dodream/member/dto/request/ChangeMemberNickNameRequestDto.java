package com.dodream.member.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;

public record ChangeMemberNickNameRequestDto(
    @NotBlank
    @Schema(description = "새로운 닉네임", example = "두드림림")
    String newNickName
) {

}
