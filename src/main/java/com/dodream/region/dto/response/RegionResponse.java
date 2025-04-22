package com.dodream.region.dto.response;

import com.dodream.region.infrastructure.converter.ScnListDeserializer;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import java.util.List;

public record RegionResponse(
        String codeName,
        @JsonProperty("scn_cnt") String scnCnt,
        SrchList srchList
) {
    public record SrchList(
            @JsonProperty("scn_list")
            @JsonDeserialize(using = ScnListDeserializer.class)
            List<ScnItem> scnList
    ) {}

    public record ScnItem(
            String rsltCode,
            String rsltName,
            String useYn
    ) {}
}
