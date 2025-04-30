package com.dodream.member.dto.response;

public record CheckMemberIdResponseDto(
    String memberId,
    boolean duplicated,
    String message

) {

    public static CheckMemberIdResponseDto of(String memberId, boolean duplicated) {
        return new CheckMemberIdResponseDto(memberId, duplicated,"사용가능한 아이디입니다");
    }

}
