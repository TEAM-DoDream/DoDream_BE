package com.dodream.member.dto.response;

public record CheckMemberNickNameResponseDto(
    String memberId,
    boolean duplicated

) {

    public static CheckMemberNickNameResponseDto of(String memberId, boolean duplicated) {
        return new CheckMemberNickNameResponseDto(memberId, duplicated);
    }

}
