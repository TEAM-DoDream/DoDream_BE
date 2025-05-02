package com.dodream.member.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;

public record ChangeMemberRegionCodeRequestDto(

    @NotBlank
    @Schema(description = "새로운 거주지", example = "11012")
    String newRegionCode
) {

}
