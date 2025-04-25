package com.dodream.member.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;

public record MemberLoginRequestDto(
    @Schema(description = "아이디", example = "dodream")
    String memberId,
    @Schema(description = "비밀번호", example = "hello2025")
    String password
){
}
