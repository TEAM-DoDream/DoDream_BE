package com.dodream.bootcamp.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public record BootcampListApiResponse(
        @JsonProperty("scn_cnt")
        int scnCnt,

        @JsonProperty("pageNum")
        String pageNum,

        @JsonProperty("pageSize")
        String pageSize,

        @JsonProperty("srchList")
        List<BootcampItem> srchList
) {
    public record BootcampItem(
            @JsonProperty("address")
            String address,

            @JsonProperty("courseMan")
            String courseMan,

            @JsonProperty("eiEmplRate3")
            String eiEmplRate3,

            @JsonProperty("realMan")
            String realMan,

            @JsonProperty("subTitle")
            String subTitle,

            @JsonProperty("title")
            String title,

            @JsonProperty("titleLink")
            String titleLink,

            @JsonProperty("traEndDate")
            String traEndDate,

            @JsonProperty("traStartDate")
            String traStartDate,

            @JsonProperty("trainstCstId")
            String trainstCstId,

            @JsonProperty("trngAreaCd")
            String trngAreaCd,

            @JsonProperty("trprDegr")
            String trprDegr,

            @JsonProperty("trprId")
            String trprId
    ) {}
}

