package com.dodream.training.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;

public record TrainingDetailApiResponse(

        @JsonProperty("inst_base_info")
        InstBaseInfo instBaseInfo
) {
    public record InstBaseInfo(

            @JsonProperty("trprDegr")
            @Schema(description = "훈련 차수", example = "3")
            int trprDegr,

            @JsonProperty("trprNm")
            @Schema(description = "훈련 과정 이름", example = "요양보호사 자격증 취득 과정 (사회복지사)")
            String trprNm,

            @JsonProperty("inoNm")
            @Schema(description = "훈련 기관 이름", example = "느티나무요양보호사교육원")
            String inoNm,

            @JsonProperty("ncsCd")
            @Schema(description = "ncs 직무 코드", example = "06010108")
            String ncsCd,

            @JsonProperty("ncsNm")
            @Schema(description = "ncs 직무 이름", example = "요양 지원")
            String ncsNm,

            @JsonProperty("trDcnt")
            @Schema(description = "총 훈련 일수", example = "12")
            int trDcnt,

            @JsonProperty("trtm")
            @Schema(description = "총 훈련 시간", example = "50")
            int trtm,

            @JsonProperty("torgParGrad")
            @Schema(description = "평가 등급", example = "3")
            String torgParGrad,

            @JsonProperty("instPerTrco")
            @Schema(description = "실제 훈련비", example = "250000")
            int instPerTrco
    ){}
}
