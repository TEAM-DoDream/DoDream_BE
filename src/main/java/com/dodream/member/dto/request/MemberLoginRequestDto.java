package com.dodream.member.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;

public record MemberLoginRequestDto(

    @NotBlank
    @Schema(description = "아이디", example = "dodream")
    String loginId,
    @NotBlank
    @Schema(description = "비밀번호", example = "hello2025")
    String password
){
}
