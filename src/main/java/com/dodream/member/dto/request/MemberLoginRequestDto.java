package com.dodream.member.dto.request;

public record MemberLoginRequestDto(
    String memberId,
    String password
){
}
