package com.dodream.training.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;

public record TrainingDetailApiResponse(

        @JsonProperty("inst_base_info")
        InstBaseInfo instBaseInfo,

        @JsonProperty("inst_detail_info")
        InstDetailInfo instDetailInfo
) {
    public record InstBaseInfo(
            @JsonProperty("hpAddr")
            @Schema(description = "홈페이지 주소", example = "http://~")
            String hpAddr,

            @JsonProperty("addr1")
            @Schema(description = "주소지", example = "경기도 안양시 만안구")
            String addr,

            @JsonProperty("trprDegr")
            @Schema(description = "훈련 차수", example = "3")
            int trprDegr,

            @JsonProperty("trprNm")
            @Schema(description = "훈련 과정 이름", example = "요양보호사 자격증 취득 과정 (사회복지사)")
            String trprNm,

            @JsonProperty("inoNm")
            @Schema(description = "훈련 기관 이름", example = "느티나무요양보호사교육원")
            String inoNm,

            @JsonProperty("instPerTrco")
            @Schema(description = "실제 훈련비", example = "250000")
            int instPerTrco
    ){}

    public record InstDetailInfo(
            @JsonProperty("totalCrsAt")
            String totalCrsAt
    ){}
}
