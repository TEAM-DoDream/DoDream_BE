package com.dodream.member.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import java.time.LocalDate;

public record ChangeMemberBirthDateResponseDto(
    @Schema(description = "멤버 id(DB상)", example = "1")
    Long memberId,
    @Schema(description = "새로운 생년월일", example = "2001/01/05", type = "string")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy/MM/dd")
    LocalDate newBirthDate,
    @Schema(description = "메세지", example = "생년월일 변경 완료")
    String message

) {

    public static ChangeMemberBirthDateResponseDto of(Long memberId, LocalDate newBirthDate) {
        return new ChangeMemberBirthDateResponseDto(memberId, newBirthDate, "생년월일 변경 완료");
    }

}
