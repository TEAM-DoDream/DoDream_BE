package com.dodream.member.dto.request;

import com.dodream.member.domain.Gender;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;

public record MemberSignUpRequestDto(
    @NotBlank
    @Schema(description = "아이디", example = "dodream")
    String loginId,
    @NotBlank
    @Schema(description = "비밀번호", example = "hello2025")
    String password,
    @NotBlank
    @Schema(description = "닉네임", example = "두둠칫")
    String nickName,
    @NotNull
    @Schema(description = "생년월일", example = "2000/01/05", type = "string")
    @JsonFormat(shape = JsonFormat.Shape.STRING,pattern = "yyyy/MM/dd")
    LocalDate birthDate,
    @NotNull
    @Schema(description = "성별", example = "FEMALE")
    Gender gender,
    @Schema(description = "지역코드", example = "11110")
    String regionCode
) {
}
