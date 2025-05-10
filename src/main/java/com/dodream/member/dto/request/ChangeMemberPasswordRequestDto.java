package com.dodream.member.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;

public record ChangeMemberPasswordRequestDto(

    @NotBlank
    @Schema(description = "비밀번호", example = "newPassword")
    String newPassword,
    @NotBlank
    @Schema(description = "비밀번호 확인", example = "newPassword")
    String newPasswordCheck

) {

}
