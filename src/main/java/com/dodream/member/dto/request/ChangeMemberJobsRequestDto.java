package com.dodream.member.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import java.util.List;

public record ChangeMemberJobsRequestDto(

    @Schema(description = "삭제할 관심 직업 id")
    List<Long> jobIds

) {

}
