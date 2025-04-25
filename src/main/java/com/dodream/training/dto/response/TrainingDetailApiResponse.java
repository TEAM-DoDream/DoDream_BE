package com.dodream.training.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;

public record TrainingDetailApiResponse(

        @JsonProperty("inst_base_info")
        InstBaseInfo instBaseInfo
) {
    public record InstBaseInfo(

            @JsonProperty("trprDegr")
            int trprDegr,

            @JsonProperty("trprNm")
            String trprNm,

            @JsonProperty("inoNm")
            String inoNm,

            @JsonProperty("ncsCd")
            String ncsCd,

            @JsonProperty("ncsNm")
            String ncsNm,

            @JsonProperty("trDcnt")
            int trDcnt,

            @JsonProperty("trtm")
            int trtm,

            @JsonProperty("torgParGrad")
            String torgParGrad,

            @JsonProperty("instPerTrco")
            int instPerTrco
    ){}
}
