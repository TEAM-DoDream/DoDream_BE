package com.dodream.member.dto.response;

import com.dodream.region.domain.Region;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

@Builder
public record ChangeMemberRegionResponseDto(
    @Schema(description = "멤버 id(DB상)", example = "1")
    Long memberId,
    @Schema(description = "새로운 거주지 지역이름", example = "서울시 용산구")
    String newRegionName,
    @Schema(description = "새로운 거주지 지역코드", example = "11170")
    String newRegionCode,
    @Schema(description = "메세지", example = "거주지 변경 완료")
    String message

) {

    public static ChangeMemberRegionResponseDto of(Long memberId, Region region) {
        return ChangeMemberRegionResponseDto.builder()
            .memberId(memberId)
            .newRegionName(region.getRegionName())
            .newRegionCode(region.getRegionCode())
            .message("거주지 변경 완료")
            .build();
    }
}
