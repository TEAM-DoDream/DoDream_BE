package com.dodream.member.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Level {

    SEED("씨앗","관심은 있지만, 아직 정보만 찾아보고 있어요"),
    SPROUT("새쌋","교육을 듣거나, 자격증 공부를 시작할거예요."),
    TREE("꿈나무","실제로 채용 공고를 찾거나, 서류를 준비하고 있어요.");

    private final String value;
    private final String description;
}