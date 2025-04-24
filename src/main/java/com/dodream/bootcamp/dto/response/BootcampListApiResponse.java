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
            @JsonProperty("eiEmplCnt3Gt10")
            String eiEmplCnt3Gt10,

            @JsonProperty("eiEmplRate6")
            String eiEmplRate6,

            @JsonProperty("eiEmplCnt3")
            String eiEmplCnt3,

            @JsonProperty("eiEmplRate3")
            String eiEmplRate3,

            @JsonProperty("title")
            String title,

            @JsonProperty("realMan")
            String realMan,

            @JsonProperty("telNo")
            String telNo,

            @JsonProperty("stdgScor")
            String stdgScor,

            @JsonProperty("traStartDate")
            String traStartDate,

            @JsonProperty("grade")
            String grade,

            @JsonProperty("ncsCd")
            String ncsCd,

            @JsonProperty("regCourseMan")
            String regCourseMan,

            @JsonProperty("trprDegr")
            String trprDegr,

            @JsonProperty("address")
            String address,

            @JsonProperty("traEndDate")
            String traEndDate,

            @JsonProperty("subTitle")
            String subTitle,

            @JsonProperty("instCd")
            String instCd,

            @JsonProperty("trngAreaCd")
            String trngAreaCd,

            @JsonProperty("trprId")
            String trprId,

            @JsonProperty("yardMan")
            String yardMan,

            @JsonProperty("courseMan")
            String courseMan,

            @JsonProperty("trainTarget")
            String trainTarget,

            @JsonProperty("trainTargetCd")
            String trainTargetCd,

            @JsonProperty("trainstCstId")
            String trainstCstId,

            @JsonProperty("contents")
            String contents,

            @JsonProperty("subTitleLink")
            String subTitleLink,

            @JsonProperty("titleLink")
            String titleLink,

            @JsonProperty("titleIcon")
            String titleIcon
    ) {}
}

