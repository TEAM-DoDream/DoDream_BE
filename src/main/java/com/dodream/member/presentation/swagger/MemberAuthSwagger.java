package com.dodream.member.presentation.swagger;

import com.dodream.core.config.swagger.ApiErrorCode;
import com.dodream.core.presentation.RestResponse;
import com.dodream.member.dto.request.MemberLoginRequestDto;
import com.dodream.member.dto.request.MemberSignUpRequestDto;
import com.dodream.member.dto.response.CheckMemberIdResponseDto;
import com.dodream.member.dto.response.CheckMemberNickNameResponseDto;
import com.dodream.member.dto.response.MemberLoginResponseDto;
import com.dodream.member.dto.response.MemberNewTokenResponse;
import com.dodream.member.dto.response.MemberSignUpResponseDto;
import com.dodream.member.exception.MemberErrorCode;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;

@Tag(name = "Auth", description = "회원 인증 관련 API")
public interface MemberAuthSwagger {

    @Operation(
        summary = "회원 로그인 API",
        description = "아이디와 비번 입력하여 로그인을 진행한다",
        operationId = "/v1/member/auth/login"
    )
    @ApiErrorCode(MemberErrorCode.class)
    ResponseEntity<RestResponse<MemberLoginResponseDto>> getMemberLogin(
        @RequestBody MemberLoginRequestDto memberLoginRequestDto);

    @Operation(
        summary = "로그아웃",
        description = "로그아웃을 진행한다",
        operationId = "/v1/member/auth/logout"
    )
    @ApiErrorCode(MemberErrorCode.class)
    ResponseEntity<RestResponse<String>> getMemberLogout();


    @Operation(
        summary = "회원 회원가입 API",
        description = "아이디,비밀번호,닉네임,성별 등을 입력하여 회원가입을 진행한다 <br>✅ 만약 거주지(선택사항)를 입력하지 않은 경우: \"regionCode\": null 로 보낸다.",
        operationId = "/v1/member/auth/sign-up"
    )
    @ApiErrorCode(MemberErrorCode.class)
    ResponseEntity<RestResponse<MemberSignUpResponseDto>> getMemberSignUp(
        @RequestBody MemberSignUpRequestDto memberSignUpRequestDto);

    @Operation(
        summary = "회원 회원가입 - 아이디 중복 확인 API",
        description = "아이디의 중복 확인 여부를 확인한다",
        operationId = "/v1/member/auth/check-id"
    )
    @ApiErrorCode(MemberErrorCode.class)
    ResponseEntity<RestResponse<CheckMemberIdResponseDto>> checkMemberLoginId(
        @Parameter(name = "loginId", description = "가입하려는 아이디", example = "dodream")
        @RequestParam String loginId
    );

    @Operation(
        summary = "회원 회원가입 - 닉네임 중복 확인 API",
        description = "닉네임 중복 확인 여부를 확인한다",
        operationId = "/v1/member/auth/check-nickname"
    )
    @ApiErrorCode(MemberErrorCode.class)
    ResponseEntity<RestResponse<CheckMemberNickNameResponseDto>> checkMemberNickName(
        @Parameter(name = "nickname", description = "가입하려는 닉네임", example = "두둠칫")
        @RequestParam String nickname
    );

    @Operation(
        summary = "토큰 재발급 API",
        description = "refresh 토큰을 이용하여 토큰을 재발급 받는다",
        operationId = "/v1/member/auth/refresh"
    )
    @ApiErrorCode(MemberErrorCode.class)
    ResponseEntity<RestResponse<MemberNewTokenResponse>> issueNewToken(
        @RequestHeader(value = "refreshToken", required = false) String refreshToken);


    @Operation(
         summary = "회원탈퇴 API",
         description = "회원탈퇴를 진행한다",
         operationId = "/v1/member/auth/withdraw"
     )
     @ApiErrorCode(MemberErrorCode.class)
     ResponseEntity<RestResponse<String>> withdrawMember();



}
