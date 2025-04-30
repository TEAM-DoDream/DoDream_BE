package com.dodream.member.dto.response;

public record CheckMemberNickNameResponseDto(
    String memberId,
    boolean duplicated,
    String message

) {

    public static CheckMemberNickNameResponseDto of(String memberId, boolean duplicated) {
        return new CheckMemberNickNameResponseDto(memberId, duplicated, "사용가능한 닉네임입니다");
    }

}
