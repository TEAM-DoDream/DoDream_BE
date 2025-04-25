package com.dodream.member.presentation.swagger;

import com.dodream.core.config.swagger.ApiErrorCode;
import com.dodream.core.presentation.RestResponse;
import com.dodream.member.dto.request.MemberLoginRequestDto;
import com.dodream.member.dto.request.MemberSignUpRequestDto;
import com.dodream.member.dto.response.MemberLoginResponseDto;
import com.dodream.member.dto.response.MemberSignUpResponseDto;
import com.dodream.member.exception.MemberErrorCode;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;

@Tag(name = "Member Auth", description = "회원 인증 관련 API")
public interface MemberAuthSwagger {

    @Operation(
        summary = "회원 로그인 API",
        description = "아이디와 비번 입력하여 로그인 진행",
        operationId = "/v1/member/auth/login"
    )
    @ApiErrorCode(MemberErrorCode.class)
    ResponseEntity<RestResponse<MemberLoginResponseDto>> getMemberLogin(
        @RequestBody MemberLoginRequestDto memberLoginRequestDto);


    @Operation(
        summary = "회원 회원가입 API",
        description = "아이디,비밀번호,닉네임,성별 등을 입력하여 회원가입 진행",
        operationId = "/v1/member/auth/sign-up"
    )
    @ApiErrorCode(MemberErrorCode.class)
    ResponseEntity<RestResponse<MemberSignUpResponseDto>> getMemberSignUp(
        @RequestBody MemberSignUpRequestDto memberSignUpRequestDto);


}
