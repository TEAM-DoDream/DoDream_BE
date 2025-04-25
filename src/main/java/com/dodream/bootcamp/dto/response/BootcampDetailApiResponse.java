package com.dodream.bootcamp.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;

public record BootcampDetailApiResponse(
        @JsonProperty("trprDegr") int trprDegr,
        @JsonProperty("trprNm") String trprNm,
        @JsonProperty("inoNm") String inoNm,
        @JsonProperty("ncsCd") String ncsCd,
        @JsonProperty("ncsNm") String ncsNm,
        @JsonProperty("trDcnt") int trDcnt,
        @JsonProperty("trtm") int trtm,
        @JsonProperty("torgParGrad") String torgParGrad,
        @JsonProperty("instPerTrco") int instPerTrco
) {
}
