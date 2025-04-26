package com.dodream.member.dto.response;

public record CheckMemberIdResponseDto(
    String memberId,
    boolean duplicated

) {

    public static CheckMemberIdResponseDto of(String memberId, boolean duplicated) {
        return new CheckMemberIdResponseDto(memberId, duplicated);
    }

}
