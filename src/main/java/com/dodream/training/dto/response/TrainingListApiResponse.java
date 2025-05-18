package com.dodream.training.dto.response;

import com.dodream.training.util.TrainingDateUtils;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;

import java.util.List;

public record TrainingListApiResponse(
        @JsonProperty("scn_cnt")
        @Schema(description = "전체 검색 결과 개수", example = "28")
        int scnCnt,

        @JsonProperty("pageNum")
        @Schema(description = "현재 페이지 번호", example = "1")
        String pageNum,

        @JsonProperty("pageSize")
        @Schema(description = "현재 페이지 사이즈", example = "20")
        String pageSize,

        @JsonProperty("srchList")
        @Schema(description = "검색 결과 리스트")
        List<BootcampItem> srchList
) {
    public record BootcampItem(
            @JsonProperty("address")
            @Schema(description = "주소", example = "경기 안양시 만안구")
            String address,

            @JsonProperty("realMan")
            @Schema(description = "수강비", example = "189850")
            String realMan,

            @JsonProperty("subTitle")
            @Schema(description = "부제목(장소)", example = "느티나무요양보호사교육원")
            String subTitle,

            @JsonProperty("title")
            @Schema(description = "제목", example = "요양보호사자격증취득과정 (사회복지사)")
            String title,

            @JsonProperty("titleLink")
            @Schema(
                    description = "링크",
                    example = "https://hrd.work24.go.kr/hrdp/co/pcobo/PCOBO0100P.do?tracseId=AIG20240000469334&tracseTme=3&trainstCstmrId=500041590848&crseTracseSe=C0061"
            )
            String titleLink,

            @JsonProperty("traStartDate")
            @Schema(description = "훈련 시작일", example = "2025-07-11")
            String traStartDate,

            @JsonProperty("traEndDate")
            @Schema(description = "훈련 종료일", example = "2025-07-26")
            String traEndDate,

            @Schema(description = "총 훈련기간", example = "약 15일")
            String traDuration,

            @JsonProperty("trainstCstId")
            @Schema(description = "훈련기관 아이디", example = "500041590848")
            String trainstCstId,

            @JsonProperty("trprDegr")
            @Schema(description = "훈련차수", example = "3")
            String trprDegr,

            @JsonProperty("trprId")
            @Schema(description = "훈련 고유 id", example = "AIG20240000469334")
            String trprId
    ) {
            public static BootcampItem from(BootcampItem item) {
                    return new BootcampItem(
                            item.address(),
                            item.realMan(),
                            item.subTitle(),
                            item.title(),
                            item.titleLink(),
                            item.traStartDate(),
                            item.traEndDate(),
                            TrainingDateUtils.calculateDuration(item.traStartDate, item.traEndDate),
                            item.trainstCstId(),
                            item.trprDegr(),
                            item.trprId()
                    );
            }
    }
}

