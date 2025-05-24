package com.dodream.common.dto.response;

import com.dodream.common.infrastructure.converter.ScnListDeserializer;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import java.util.List;

public record CommonResponse(
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
