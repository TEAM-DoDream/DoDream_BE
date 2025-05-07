package com.dodream.member.dto.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;

public record ChangeMemberBirthDateRequestDto(

    @NotNull
    @Schema(description = "새로운 생년월일", example = "2000/01/20", type = "string")
    @JsonFormat(shape = JsonFormat.Shape.STRING,pattern = "yyyy/MM/dd")
    LocalDate newBirthDate
) {

}
